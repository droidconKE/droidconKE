In the .droidconKE app we have used the following Firebase SDKs
1. Cloud Firestore
2. Cloud Messaging
3. Crashlytics
4. Cloud Functions
5. Authentication
6. Cloud Storage
7. Remote Config

For those who want to run the code for the .droidconKE app, follow the setup steps below for each of the Firebase SDKs as outlined below.

### Authentication Setup

The .droidconKE uses Google sign up for users to easily sign up/in to the app. Firebase has a couple of authentication methods but we used only Google Sign in. In your Firebase Console, Go to Authentication and click on the **Sign-in method and enable Google.**


### Cloud Storage Setup
All the images for the .droidconKE app were stored in Cloud Storage. The images are grouped into folders for easier identification. The following are the folders in the cloud storage.

**1. agenda_icons/** - This contains icons for the conference agenda. Example of the agendas include lunch, after hours, codelabs etc. You can have your own icons and place them in these folder and add their names as:
   1. codelabs.png
   2. lunch.png
   3. afterhours.png
   4. etc
   
**2. designs/** - This folder contains the designs for the conference.Example images in this folder are:
   1. hack_bounty.png
   2. sponsor_slots.png
   3. pitching.png
 
**3. organizers/** - This folder contains the logos for the organizers of the conference. Example images in this folder are:
   1. organizer_one.jpeg
   2. organizer_two.png
   
**4. sessions/** - This folder contains the designs for each session of the conference. Example images in this folder are:
   1. jetpack_compose.png
   2. compassionate_android_developer.png
   
**5. speakers/** - This folder contains the profile pictures for the various speakers at the conference . Example images in this folder are:
   1. speaker_one.png
   2. speaker_two.png
   
**6. sponsors/** - This folder contains the logos for the companies that sponsored the conference. Example images in this folder are: 
   1. sponsor_one.png
   2. sponsor_two.png
   
  The images can either be **.png/jpeg/jpg**. You can add as many folders as need be.
  
### Cloud Functions
  
Cloud Functions were used to listen to when a user "stars" or favourites a session. When a favourited session is about to begin, the cloud functions will trigger cloud messaging to send notifications to all users who have starred/favourited the session. The code for the cloud functions can be found [here](https://github.com/droidconKE/droidconKE/blob/master/functions/index.js)

### Cloud Messaging

For Cloud Mesaging, no additional configurations were made. The default console settings are good to go with.

### Crashlytics

The Crashlytics setup has already been done in gradle files according to the following [instructions](https://firebase.google.com/docs/crashlytics/get-started) to setup crashlytics. 

### Remote Config

We used Remote Config for the data which would change during the conference and the data that we wanted the changes t quickly reflect to the users upon change. The remote config defaults can be found in [this](https://github.com/droidconKE/droidconKE/blob/master/app/src/main/res/xml/remote_config_defaults.xml) file. Add the parameters according to their `key` in the file in the Remote Config section of Firebase Console.

### Cloud Firestore

