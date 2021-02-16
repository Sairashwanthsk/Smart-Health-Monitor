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

// Registration
function register2(){
  let email = document.getElementById("email").value,
    password = document.getElementById("password_1").value,
    password_2 = document.getElementById("password_2").value;
  if (password != password_2){
      alert("Error: Password do not match");
  }else {
    firebase.auth().createUserWithEmailAndPassword(email, password).then(function() {
      window.location = "personalinfo.html"
    alert("Successfully registered");
  }).catch(() => {
      alert("Error: Email already exists");
  });
  }    
}


// Login
function login(){
  let email = document.getElementById("email").value,
    password = document.getElementById("password").value;
  firebase.auth().signInWithEmailAndPassword(email, password).then(function() {
    window.location="Dashboard.html";
    alert("Successfully Logged In");
  }).catch(() => {
    alert("Error: Incorrect Email/Password");
  });
}


// Logout
function logout(){
  firebase.auth().signOut();
  window.location="login.html";
  alert("Signed Out");
}


// Personal Details
let db = firebase.firestore();

document.querySelector("#perinfo").addEventListener("submit", next1);
function next1(e) {
    e.preventDefault();
    
    let name = document.querySelector(".name").value,
      birthdate = document.querySelector(".birthdate").value,
      age = document.querySelector(".age").value,
      gender = document.querySelector(".gender").value,
      bloodgroup = document.querySelector(".bloodgroup").value,
      height = document.querySelector(".height").value,
      weight = document.querySelector(".weight").value;
      //console.log("Success");
firebase.auth().onAuthStateChanged(function(user) {
  let email = user.email;
  db.collection("Users").doc(email).set({
      Name: name,
      Email: email,
      Birthdate: birthdate,
      Age: Number(age),
      Gender: gender,
      Blood_Group: bloodgroup,
      Height: Number(height),
      Weight: Number(weight)
  }).then(function() {
      document.getElementById("perinfo").hidden = true;
      document.getElementById("medhis").hidden = false;
  })
})}


// Medical history details
document.querySelector("#medhis").addEventListener("submit", submit);
function submit(e) {
     e.preventDefault();
    //let name = document.querySelector("#name").value,
    let  drugallergies = document.querySelector(".drug_allergies").value,
      asthma = document.querySelector(".asthma").value,  
      chd = document.querySelector(".chd").value,
      hypoxemia = document.querySelector(".hypoxemia").value,  
      diabetes = document.querySelector(".diabetes").value, 
      bronchiectasis = document.querySelector(".bronchiectasis").value,
      otherillness = document.querySelector(".otherillness").value,
      curmedication = document.querySelector(".curmedication").value,
      alcoholconsumption = document.querySelector(".alcoholconsumption").value;
  firebase.auth().onAuthStateChanged(function(user) {
    let email = user.email;
    db.collection("Users").doc(email).set({
      Drug_allergies: drugallergies,
      Asthma: asthma,
      Coronary_Artery_Disease: chd,
      Hypoxemia: hypoxemia,
      Diabetes: diabetes,
      Bronchiectasis: bronchiectasis, 
      Other_Illness: otherillness,
      Current_medication: curmedication,
      Alcoholic: alcoholconsumption
  },{merge:true}).then(function() {
        window.location="Dashboard.html";
  })
})}
