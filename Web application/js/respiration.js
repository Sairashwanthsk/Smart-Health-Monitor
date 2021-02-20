// Link Firebase confidential configuration for connecting firebase.

  // Initialize Firebase
  firebase.initializeApp(firebaseConfig);
  
  
    function logout(){
      firebase.auth().signOut();
      window.location="login.html";
      alert("Signed Out");
    }

  
  let db = firebase.firestore();

  // Creating Database
document.querySelector(".resform").addEventListener("submit", record);  
function record(e) {
  e.preventDefault();
  
  firebase.auth().onAuthStateChanged(function(user) {
    let email = user.email;
  //let name = document.querySelector(".name").value,
  let  date = document.querySelector("#date").value,
    rrreading = document.querySelector("#respiration_rate").value,
    o2reading = document.querySelector("#spo2_reading").value;
  
  let getmonths = ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"],
    d = new Date(),
    month = getmonths[d.getMonth()];    

  db.collection("Users").doc(email).collection("All_health_parameters").doc(date).set({
    Month: month,
    Date: date,
    O2Reading: Number(o2reading),
    RRReading: Number(rrreading)
  },{merge:true}).then(() => {
    alert("Recorded Successfully");
    window.location.reload();
  })
})}

firebase.auth().onAuthStateChanged(function(user) {
    let email = user.email;
  db.collection("Users").doc(email).collection("All_health_parameters").orderBy('Date', 'desc').get().then(snapshot => {
    snapshot.forEach(doc => {
      let o2reading = doc.data().O2Reading,
        rrreading = doc.data().RRReading;
        
      // SpO2 track
      if(o2reading >= 98){
        trackbody1.innerHTML += "<div style='display:inline-block; position:relative; left: 60px; top:10px; width:100px; font-size:large;'>" + doc.data().Month + "</div>";
        trackbody1.innerHTML += "<div style='display:inline-block; position:relative; left: 130px; top:10px; width:100px; font-size:large;'>" + doc.data().Date + "</div>";
        trackbody1.innerHTML += "<div style='display:inline-block; position:relative; left: 240px; top:10px; width:100px; font-size:large;'>" + doc.data().O2Reading + "%</div>";
        trackbody1.innerHTML += "<div style='display:inline-block; position:relative; left: 320px; top:10px; width:100px; font-size:large; color:green'><p>Normal</p></div><br/><br/>";
      }
      else if(o2reading < 98){
        trackbody1.innerHTML += "<div style='display:inline-block; position:relative; left: 60px; top:10px; width:100px; font-size:large;'>" + doc.data().Month + "</div>";
        trackbody1.innerHTML += "<div style='display:inline-block; position:relative; left: 130px; top:10px; width:100px; font-size:large;'>" + doc.data().Date + "</div>";
        trackbody1.innerHTML += "<div style='display:inline-block; position:relative; left: 240px; top:10px; width:100px; font-size:large;'>" + doc.data().O2Reading + "%</div>";
        trackbody1.innerHTML += "<div style='display:inline-block; position:relative; left: 315px; top:10px; width:100px; font-size:large; color:red'><p>Abnormal</p></div><br/><br/>";
      }

      // Respiration rate track
      if(rrreading >= 12 && rrreading <=20){
        trackbody2.innerHTML += "<div style='display:inline-block; position:relative; left: 60px; top:10px; width:100px; font-size:large;'>" + doc.data().Month + "</div>";
        trackbody2.innerHTML += "<div style='display:inline-block; position:relative; left: 130px; top:10px; width:100px; font-size:large;'>" + doc.data().Date + "</div>";
        trackbody2.innerHTML += "<div style='display:inline-block; position:relative; left: 220px; top:10px; width:100px; font-size:large;'>" + doc.data().RRReading + "brpm</div>";
        trackbody2.innerHTML += "<div style='display:inline-block; position:relative; left: 340px; top:10px; width:100px; font-size:large; color:green'><p>Normal</p></div><br/><br/>";
      }
      else if(rrreading > 20 || rrreading < 12){
        trackbody2.innerHTML += "<div style='display:inline-block; position:relative; left: 60px; top:10px; width:100px; font-size:large;'>" + doc.data().Month + "</div>";
        trackbody2.innerHTML += "<div style='display:inline-block; position:relative; left: 130px; top:10px; width:100px; font-size:large;'>" + doc.data().Date + "</div>";
        trackbody2.innerHTML += "<div style='display:inline-block; position:relative; left: 220px; top:10px; width:100px; font-size:large;'>" + doc.data().RRReading + "brpm</div>";
        trackbody2.innerHTML += "<div style='display:inline-block; position:relative; left: 335px; top:10px; width:100px; font-size:large; color:red'><p>Abnormal</p></div><br/><br/>";
      }
    })})
    db.collection("Users").doc(email).collection("All_health_parameters").orderBy('Date', 'desc').limit(1).get().then(snapshot => {
      snapshot.forEach(doc => {
        let o2reading = doc.data().O2Reading,
          rrreading = doc.data().RRReading,
          preading = doc.data().PReading;
      
      // SpO2 summary      
      if(o2reading >= 98) {
        rhealthsummary.innerHTML += "<p style='font-size:20px;position:relative;top:5px;left:75px'><u>Last recorded:</u></p>";
        rhealthsummary.innerHTML += "<p style='float:left; font-size:20px; position:relative; top:30px; left:30px'>SpO2:</p><p style='color:green; font-size:20px; display:inline-block;position:relative;left:-40px;top:25px'>" + doc.data().O2Reading + "%</p>"
        }
      else if(o2reading < 98) {
        rhealthsummary.innerHTML += "<p style='font-size:20px;position:relative;top:5px;left:75px'><u>Last recorded:</u></p>";
        rhealthsummary.innerHTML += "<p style='float:left; font-size:20px; position:relative; top:30px; left:30px'>SpO2:</p><p style='color:red; font-size:20px; display:inline-block;position:relative;left:-40px;top:25px'>" + doc.data().O2Reading + "%</p>"
      }

      
      //Respiration rate summary
      if(rrreading >= 12 && rrreading <=20) {
        rhealthsummary.innerHTML += "<p style='float:left; font-size:20px; position:relative; top:60px; left:-20px'>Respiration<br>rate:</p><p style='color:green; font-size:20px; display:inline-block;position:relative;left:-30px;top:70px;'>" + doc.data().RRReading + "brpm</p>"
      }
      else if(rrreading > 20 || rrreading < 12) {
        rhealthsummary.innerHTML += "<p style='float:left; font-size:20px; position:relative; top:60px; left:-20px'>Respiration<br>rate:</p><p style='color:red; font-size:20px; display:inline-block;position:relative;left:-30px;top:70px;'>" + doc.data().RRReading + "brpm</p>"
      }

      
      // Total summary
      if(o2reading >= 98 && (rrreading >= 12 && rrreading <=20)) {
        rhealthsummary.innerHTML += "<p style='float:left;font-size:25px; position:relative; top:80px; left:50px; color:green; border:3px solid green; padding:5px;width:150px;'>Your Lung is:<br>&nbsp&nbsp&nbsp&nbsp&nbsp;Healthy</p>"
      }
      else if((o2reading >= 92 && o2reading <= 95) && (rrreading > 20 && rrreading <= 30) && (preading > 100 && preading <= 125)) {
        rhealthsummary.innerHTML += "<p style='float:left;font-size:20px; position:relative; top:80px; left:50px; color:darkred; border:3px solid darkred; padding:5px;width:150px;'>Your Lung is:<br>affected by<br>&nbsp&nbsp&nbsp;'Asthma'</p>"
      }
      else{
        if(o2reading < 98) {
          rhealthsummary.innerHTML += "<p style='float:left;font-size:20px; position:relative; top:80px; left:50px; color:red; border:3px solid red; padding:5px;width:150px;'>Hypoxemia</p>"
        }
        if(rrreading > 20){
          rhealthsummary.innerHTML += "<p style='float:left;font-size:20px; position:relative; top:80px; left:50px; color:red; border:3px solid red; padding:5px;width:150px;'>High Respiration rate</p>"
        }
        if(rrreading < 12){
          rhealthsummary.innerHTML += "<p style='float:left;font-size:20px; position:relative; top:80px; left:50px; color:red; border:3px solid red; padding:5px;width:150px;'>low Respiration rate</p>"
        }
      }
    })
  })})

