# Face API - Indentification in Android

<p>
  <img width="220" align="right" src="https://github.com/ishaanjav/Face_API_-_Indentification_in_Android/blob/master/Detection.png">
  <img width="220" align="right" src="https://github.com/ishaanjav/Face_API_-_Indentification_in_Android/blob/master/Identification.png">
</p>

**This is a repository for an Android app that uses the [Microsoft Face API](https://azure.microsoft.com/en-us/services/cognitive-services/face/) for facial detection and  identification.** Features of this app include:
- **Detecting** faces in an image and drawing a box around them.
- **Indentifying** faces in an image and placing their name next to their face.

<br/>

In the following sections, information regarding [**using the app**](#usage) and [**setting up the app**](#setup) can be found below. 

<br/>

###### Please Note: The process of adding people to Person Groups is done through a Console Application found in my other repository called [Face API - Visual Studio.](https://github.com/ishaanjav/Face_API_-_Visual_Studio) After adding the people to the Person Groups, this Android app can be used for identification.

<br/>

------
<img width="250" align="right" src="https://github.com/ishaanjav/Face_API_-_Indentification_in_Android/blob/master/Process.gif">

## Usage


The app is simple to use and has two buttons: a **detect** button and an **identify** button along with an `imageView` to display the taken picture. Below are the steps for using the app:
1. Press the **"Detect" button** to launch the camera. 
2. **Take a picture** of a person's face. 
   * Now the app will proccess the image for faces and draw a red box around every face that it finds in the image.
3. **After pressing the "Detect" button**, press the **"Identify" button** to identify the face in the picture with a person from the Person Group. 
   * The app will use the face detected from the detection process to find its best match in the existing Person Group.
   * If no matches were found, an empty `String` will result. Otherwise, the person's name will appear next to their red box.

<br/>

**IMPORTANT:** In order to use this app as demoed to the right, you must follow the steps in the [Setup Section](#setup) to set up the app with your own [Microsoft Face API Key.](https://azure.microsoft.com/en-us/pricing/details/cognitive-services/face-api/)

-----
## Setup
**Please note that this app requires the use of Microsoft Azure's Face API. Without the API Key, you will not be able to use the app as it was intended. [This section](#getting-the-face-api-key) contains the full set of instructions to getting your own API key for free. The [below section](#using-the-api-key-in-the-app) explains how to use it in the app by changing a single line of code.**
### Downloading to Android Studio
To use the app, you can clone it from this GitHub repository as a zip file, extract the contents of the file, and open it as a project in Android Studio. Once you have done so, it can be run on your Android device.

### Using the API Key in the app
Assuming that you have already gotten the API Key from the [Azure Portal](https://portal.azure.com/#home), you can continue with the following steps. If not, please read the [**"Getting the Face API Key" Section**](#getting-the-face-api-key) to see instructions on creating a free account and getting the API Key.

In [`MainActivity.java`](https://github.com/ishaanjav/Face_API_-_Indentification_in_Android/blob/master/app/src/main/java/com/example/anany/videofacerecognition/MainActivity.java), Line 45 looks like this:

    private FaceServiceClient faceServiceClient = new FaceServiceRestClient("<YOUR API ENDPOINT HERE>", "<YOUR API KEY HERE>");
replace `<YOUR API SUBSCRIPTION KEY>` with one of your 2 keys from the Azure Portal. *(If you haven't gotten your API Key yet, read the following [two sections below](#getting-the-face-api-key))*. `<YOUR ENDPOINT HERE>` should be replaced with one of the following examples from [this API Documentation link](https://westus.dev.cognitive.microsoft.com/docs/services/563879b61984550e40cbbe8d/operations/563879b61984550f30395236). The format should be similar to: 
  
    "https://<LOCATION>/face/v1.0"
  
where `<LOCATION>` should be replaced with something like `uksouth.api.cognitive.microsoft.com` or `japaneast.api.cognitive.microsoft.com`. All of these can be found, listed at [this link](https://westus.dev.cognitive.microsoft.com/docs/services/563879b61984550e40cbbe8d/operations/563879b61984550f30395236).  

### If you have followed all these steps, the app should now be ready for you to use! Check out the [Conclusion Section](#conclusion) for extra info.



-----
## Getting the Face API Key

### Making the Azure Account
In order to run the face dectection and identification , you must get an API Subscription Key from the Azure Portal. [This page](https://azure.microsoft.com/en-us/services/cognitive-services/face/) by Microsoft provides the features and capabilities of the Face API. **You can create a free Azure account that doesn't expire at [this link here](https://azure.microsoft.com/en-us/try/cognitive-services/?api=face-api) by clicking on the "Get API Key" button and choosing the option to create a free Azure account**. 
### Getting the Face API Key from Azure Portal
Once you have created your account, head to the [Azure Portal](https://portal.azure.com/#home). Follow these steps:
1. Click on **"Create a resource"** on the left side of the portal.
2. Underneath **"Azure Marketplace"**, click on the **"AI + Machine Learning"** section. 
3. Now, under **"Featured"** you should see **"Face"**. Click on that.
4. You should now be at [this page](https://portal.azure.com/#create/Microsoft.CognitiveServicesFace). **Fill in the required information and press "Create" when done**.
5. Now, click on **"All resources"** on the left hand side of the Portal.
6. Click on the **name you gave the API**.
7. Underneath **"Resource Management"**, click on **"Manage Keys"**.

<p align="center">
  <img width="900" src="https://github.com/ishaanjav/Face_Analyzer/blob/master/Azure-FaceAPI%20Key.PNG">
  <td>Hi</td>
</p>

You should now be able to see two different subscription keys that you can use. Follow the instructions in the [**"Using the API Key in the app" Section**](#using-the-api-key-in-the-app) to see how to use the API Key in the app. It only requires a change in 1 line of code.

-----

## Conclusion
**This was a repository for an Android app that uses the Microsoft Face API to detect and identify faces in an image.** If you are having any issues, troubles, or problems, you can reach me at ishaanjav@gmail.com or by [making a new issue](https://github.com/ishaanjav/Face_API_-_Indentification_in_Android/issues) for this repository.

One thing to keep in mind is that *in order to add people to the Person Group, you should first follow my code for [this repository](https://github.com/ishaanjav/Face_API_-_Visual_Studio).* In that repository, you will find code for Visual Studio that adds people to Person Groups given their name and images and identifies them. Thence, you can try out this Android app for identification.

**Other Repositories:** If you enjoyed using and testing out this app, *and have given it a star*, then you may want to check out some of my other repositories related to facial analysis and identification.
- [**Face API - Visual Studio**](https://github.com/ishaanjav/Face_API_-_Visual_Studio): The purpose of this repository is to provide code for a Console Application in C# that uses the Microsoft Azure Face API to add people to Person Groups and then identify people given images. [README](https://github.com/ishaanjav/Face_API_-_Visual_Studio/blob/master/README.md)** 
- [**RECOMMENDED - Face Analyzer**](https://github.com/ishaanjav/Face_Analyzer): The purpose of this Android app is to utilize the Microsoft Face API to not only detect individual faces in an image, but also for each face provide information such as emotions, the estimated age, gender, and more. [README](https://github.com/ishaanjav/Face_Analyzer/blob/master/README.md)
- [**RECOMMENDED - Kairos Face Recognition**](https://github.com/ishaanjav/Kairos_Face_Recognition): The purpose of this Android app is to use Kairos's SDK for Android in order to implement facial recognition. Features of this app include: registering and identifying users when given an image. [README](https://github.com/ishaanjav/Kairos_Face_Recognition/blob/master/README.md)
