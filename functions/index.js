let functions = require('firebase-functions');
let admin = require('firebase-admin');
admin.initializeApp(functions.config().firebase);

exports.sendStarredSessionNotification = functions.database.ref('/starred_sessions/{pushId}')
.onCreate((snapshot, context)  => {

     // Grab the current value of what was written to the Realtime Database.
     const data = snapshot.val();
    //get the start session event
    const day = data.day;
    console.log('day of the event ' +  day);

    const session_id =   data.session_id;
    console.log('session id is  ' +  session_id);

    const user_id =   data.user_id;
    console.log('user id is ' +  user_id);

    //device id token get it here
    const toDeviceIdPromise = admin.database().ref(`/users/${user_id}/${refresh_token}/instanceId`).
    once('value');

    //name of event get it here
    const eventNamePromise = admin.database().ref(`/${day}/${session_id}/${title}/instanceId`).
    once('value');
    //venue of event get it here
    const eventVenuePromise = admin.database().ref(`/${day}/${session_id}/${room}/instanceId`).
    once('value');
    //time of event comes here
    const eventTimePromise = admin.database().ref(`/${day}/${session_id}/${time_stamp}/instanceId`).
    once('value');

 // const userIdPromise = admin.auth().getUser(user_id);

 return Promise.all([toDeviceIdPromise, eventNamePromise,eventVenuePromise,
    eventTimePromise]).then(results => {

    const toDeviceId = results[0].val();
    const eventName = results[1].val();
    const eventVenue = results[2].val();
    const eventTime = results[3].val();

    console.log('device id is  ' + toDeviceId);
    console.log('eventName  is  ' + eventName);

    console.log('eventVenue  is ' + eventVenue);
    console.log('eventTime  is  ' + eventTime);


        return console.log('eventVenue  is ' + eventVenue) ;

          });


     

})