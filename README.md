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
