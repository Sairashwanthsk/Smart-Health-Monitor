// Link Firebase confidential configuration for connecting firebase.


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