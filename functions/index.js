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

    var session_id =  data.session_id;
    console.log('session id is  ' +  session_id);

    var user_id =   data.user_id;
    console.log('user id is ' +  user_id);

    var documentId= data.documentId;
    console.log('documentId is '+ documentId);

    //device id token get it here
    const getDeviceTokenPromise = admin.firestore().collection('users').doc(user_id).get();
   
    //get session details
    const getSessionDetailsPromise = admin.firestore().collection(day).doc(documentId).get();

    let userDocument;
    let sessionDocument;

   return Promise.all([getDeviceTokenPromise,getSessionDetailsPromise]).then(results => {
        var refresh_token = '';
        var notificationMessage = {};

        userDocument= results[0].data();
        sessionDocument = results[1].data();

        refresh_token = userDocument.refresh_token;     
        var eventName= sessionDocument.title;
        var eventVenue= sessionDocument.room;
        var eventTime= sessionDocument.time;

        console.log('refresh token is '+ refresh_token);
        console.log('event name is '+ eventName);
        console.log('event venu is '+ eventVenue);
        console.log('event name is '+ eventTime);
         
        notificationMessage = {
            notification:{
                title: eventName + ' About to start',
                body:  eventName+' time is :'+eventTime +' and venue is '+eventVenue                    
             }
        };

        //add condition check for session time                
    return admin.messaging().sendToDevice(refresh_token, notificationMessage);
    }).then((response)=>{
        
        return console.log('push notification response is'+ response.data);
    })
    .catch(err =>{
        console.log('Error getting details',err);
    });

});
