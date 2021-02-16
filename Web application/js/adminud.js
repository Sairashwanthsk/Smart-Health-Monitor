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
    window.location="adminlogin.html";
    alert("Signed Out");
  }

  function SelectAllDataforud(){
    firebase.firestore().collection("Users").get().then(snapshot => {
      snapshot.forEach(doc => {
        let name = doc.data().Name,
          email = doc.data().Email,
          age = doc.data().Age,
          alcoholic = doc.data().Alcoholic,
          asthma = doc.data().Asthma,
          birthdate = doc.data().Birthdate,
          bloodgroup = doc.data().Blood_Group,
          bronchiectasis = doc.data().Bronchiectasis,
          chd = doc.data().Coronary_Artery_Disease,
          currentmedication = doc.data().Current_medication,
          diabetes = doc.data().Diabetes,
          drugallergies = doc.data().Drug_allergies,
          gender = doc.data().Gender,
          height = doc.data().Height,
          hypoxemia = doc.data().Hypoxemia,
          otherillness = doc.data().Other_Illness,
          weight = doc.data().Weight;

          AddItemsToudTable(name, email, age, alcoholic, asthma, birthdate, bloodgroup, bronchiectasis, chd, currentmedication, diabetes, drugallergies, gender, height, weight, hypoxemia, otherillness);
      })})
  }

  window.onload = SelectAllDataforud;

  function AddItemsToudTable(name, email, age, alcoholic, asthma, birthdate, bloodgroup, bronchiectasis, chd, currentmedication, diabetes, drugallergies, gender, height, weight, hypoxemia, otherillness){
    let tbody = document.getElementById('udbody');
    let trow = document.createElement('tr');
    let td1 = document.createElement('td');
    let td2 = document.createElement('td');
    let td3 = document.createElement('td');
    let td4 = document.createElement('td');
    let td5 = document.createElement('td');
    let td6 = document.createElement('td');
    let td7 = document.createElement('td');
    let td8 = document.createElement('td');
    let td9 = document.createElement('td');
    let td10 = document.createElement('td');
    let td11 = document.createElement('td');
    let td12 = document.createElement('td');
    let td13 = document.createElement('td');
    let td14 = document.createElement('td');
    let td15 = document.createElement('td');
    let td16 = document.createElement('td');
    let td17 = document.createElement('td');

    td1.innerHTML = name;
    td2.innerHTML = email;
    td3.innerHTML = gender;
    td4.innerHTML = birthdate;
    td5.innerHTML = bloodgroup;
    td6.innerHTML = age;
    td7.innerHTML = height;
    td8.innerHTML = weight;
    td9.innerHTML = alcoholic;
    td10.innerHTML = diabetes;
    td11.innerHTML = asthma;
    td12.innerHTML = bronchiectasis;
    td13.innerHTML = chd;
    td14.innerHTML = hypoxemia;
    td15.innerHTML = otherillness;
    td16.innerHTML = currentmedication;
    td17.innerHTML = drugallergies;
    trow.appendChild(td1);
    trow.appendChild(td2);
    trow.appendChild(td3);
    trow.appendChild(td4);
    trow.appendChild(td5);
    trow.appendChild(td6);
    trow.appendChild(td7);
    trow.appendChild(td8);
    trow.appendChild(td9);
    trow.appendChild(td10);
    trow.appendChild(td11);
    trow.appendChild(td12);
    trow.appendChild(td13);
    trow.appendChild(td14);
    trow.appendChild(td15);
    trow.appendChild(td16);
    trow.appendChild(td17);
    tbody.appendChild(trow);
    
    
  }