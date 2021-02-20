// Link Firebase confidential configuration for connecting firebase.

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