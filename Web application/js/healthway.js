// Link Firebase confidential configuration for connecting firebase.

  // Initialize Firebase
  firebase.initializeApp(firebaseConfig);

  function logout(){
    firebase.auth().signOut();
    window.location="login.html";
    alert("Signed Out");
  }

  function SelectAllDataforHealthway(){
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
          treading = doc.data().TReading;
        let pstatus, bpstatus, cstatus, o2status, rrstatus, gstatus;

        if(treading > 98){
          tstatus = "<p style='color:red;'>High Temperature</p>";
        } else if(treading < 96){
          tstatus = "<p style='color:red;'>Low Temperature</p>";
        } else {
          tstatus = "<p style='color:green;'>Normal</p>";
        }

        if(preading < 60){
          pstatus = "<p style='color:red;'>Low Pulse</p>";
        }else if(preading > 100){
          pstatus= "<p style='color:red;'>High Pulse</p>";
        } else {
          pstatus = "<p style='color:green;'>Normal</p>";
        }

        if(o2reading < 98){
          o2status = "<p style='color:red;;'>Hypoxemia</p>";
        } else {
          o2status = "<p style='color:green;'>Normal</p>";
        }

        if((greading > 140 && greading <= 199)){
          gstatus = "<p style='color:red;'>Pre-Diabetes</p>";
        }else if(greading >= 200){
          gstatus = "<p style='color:crimson;'>Diabetes</p>";
        } else {
          gstatus = "<p style='color:green;'>Normal</p>";
        }

        if(rrreading > 20){
          rrstatus = "<p style='color:crimson;'>High Respiration rate</p>";
        }else if(rrreading < 12){
          rrstatus = "<p style='color:red;'>low Respiration rate</p>";
        } else {
          rrstatus = "<p style='color:green;'>Normal</p>";
        }

        if(creading > 200){
          cstatus = "<p style='color:crimson;'>High Cholesterol level</p>";
        }else if(creading < 125){
          cstatus = "<p style='color:red;'>low Cholesterol level</p>";
        } else {
          cstatus = "<p style='color:green;'>Normal</p>";
        }

        if((sreading > 120 && sreading < 130) && (dreading <= 90)){
          bpstatus = "<p style='color:red;'>Elevated BP</p>";
        }else if((sreading >= 130 && sreading < 140) && (dreading <= 100)){
          bpstatus = "<p style='color:red;'>High BP stage-I</p>";
        }else if((sreading >= 140 && sreading < 180) && (dreading <= 120)){
          bpstatus = "<p style='color:crimson;'>High BP stage-II</p>";
        }else if(sreading > 180 && dreading > 120){
          bpstatus = "<p style='color:red;'>High BP stage-III</p>";
        } else {
          bpstatus = "<p style='color:green;'>Normal</p>";
        }

        AddItemsToTable(treading, preading, sreading, dreading, creading, o2reading, rrreading, greading, tstatus, pstatus, bpstatus, cstatus, o2status, rrstatus, gstatus);
      })
    })})
  }

  window.onload = SelectAllDataforHealthway;

  function AddItemsToTable(treading, preading, sreading, dreading, creading, o2reading, rrreading, greading, tstatus, pstatus, bpstatus, cstatus, o2status, rrstatus, gstatus){
    let trowt = document.getElementById('tem');
    let tdt1 = document.createElement('td');
    let tdt2 = document.createElement('td');
    let tdt3 = document.createElement('td');
    let tdt4 = document.createElement('td');
    
    tdt1.innerHTML = "Temperature";
    tdt2.innerHTML = treading + "&#8451;";
    tdt3.innerHTML = tstatus;
    tdt4.innerHTML = "96 - 98" + "&#8451;";
    trowt.appendChild(tdt1);
    trowt.appendChild(tdt2);
    trowt.appendChild(tdt3);
    trowt.appendChild(tdt4);

    let trowp = document.getElementById('pul');
    let tdp1 = document.createElement('td');
    let tdp2 = document.createElement('td');
    let tdp3 = document.createElement('td');
    let tdp4 = document.createElement('td');
    
    tdp1.innerHTML = "Pulse";
    tdp2.innerHTML = preading + "bpm";
    tdp3.innerHTML = pstatus;
    tdp4.innerHTML = "60 - 100bpm";
    trowp.appendChild(tdp1);
    trowp.appendChild(tdp2);
    trowp.appendChild(tdp3);
    trowp.appendChild(tdp4);

    let trowg = document.getElementById('glu');
    let tdg1 = document.createElement('td');
    let tdg2 = document.createElement('td');
    let tdg3 = document.createElement('td');
    let tdg4 = document.createElement('td');
    
    tdg1.innerHTML = "Glucose";
    tdg2.innerHTML = greading + "mg/dl";
    tdg3.innerHTML = gstatus;
    tdg4.innerHTML = "Below 140mg/dl";
    trowg.appendChild(tdg1);
    trowg.appendChild(tdg2);
    trowg.appendChild(tdg3);
    trowg.appendChild(tdg4);

    let trowb = document.getElementById('bp');
    let tdb1 = document.createElement('td');
    let tdb2 = document.createElement('td');
    let tdb3 = document.createElement('td');
    let tdb4 = document.createElement('td');
    
    tdb1.innerHTML = "Blood pressure";
    tdb2.innerHTML = sreading + "/" + dreading + "mmHg";
    tdb3.innerHTML = bpstatus;
    tdb4.innerHTML = "Below 120/80mmHg";
    trowb.appendChild(tdb1);
    trowb.appendChild(tdb2);
    trowb.appendChild(tdb3);
    trowb.appendChild(tdb4);

    let trows = document.getElementById('spo');
    let tds1 = document.createElement('td');
    let tds2 = document.createElement('td');
    let tds3 = document.createElement('td');
    let tds4 = document.createElement('td');
    
    tds1.innerHTML = "SpO2";
    tds2.innerHTML = o2reading + "%";
    tds3.innerHTML = o2status;
    tds4.innerHTML = "98 - 100%";
    trows.appendChild(tds1);
    trows.appendChild(tds2);
    trows.appendChild(tds3);
    trows.appendChild(tds4);

    let trowc = document.getElementById('cho');
    let tdc1 = document.createElement('td');
    let tdc2 = document.createElement('td');
    let tdc3 = document.createElement('td');
    let tdc4 = document.createElement('td');
    
    tdc1.innerHTML = "Cholesterol";
    tdc2.innerHTML = creading + "mg/dl";
    tdc3.innerHTML = bpstatus;
    tdc4.innerHTML = "15 - 200mg/dl";
    trowc.appendChild(tdc1);
    trowc.appendChild(tdc2);
    trowc.appendChild(tdc3);
    trowc.appendChild(tdc4);

    let trowr = document.getElementById('res');
    let tdr1 = document.createElement('td');
    let tdr2 = document.createElement('td');
    let tdr3 = document.createElement('td');
    let tdr4 = document.createElement('td');
    
    tdr1.innerHTML = "Respiration rate";
    tdr2.innerHTML = rrreading + "brpm";
    tdr3.innerHTML = rrstatus;
    tdr4.innerHTML = "12 - 20brpm";
    trowr.appendChild(tdr1);
    trowr.appendChild(tdr2);
    trowr.appendChild(tdr3);
    trowr.appendChild(tdr4);
  }
