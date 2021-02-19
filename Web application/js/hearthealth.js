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
  
  
    function logout(){
      firebase.auth().signOut();
      window.location="login.html";
      alert("Signed Out");
    }
    
  
  let db = firebase.firestore();

  // Creating Database
document.querySelector(".hearthealthform").addEventListener("submit", record);  
function record(e) {
  e.preventDefault();
  
  firebase.auth().onAuthStateChanged(function(user) {
    let email = user.email;
  //let name = document.querySelector(".name").value,
  let  date = document.querySelector("#date").value,
    preading = document.querySelector("#pulse").value,
    sbpreading = document.querySelector("#sbp").value,
    dbpreading = document.querySelector("#dbp").value,
    creading = document.querySelector("#cholestrol").value;
  
  let getmonths = ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"],
    d = new Date(),
    month = getmonths[d.getMonth()];
   db.collection("Users").doc(email).collection("All_health_parameters").doc(date).set({
    Month: month,
    Date: date,
    PReading: Number(preading),
    SReading: Number(sbpreading),
    DReading: Number(dbpreading),
    CReading: Number(creading)  
  },{merge:true}).then(() => {
    alert("Recorded successfully");
    window.location.reload();
  })
})}

//Track
//pulse
firebase.auth().onAuthStateChanged(function(user) {
    let email = user.email;
  db.collection("Users").doc(email).collection("All_health_parameters").orderBy('Date', 'desc').get().then(snapshot => {
    snapshot.forEach(doc => {
      let preading = doc.data().PReading,
        sreading = doc.data().SReading,
        dreading = doc.data().DReading,
        creading = doc.data().CReading;
      
      // Pulse track
      if(preading >= 60 && preading <= 100){
        trackbody1.innerHTML += "<div style='display:inline-block; position:relative; left: 60px; top:10px; width:100px; font-size:large;'>" + doc.data().Month + "</div>";
        trackbody1.innerHTML += "<div style='display:inline-block; position:relative; left: 110px; top:10px; width:100px; font-size:large;'>" + doc.data().Date + "</div>";
        trackbody1.innerHTML += "<div style='display:inline-block; position:relative; left: 230px; top:10px; width:100px; font-size:large;'>" + doc.data().PReading + "bpm</div>";
        trackbody1.innerHTML += "<div style='display:inline-block; position:relative; left: 320px; top:10px; width:100px; font-size:large; color:green'><p>Normal</p></div><br/><br/>";
      }
      else if(preading < 60 || preading > 100){
        trackbody1.innerHTML += "<div style='display:inline-block; position:relative; left: 60px; top:10px; width:100px; font-size:large;'>" + doc.data().Month + "</div>";
        trackbody1.innerHTML += "<div style='display:inline-block; position:relative; left: 110px; top:10px; width:100px; font-size:large;'>" + doc.data().Date + "</div>";
        trackbody1.innerHTML += "<div style='display:inline-block; position:relative; left: 230px; top:10px; width:100px; font-size:large;'>" + doc.data().PReading + "bpm</div>";
        trackbody1.innerHTML += "<div style='display:inline-block; position:relative; left: 315px; top:10px; width:100px; font-size:large; color:red'><p>Abnormal</P></div><br/><br/>";
      }
      // Blood pressure track
      if((sreading <= 120) && (dreading <= 80)){
        trackbody2.innerHTML += "<div style='display:inline-block; position:relative; left: 60px; top:10px; width:100px; font-size:large;'>" + doc.data().Month + "</div>";
        trackbody2.innerHTML += "<div style='display:inline-block; position:relative; left: 110px; top:10px; width:100px; font-size:large;'>" + doc.data().Date + "</div>";
        trackbody2.innerHTML += "<div style='display:inline-block; position:relative; left: 200px; top:10px; width:100px; font-size:large;'>" + doc.data().SReading + "/" + doc.data().DReading + "mmHg</div>";
        trackbody2.innerHTML += "<div style='display:inline-block; position:relative; left: 325px; top:10px; width:100px; font-size:large; color:green'><p>Normal</p></div><br/><br/>";
      }
      else if((sreading > 120 && sreading <= 130) && (dreading <= 90)){
        trackbody2.innerHTML += "<div style='display:inline-block; position:relative; left: 60px; top:10px; width:100px; font-size:large;'>" + doc.data().Month + "</div>";
        trackbody2.innerHTML += "<div style='display:inline-block; position:relative; left: 110px; top:10px; width:100px; font-size:large;'>" + doc.data().Date + "</div>";
        trackbody2.innerHTML += "<div style='display:inline-block; position:relative; left: 200px; top:10px; width:100px; font-size:large;'>" + doc.data().SReading + "/" + doc.data().DReading + "mmHg</div>";
        trackbody2.innerHTML += "<div style='display:inline-block; position:relative; left: 320px; top:10px; width:100px; font-size:large; color:darkorange;'><p>Abnormal</p></div><br/><br/>";
      }
      else if((sreading > 130 && sreading <= 140) && (dreading <= 100)){
        trackbody2.innerHTML += "<div style='display:inline-block; position:relative; left: 60px; top:10px; width:100px; font-size:large;'>" + doc.data().Month + "</div>";
        trackbody2.innerHTML += "<div style='display:inline-block; position:relative; left: 110px; top:10px; width:100px; font-size:large;'>" + doc.data().Date + "</div>";
        trackbody2.innerHTML += "<div style='display:inline-block; position:relative; left: 200px; top:10px; width:100px; font-size:large;'>" + doc.data().SReading + "/" + doc.data().DReading + "mmHg</div>";
        trackbody2.innerHTML += "<div style='display:inline-block; position:relative; left: 320px; top:10px; width:100px; font-size:large; color:darkorange;'><p>Abnormal</p></div><br/><br/>";
      }
      else if((sreading > 140) && (dreading > 100)){
        trackbody2.innerHTML += "<div style='display:inline-block; position:relative; left: 60px; top:10px; width:100px; font-size:large;'>" + doc.data().Month + "</div>";
        trackbody2.innerHTML += "<div style='display:inline-block; position:relative; left: 110px; top:10px; width:100px; font-size:large;'>" + doc.data().Date + "</div>";
        trackbody2.innerHTML += "<div style='display:inline-block; position:relative; left: 200px; top:10px; width:100px; font-size:large;'>" + doc.data().SReading + "/" + doc.data().DReading + "mmHg</div>";
        trackbody2.innerHTML += "<div style='display:inline-block; position:relative; left: 325px; top:10px; width:100px; font-size:large; color:red;'><p>Critical</p></div><br/><br/>";
      }
      //Cholesterol track
      if(creading >= 125 && creading <= 200){
        trackbody3.innerHTML += "<div style='display:inline-block; position:relative; left: 60px; top:10px; width:100px; font-size:large;'>" + doc.data().Month + "</div>";
        trackbody3.innerHTML += "<div style='display:inline-block; position:relative; left: 110px; top:10px; width:100px; font-size:large;'>" + doc.data().Date + "</div>";
        trackbody3.innerHTML += "<div style='display:inline-block; position:relative; left: 230px; top:10px; width:100px; font-size:large;'>" + doc.data().CReading + "mg/dl</div>";
        trackbody3.innerHTML += "<div style='display:inline-block; position:relative; left: 320px; top:10px; width:100px; font-size:large; color:green'><p>Normal</p></div><br/><br/>";
      }
      else if(creading > 200){
        trackbody3.innerHTML += "<div style='display:inline-block; position:relative; left: 60px; top:10px; width:100px; font-size:large;'>" + doc.data().Month + "</div>";
        trackbody3.innerHTML += "<div style='display:inline-block; position:relative; left: 110px; top:10px; width:100px; font-size:large;'>" + doc.data().Date + "</div>";
        trackbody3.innerHTML += "<div style='display:inline-block; position:relative; left: 230px; top:10px; width:100px; font-size:large;'>" + doc.data().CReading + "mg/dl</div>";
        trackbody3.innerHTML += "<div style='display:inline-block; position:relative; left: 315px; top:10px; width:100px; font-size:large; color:red'><p>Abnormal</p></div><br/><br/>";
      }
    })
  })
    db.collection("Users").doc(email).collection("All_health_parameters").orderBy('Date', 'desc').limit(1).get().then(snapshot => {
      snapshot.forEach(doc => {
        let preading = doc.data().PReading,
          sreading = doc.data().SReading,
          dreading = doc.data().DReading,
          creading = doc.data().CReading;

      // Pulse summary
      hhealthsummary.innerHTML += "<p style='font-size:20px;position:relative;top:5px;left:75px'><u>Last recorded:</u></p>";
      if(preading >= 60 && preading <= 100) {
        hhealthsummary.innerHTML += "<p style='float:left; font-size:20px; position:relative; top:30px; left:30px'>Pulse:</p><p style='color:green; font-size:20px; display:inline-block;position:relative;left:-25px;top:25px'>" + doc.data().PReading + "bpm</p>"
        }
      else if(preading < 60 || preading > 100) {
        hhealthsummary.innerHTML += "<p style='float:left; font-size:20px; position:relative; top:30px; left:30px'>Pulse:</p><p style='color:red; font-size:20px; display:inline-block;position:relative;left:-25px;top:25px'>" + doc.data().PReading + "bpm</p>"
      }

      // Blood pressure summary
      if((sreading <= 120) && (dreading <= 80)) {
        hhealthsummary.innerHTML += "<p style='float:left; font-size:20px; position:relative; top:60px; left:-20px'>Blood<br>pressure:</p><p style='color:green; font-size:20px; display:inline-block;position:relative;left:-10px;top:55px;'>" + doc.data().SReading + "/" + doc.data().DReading + "mmHg</p>"
      }
      else if((sreading > 120 && sreading <= 130) && (dreading <= 90)) {
        hhealthsummary.innerHTML += "<p style='float:left; font-size:20px; position:relative; top:60px; left:-20px'>Blood<br>pressure:</p><p style='color:darkgoldenrod; font-size:20px; display:inline-block;position:relative;left:-10px;top:55px;'>" + doc.data().SReading + "/" + doc.data().DReading + "mmHg</p>"
      }
      else if((sreading > 130 && sreading <= 140) && (dreading <= 100)) {
        hhealthsummary.innerHTML += "<p style='float:left; font-size:20px; position:relative; top:60px; left:-20px'>Blood<br>pressure:</p><p style='color:darkorange; font-size:20px; display:inline-block;position:relative;left:-10px;top:55px;'>" + doc.data().SReading + "/" + doc.data().DReading + "mmHg</p>"
      }
      else if((sreading > 140) && (dreading > 100)) {
        hhealthsummary.innerHTML += "<p style='float:left; font-size:20px; position:relative; top:60px; left:-20px'>Blood<br>pressure:</p><p style='color:red; font-size:20px; display:inline-block;position:relative;left:-10px;top:55px;'>" + doc.data().SReading + "/" + doc.data().DReading + "mmHg</p>"
      }

        // Cholesterol summary
      if(creading >= 125 && creading <= 200) {
        hhealthsummary.innerHTML += "<p style='float:left; font-size:20px; position:relative; top:65px; left:30px'>Cholesterol:</p><p style='color:green; font-size:20px; display:inline-block;position:relative;left:30px;top:65px'>" + doc.data().CReading + "mg/dl</p>"
      }
      else if(creading > 200) {
        hhealthsummary.innerHTML += "<p style='float:left; font-size:20px; position:relative; top:65px; left:30px'>Cholesterol:</p><p style='color:red; font-size:20px; display:inline-block;position:relative;left:35px;top:65px'>" + doc.data().CReading + "mg/dl</p>"
      }


      //Total Summary
      if((preading >= 60 && preading <= 100) && (sreading <= 120 && dreading <=80) && (creading >=125 && creading <=200)) {
        hhealthsummary.innerHTML += "<p style='float:left;font-size:25px; position:relative; top:100px; left:50px; color:green; border:3px solid green; padding:5px;width:150px;'>Your Heart is:<br>&nbsp&nbsp&nbsp&nbsp&nbsp;Healthy</p>"
      }
      else{
        if(preading < 60){
          hhealthsummary.innerHTML += "<p style='float:left;font-size:20px; position:relative; top:100px; left:50px; color:darkgoldenrod; padding:5px;width:150px;'>Low pulse<br></p>"
        }
        if(preading > 100){
          hhealthsummary.innerHTML += "<p style='color:red; float:left;font-size:20px; position:relative; top:100px; left:50px; padding:5px;width:150px;'>High pulse<br></p>"
        }
        if((sreading > 120 && sreading <= 130) && (dreading <= 90)){
          hhealthsummary.innerHTML += "<p style='color:darkgoldenrod; float:left;font-size:20px; position:relative; top:100px; left:50px; padding:5px;width:150px;'>Elevated Blood pressure<br></p>"
        }
        if((sreading > 130 && sreading <= 140) && (dreading <= 100)){
          hhealthsummary.innerHTML += "<p style='color:orange; float:left;font-size:20px; position:relative; top:100px; left:50px; padding:5px;width:150px;'>High Bp Stage-I<br></p>"
        }
        if((sreading > 140 && sreading <= 180) && (dreading <= 120)){
          hhealthsummary.innerHTML += "<p style='color:red; float:left;font-size:20px; position:relative; top:100px; left:50px; padding:5px;width:150px;'>High Bp Stage-II<br></p>"
        }
        if(sreading > 180 && dreading > 120){
          hhealthsummary.innerHTML += "<p style='color:darkred; float:left;font-size:20px; position:relative; top:100px; left:50px; padding:5px;width:150px;'>High Bp Stage-III<br></p>"
        }
        if(creading > 200) {
          hhealthsummary.innerHTML += "<p style='color:red; float:left;font-size:20px; position:relative; top:100px; left:50px; padding:5px;width:150px;'>High Cholesterol<br></p>"
        }
      }
    })
  })
})