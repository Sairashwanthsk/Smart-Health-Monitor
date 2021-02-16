var firebaseConfig = {
    apiKey: "AIzaSyDAF_vfIespDU1d6GQpAkppS2rOQu_Z_U8",
    authDomain: "smart-health-monitor-2f5d5.firebaseapp.com",
    projectId: "smart-health-monitor-2f5d5",
    storageBucket: "smart-health-monitor-2f5d5.appspot.com",
    messagingSenderId: "741530269873",
    appId: "1:741530269873:web:e8987123338aafc97c0a90"
  };
  // Initialize Firebase
  firebase.initializeApp(firebaseConfig);
  
  // Logout
  function logout(){
    firebase.auth().signOut();
    window.location="adminlogin.html";
    alert("Signed Out");
  }

console.log("test1");
function sendmessage(){
  let title = document.querySelector("#title").value,
    date = document.querySelector("#date").value,
    message = document.querySelector("#message").value;
  firebase.firestore().collection("Notification").doc(date).set({
    Title: title,
    Date: date,
    Message: message
  }).then(() => {
    alert("Notification sent successfully");
    window.location.reload();
  })
}

firebase.firestore().collection("Notification").orderBy("Date", 'desc').get().then(snapshot => {
  snapshot.forEach(doc => {
  let date = doc.data().Date,
    title = doc.data().Title,
    message = doc.data().Message;

    notificationbody.innerHTML += "<h6 style='text-align:center; background-color:rgba(0, 0, 0, 0.26);'>"+date+"</h6>";
    notificationbody.innerHTML += "<h4>"+title+"</h4>";
    notificationbody.innerHTML += "<p>"+message+"</p><br>";
  })
})