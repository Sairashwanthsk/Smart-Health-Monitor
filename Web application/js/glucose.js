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
  document.querySelector(".gluform").addEventListener("submit", record);  
  function record(e) {
    e.preventDefault();
    
    firebase.auth().onAuthStateChanged(function(user) {
      let email = user.email;
    //let name = document.querySelector(".name").value,
    let  date = document.querySelector(".dates").value,
      reading = document.querySelector(".glucose_reading").value;
    
    let getmonths = ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"],
      d = new Date(),
      month = getmonths[d.getMonth()];    
      db.collection("Users").doc(email).collection("All_health_parameters").doc(date).set({
        Month: month,
        Date: date,
        GReading: Number(reading)
      },{merge:true}).then(() => {
          alert("Recorded successfully");
          window.location.reload();
      })
    })
  }
  
  
  firebase.auth().onAuthStateChanged(function(user) {
    let email = user.email;
    db.collection("Users").doc(email).collection("All_health_parameters").orderBy('Date', 'desc').get().then(snapshot => {
      snapshot.forEach(doc => {
        let reading = doc.data().GReading;
  
          // Track
          if(reading <= 140){
            trackbody.innerHTML += "<div style='display:inline-block; position:relative; left: 60px; top:10px; width:100px; font-size:large;'>" + doc.data().Month + "</div>";
            trackbody.innerHTML += "<div style='display:inline-block; position:relative; left: 130px; top:10px; width:100px; font-size:large;'>" + doc.data().Date + "</div>";
            trackbody.innerHTML += "<div style='display:inline-block; position:relative; left: 220px; top:10px; width:100px; font-size:large;'>" + doc.data().GReading + "mg/dl</div>";
            trackbody.innerHTML += "<div style='display:inline-block; position:relative; left: 350px; top:10px; width:100px; font-size:large; color:green'><p>Normal</p></div><br/><br/>";
          }
          else if(reading > 140 && reading < 200){
            trackbody.innerHTML += "<div style='display:inline-block; position:relative; left: 60px; top:10px; width:100px; font-size:large;'>" + doc.data().Month + "</div>";
            trackbody.innerHTML += "<div style='display:inline-block; position:relative; left: 130px; top:10px; width:100px; font-size:large;'>" + doc.data().Date + "</div>";
            trackbody.innerHTML += "<div style='display:inline-block; position:relative; left: 220px; top:10px; width:100px; font-size:large;'>" + doc.data().GReading + "mg/dl</div>";
            trackbody.innerHTML += "<div style='display:inline-block; position:relative; left: 330px; top:10px; width:100px; font-size:large; color:darkorange'><p>Pre-Diabetes</p></div><br/><br/>";
          }
          else if(reading >= 200){
            trackbody.innerHTML += "<div style='display:inline-block; position:relative; left: 60px; top:10px; width:100px; font-size:large;'>" + doc.data().Month + "</div>";
            trackbody.innerHTML += "<div style='display:inline-block; position:relative; left: 130px; top:10px; width:100px; font-size:large;'>" + doc.data().Date + "</div>";
            trackbody.innerHTML += "<div style='display:inline-block; position:relative; left: 220px; top:10px; width:100px; font-size:large;'>" + doc.data().GReading + "mg/dl</div>";
            trackbody.innerHTML += "<div style='display:inline-block; position:relative; left: 345px; top:10px; width:100px; font-size:large; color:red'><p>Diabetes</p></div><br/><br/>";
          }
      })
    })
    db.collection("Users").doc(email).collection("All_health_parameters").orderBy('Date', 'desc').limit(1).get().then(snapshot => {
      snapshot.forEach(doc => {
          let reading = doc.data().GReading;
        
        if(reading <= 140){
            summary.innerHTML += "<p style='font-size:20px;position:relative;top:13px;left:15px'>Last recorded:</p><p style='color:green; font-size:20px; display:inline-block;position:relative;left:140px;top:-10px'>" + doc.data().GReading + "mg/dl</p><br /><h4 style='color:green;border:3px solid green;width:100px;padding:5px;position:relative;left:70px;text-align:center;'>NORMAL</h4>";
        }
        else if(reading > 140 && reading <= 200){
            summary.innerHTML += "<p style='font-size:20px;position:relative;top:13px;left:15px'>Last recorded:</p><p style='color:orange; font-size:20px; display:inline-block;position:relative;left:140px;top:-10px'>" + doc.data().GReading + "mg/dl</p><br /><h4 style='color:orange;border:3px solid orange;width:150px;padding:5px;position:relative;left:55px;text-align:center;'>Pre-Diabetes</h4>";
        }
        else if(reading > 200){
            summary.innerHTML += "<p style='font-size:20px;position:relative;top:13px;left:15px'>Last recorded:</p><p style='color:red; font-size:20px; display:inline-block;position:relative;left:140px;top:-10px'>" + doc.data().GReading + "mg/dl</p><br /><h4 style='color:red;border:3px solid red;width:100px;padding:5px;position:relative;left:70px;text-align:center;'>Diabetes</h4>";
        }
      })
    })
  })