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


let img = document.getElementById("img");


function chooseprofile(e) {
firebase.auth().onAuthStateChanged(function(user) {
    let file = {};
    let email = user.email;
    file = e.target.files[0];
    firebase.storage().ref('Profile Images/' + email +'/profile.jpg').put(file).then(() => {
        window.location.reload();
    })
})}
let user = firebase.auth().onAuthStateChanged(function(user) {
    let email = user.email;
    firebase.storage().ref('Profile Images/' + email + '/profile.jpg').getDownloadURL().then(photoURL => {
        proimg.src = photoURL
        console.log("success")
    }).catch(() => {
        alert("Please upload your Profile picture")
    })
})
    

function logout(){
    firebase.auth().signOut();
    window.location="login.html";
    alert("Signed Out");
  }

firebase.auth().onAuthStateChanged(function(user) {
    let email = user.email;
    firebase.firestore().collection("Users").doc(email).get().then(doc => {
        if(doc.exists) {

            personal_info.innerHTML += "<br><br><label style='text-align:left; color:darkslategray;'>Name</label><h3 style='color:darkblue;'>" + doc.data().Name + "</h3><br>"
            personal_info.innerHTML += "<label style='float: left; color:darkslategray;'>Birth Date</label><br><h3 style='color:darkblue;'>" + doc.data().Birthdate + "</h3><br>"
            personal_info.innerHTML += "<label style='float: left; color:darkslategray;'>Age</label><br><h3 style='color:darkblue;'>" + doc.data().Age + "</h3><br>"
            personal_info.innerHTML += "<label style='float: left; color:darkslategray;'>Gender</label><br><h3 style='color:darkblue;'>" + doc.data().Gender + "</h3><br>"
            personal_info.innerHTML += "<label style='float: left; color:darkslategray;'>Blood Group</label><br><h3 style='color:darkblue;'>" + doc.data().Blood_Group + "</h3><br>"
            personal_info.innerHTML += "<label style='float: left; color:darkslategray;'>Height(cm's)</label><br><h3 style='color:darkblue;'>" + doc.data().Height + "</h3><br>"
            personal_info.innerHTML += "<label style='float: left; color:darkslategray;'>Weight(kg's)</label><br><h3 style='color:darkblue;'>" + doc.data().Weight + "</h3><br>"
            
            medical_history.innerHTML += "<label style='color:darkslategray;'>Please list any Drug allergies</label><div id='mhdrug' style='color:darkblue;'><h3>" + doc.data().Drug_allergies + "</h3></div><br />"
            medical_history.innerHTML += "<label style='color:darkslategray;'>Have you ever had?</label><br />"
            medical_history.innerHTML += "<label style='color:darkslategray;position: relative;left:30px;'>Asthma</label><div id='mhasthma' style='color:darkblue;position: relative;left:30px;'><h3>" + doc.data().Asthma + "</h3></div>"
            medical_history.innerHTML += "<label style='color:darkslategray;position: relative;left:30px;'>Coronary Artery Disease(CHD)</label><div id='mhchd' style='color:darkblue;position: relative;left:30px;'><h3>" + doc.data().Coronary_Artery_Disease + "</h3></div>"
            medical_history.innerHTML += "<label style='color:darkslategray;position: relative;left:30px;'>Hypoxemia</label><div id='mhhypoxemia' style='color:darkblue;position: relative;left:30px;'><h3>" + doc.data().Hypoxemia + "</h3></div>"
            medical_history.innerHTML += "<label style='color:darkslategray;position: relative;left:30px;'>Diabetes</label><div id='mhdiabetes' style='color:darkblue;position: relative;left:30px;'><h3>" + doc.data().Diabetes + "</h3></div>"
            medical_history.innerHTML += "<label style='color:darkslategray;position: relative;left:30px;'>Bronchiectasis</label><div id='mhbronchiectasis' style='color:darkblue;position: relative;left:30px;'><h3>" + doc.data().Bronchiectasis + "</h3></div><br />"
            medical_history.innerHTML += "<label style='color:darkslategray;'>Other Illness</label><div id='mhotherillness' style='color:darkblue;'><h3>" + doc.data().Other_Illness + "</h3></div><br />"
            medical_history.innerHTML += "<label style='color:darkslategray;'>Please list your current medication</label><div id='mhcurrentmedication' style='color:darkblue;'><h3>" + doc.data().Current_medication + "</h3></div><br />"
            medical_history.innerHTML += "<label style='color:darkslategray;'>Are you Alcoholic?</label><div id='mhalcoholic' style='color:darkblue;'><h3>" + doc.data().Alcoholic + "</h3></div><br />"
        }
        else{
            alert("Please update your profile")
        }
    })
})
    
