let functions = require('firebase-functions');
let admin = require('firebase-admin');
admin.initializeApp(functions.config().firebase);

exports.sendStarredSessionNotification = functions.firestore
    .document('/starred_sessions/{userId}')
    .onCreate((snap, context) => {
    
    // Grab the current value of what was written to the Firestore.
    var data = snap.data();
    //get the start session event
    var day = data.day;
    console.log('day of the event ' +  day);

    var session_id =   data.session_id;
    console.log('session id is  ' +  session_id);

    var user_id =   data.user_id;
    console.log('user id is ' +  user_id);

    var documentId= data.documentId;
    console.log('documentId is '+ documentId);

    //device id token get it here
    admin.firestore().collection('users').doc(user_id).get().then(user => {
    if (!user.exists) {
        console.log('No such user!');
    } else {
        var deviceId= user.data().refresh_token;
        console.log('refresh_token:', user.data().refresh_token);
    }
    })
    .catch(err =>{
        console.log('Error getting user',err);
    });

    //get session details
    admin.firestore().collection(day).doc(documentId).get().then(session =>{
        if (!session.exists) {
            console.log('No such session');
        }else{
            var eventName= session.data().title;
            var eventVenue= session.data().room;
            var eventTime= session.data().time;

            console.log('eventName is ' + eventName);
            console.log('eventVenue is ' +eventVenue);
            console.log('eventTime is ' +eventTime);
        }
    })
    .catch(err => {
        console.log('Error getting session',err);
    });
    
return true;


});
