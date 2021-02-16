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
    window.location="login.html";
    alert("Signed Out");
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

firebase.auth().onAuthStateChanged(function(user) {
  let email = user.email;
  firebase.firestore().collection("Users").doc(email).collection("All_health_parameters").orderBy('Date', 'desc').limit(1).get().then(snapshot => {
    snapshot.forEach(doc => {
    let preading = doc.data().PReading,
      sreading = doc.data().SReading,
      dreading = doc.data().DReading,
      creading = doc.data().CReading,
      o2reading = doc.data().O2Reading,
      rrreading = doc.data().RRReading,
      greading = doc.data().GReading;

      //Heart HEalth
      if((preading >= 60 && preading <= 100) && (creading >= 125 && creading <=200) && (sreading <= 120 && dreading <= 80)){
        Hearthealth.innerHTML += "<div style='border-radius:50%; border:2px solid green; height:100px; width:100px; position:relative; left:20%; top:30%;'><h4 style='color:green; position:relative; top:40%'>Healthy</h4></div>";
      }
      else{
        Hearthealth.innerHTML += "<div style='border-radius:50%; border:2px solid red; height:100px; width:100px; position:relative; left:20%; top:30%;'><h4 style='color:red; position:relative; top:40%'>Unhealthy</h4></div>";
      }

      //Glucose level
      if(greading <= 140){
        Glucoselevel.innerHTML += "<div style='border-radius:50%; border:2px solid green; height:100px; width:100px; position:relative; left:20%; top:30%;'><h4 style='color:green; position:relative; top:40%'>Normal</h4></div>";
      }
      else{
        Glucoselevel.innerHTML += "<div style='border-radius:50%; border:2px solid red; height:100px; width:100px; position:relative; left:20%; top:30%;'><h4 style='color:red; position:relative; top:40%'>Abnormal</h4></div>";
      }

      //Respiration rate
      if((o2reading >= 98) && (rrreading >= 12 && rrreading <=20)){
        respiration.innerHTML += "<div style='border-radius:50%; border:2px solid green; height:100px; width:100px; position:relative; left:20%; top:30%;'><h4 style='color:green; position:relative; border-radius:50%; top:40%'>Healthy</h4></div>";
      }
      else{
        respiration.innerHTML += "<div style='border-radius:50%; border:2px solid red; height:100px; width:100px; position:relative; left:20%; top:30%;'><h4 style='color:red; position:relative; top:40%;'>Unhealthy</h4></div>";
      }
    })
  })
})