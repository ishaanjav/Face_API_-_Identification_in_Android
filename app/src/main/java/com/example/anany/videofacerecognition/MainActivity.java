package com.example.anany.videofacerecognition;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.CollapsibleActionView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.microsoft.projectoxford.face.FaceServiceClient;
import com.microsoft.projectoxford.face.FaceServiceRestClient;
import com.microsoft.projectoxford.face.contract.CreatePersonResult;
import com.microsoft.projectoxford.face.contract.Face;
import com.microsoft.projectoxford.face.contract.FaceRectangle;
import com.microsoft.projectoxford.face.contract.IdentifyResult;
import com.microsoft.projectoxford.face.contract.Person;
import com.microsoft.projectoxford.face.contract.TrainingStatus;
import com.microsoft.projectoxford.face.rest.ClientException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    private FaceServiceClient faceServiceClient = new FaceServiceRestClient("<YOUR API ENDPOINT HERE>", "<YOUR API KEY HERE>");
    private final String personGroupId = "myfriends";

    ImageView imageView;
    Bitmap mBitmap;
    boolean takePicture = false;

    private ProgressDialog detectionProgressDialog;
    Face[] facesDetected;

    class detectTask extends AsyncTask<InputStream, String, Face[]> {
        private ProgressDialog mDialog = new ProgressDialog(MainActivity.this);

        @Override
        protected Face[] doInBackground(InputStream... params) {
            try {
                publishProgress("Detecting...");
                Face[] results = faceServiceClient.detect(params[0], true, false, null);
                if (results == null) {
                    publishProgress("Detection Finished. Nothing detected");
                    return null;
                } else {
                    publishProgress(String.format("Detection Finished. %d face(s) detected", results.length));
                    return results;
                }
            } catch (Exception ex) {
                return null;
            }
        }

        @Override
        protected void onPreExecute() {
            mDialog.show();
        }

        @Override
        protected void onPostExecute(Face[] faces) {
            mDialog.dismiss();

            facesDetected = faces;
        }

        @Override
        protected void onProgressUpdate(String... values) {
            mDialog.setMessage(values[0]);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        detectionProgressDialog = new ProgressDialog(this);

        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.billgates);
        imageView = findViewById(R.id.imageView);
        imageView.setImageBitmap(mBitmap);
        Button btnDetect = findViewById(R.id.btnDetectFace);
        Button btnIdentify = findViewById(R.id.btnIdentify);

        btnDetect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap icon = BitmapFactory.decodeResource(getApplicationContext().getResources(),
                        R.drawable.billgates);

                detectAndFrame(icon);

            }
        });

        btnIdentify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (takePicture) {
                    final UUID[] faceIds = new UUID[facesDetected.length];
                    for (int i = 0; i < facesDetected.length; i++) {
                        faceIds[i] = facesDetected[i].faceId;
                    }

                    new IdentificationTask(personGroupId).execute(faceIds);
                } else {
                    Toast.makeText(getApplicationContext(), "Please detect the face first.", Toast.LENGTH_SHORT).show();
                }
                //  faceServiceClient.m
                /*   CreatePersonResult createPersonResult;
                try {
                    createPersonResult = faceServiceClient.createPerson(personGroupId, "Bob", "My Friends");
                    Toast.makeText(getApplicationContext(), "Created Person called Bob", Toast.LENGTH_LONG).show();
                }catch(Exception e) {
                    Toast.makeText(getApplicationContext(), "Creation failed: " + e.getMessage() + " " + e.getCause(), Toast.LENGTH_LONG).show();
                }*/
            }
        });


    }

    private void detectAndFrame(final Bitmap imageBitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
        ByteArrayInputStream inputStream =
                new ByteArrayInputStream(outputStream.toByteArray());

        AsyncTask<InputStream, String, Face[]> detectTask =
                new AsyncTask<InputStream, String, Face[]>() {
                    String exceptionMessage = "";

                    @Override
                    protected Face[] doInBackground(InputStream... params) {
                        try {
                            publishProgress("Detecting...");
                            Face[] result = faceServiceClient.detect(
                                    params[0],
                                    true,         // returnFaceId
                                    false,        // returnFaceLandmarks
                                    null          // returnFaceAttributes:
                                /* new FaceServiceClient.FaceAttributeType[] {
                                    FaceServiceClient.FaceAttributeType.Age,
                                    FaceServiceClient.FaceAttributeType.Gender }
                                */
                            );
                            if (result == null) {
                                publishProgress(
                                        "Detection Finished. Nothing detected");
                                return null;
                            }
                            publishProgress(String.format(
                                    "Detection Finished. %d face(s) detected",
                                    result.length));
                            return result;
                        } catch (Exception e) {
                            exceptionMessage = String.format(
                                    "Detection failed: %s", e.getMessage());
                            return null;
                        }
                    }

                    @Override
                    protected void onPreExecute() {
                        //TODO: show progress dialog
                        detectionProgressDialog.show();
                    }

                    @Override
                    protected void onProgressUpdate(String... progress) {
                        //TODO: update progress
                        detectionProgressDialog.setMessage(progress[0]);
                    }

                    @Override
                    protected void onPostExecute(Face[] result) {
                        //TODO: update face frames
                        detectionProgressDialog.dismiss();

                        facesDetected = result;

                        if (!exceptionMessage.equals("")) {
                            if (facesDetected == null) {
                                showError(exceptionMessage + "\nNo faces detected.");
                            } else {
                                showError(exceptionMessage);
                            }
                        }
                        if (result == null) {
                            if (facesDetected == null) {
                                showError("No faces detected");
                            }
                        }


                        ImageView imageView = findViewById(R.id.imageView);
                        imageView.setImageBitmap(
                                drawFaceRectanglesOnBitmap(imageBitmap, result));
                        imageBitmap.recycle();
                        takePicture = true;
                    }
                };

        detectTask.execute(inputStream);
    }

    private static Bitmap drawFaceRectanglesOnBitmap(
            Bitmap originalBitmap, Face[] faces) {
        Bitmap bitmap = originalBitmap.copy(Bitmap.Config.ARGB_8888, true);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.RED);
        paint.setStrokeWidth(9);
        if (faces != null) {
            for (Face face : faces) {
                FaceRectangle faceRectangle = face.faceRectangle;
                canvas.drawRect(
                        faceRectangle.left,
                        faceRectangle.top,
                        faceRectangle.left + faceRectangle.width,
                        faceRectangle.top + faceRectangle.height,
                        paint);
            }
        }
        return bitmap;
    }

    private void showError(String message) {
        new AlertDialog.Builder(this)
                .setTitle("Error")
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                })
                .create().show();
    }

    private class IdentificationTask extends AsyncTask<UUID, String, IdentifyResult[]> {
        String personGroupId;

        private ProgressDialog mDialog = new ProgressDialog(MainActivity.this);

        public IdentificationTask(String personGroupId) {
            this.personGroupId = personGroupId;
        }

        @Override
        protected IdentifyResult[] doInBackground(UUID... params) {

            try {
                publishProgress("Getting person group status...");
                TrainingStatus trainingStatus = faceServiceClient.getPersonGroupTrainingStatus(this.personGroupId);
                if (trainingStatus.status != TrainingStatus.Status.Succeeded) {
                    publishProgress("Person group training status is " + trainingStatus.status);
                    return null;
                }
                publishProgress("Identifying...");

                IdentifyResult[] results = faceServiceClient.identity(personGroupId, // person group id
                        params // face ids
                        , 1); // max number of candidates returned

                return results;

            } catch (Exception e) {
                return null;
            }
        }

        @Override
        protected void onPreExecute() {
            mDialog.show();
        }

        @Override
        protected void onPostExecute(IdentifyResult[] identifyResults) {
            mDialog.dismiss();

            for (IdentifyResult identifyResult : identifyResults) {
                new PersonDetectionTask(personGroupId).execute(identifyResult.candidates.get(0).personId);
            }
        }

        @Override
        protected void onProgressUpdate(String... values) {
            mDialog.setMessage(values[0]);
        }
    }

    private class PersonDetectionTask extends AsyncTask<UUID, String, Person> {
        private ProgressDialog mDialog = new ProgressDialog(MainActivity.this);
        private String personGroupId;

        public PersonDetectionTask(String personGroupId) {
            this.personGroupId = personGroupId;
        }

        @Override
        protected Person doInBackground(UUID... params) {
            try {
                publishProgress("Getting person group status...");

                return faceServiceClient.getPerson(personGroupId, params[0]);
            } catch (Exception e) {
                return null;
            }
        }

        @Override
        protected void onPreExecute() {
            mDialog.show();
        }

        @Override
        protected void onPostExecute(Person person) {
            mDialog.dismiss();
            imageView.setImageBitmap(drawFaceRectangleOnBitmap(mBitmap, facesDetected, person.name));
        }

        @Override
        protected void onProgressUpdate(String... values) {
            mDialog.setMessage(values[0]);
        }
    }

    private Bitmap drawFaceRectangleOnBitmap(Bitmap mBitmap, Face[] facesDetected, String name) {

        Bitmap bitmap = mBitmap.copy(Bitmap.Config.ARGB_8888, true);
        Canvas canvas = new Canvas(bitmap);
        //Rectangle
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.RED);
        paint.setStrokeWidth(12);

        if (facesDetected != null) {
            for (Face face : facesDetected) {
                FaceRectangle faceRectangle = face.faceRectangle;
                canvas.drawRect(faceRectangle.left,
                        faceRectangle.top,
                        faceRectangle.left + faceRectangle.width,
                        faceRectangle.top + faceRectangle.height,
                        paint);
                drawTextOnCanvas(canvas, 100, ((faceRectangle.left + faceRectangle.width) / 2) + 100, (faceRectangle.top + faceRectangle.height) + 50, Color.RED, name);

            }
        }
        return bitmap;
    }

    private void drawTextOnCanvas(Canvas canvas, int textSize, int x, int y, int color, String name) {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(color);
        paint.setTextSize(textSize);

        float textWidth = paint.measureText(name);

        canvas.drawText(name, x - (textWidth / 2), y - (textSize / 2), paint);
    }
}