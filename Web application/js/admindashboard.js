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

  function adminlogin(){
    let email = document.getElementById("email").value,
      password = document.getElementById("password").value;
    firebase.auth().signInWithEmailAndPassword(email, password).then(function() {
      window.location="admindashboard.html";
      alert("Successfully Logged In");
    }).catch(() => {
      alert("Error: Incorrect Email/Password");
    });
  }
  
  
  // Logout
  function logout(){
    firebase.auth().signOut();
    window.location="adminlogin.html";
    alert("Signed Out");
  }

  function gmail(){
    window.open("https://mail.google.com/mail");
  }