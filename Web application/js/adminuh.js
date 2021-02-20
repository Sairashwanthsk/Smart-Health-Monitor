// Link Firebase confidential configuration for connecting firebase.

  // Initialize Firebase
  firebase.initializeApp(firebaseConfig);  
  
  // Logout
  function logout(){
    firebase.auth().signOut();
    window.location="adminlogin.html";
    alert("Signed Out");
  }

  function search(){
    let input, filter, table, tr, td, i, textvalue;

    input = document.getElementById("searchname");
    filter = input.value.toUpperCase();
    table = document.getElementById("table");
    tr = table.getElementsByTagName("tr");

    for (i = 0; i < tr.length; i++) {
      td = tr[i].getElementsByTagName("td")[0];
      if (td) {
        txtValue = td.textContent || td.innerText;
        if (txtValue.toUpperCase().indexOf(filter) > -1) {
          tr[i].style.display = "";
        } else {
          tr[i].style.display = "none";
        }
      }
    }
  }

  function selectData(){
    var defaultdate = new Date(); 
      var day = defaultdate.getDate(); 
      var month = defaultdate.getMonth() + 1; 
      var year = defaultdate.getFullYear(); 
      if (month < 10) month = "0" + month; 
      if (day < 10) day = "0" + day; 
      var today = year + "-" + month + "-" + day; 
      document.getElementById("date").value = today;

      firebase.firestore().collection("Users").get().then(snapshot => {
        snapshot.forEach(doc => {
          let name = doc.data().Name,
            email = doc.data().Email;
          
        
        firebase.firestore().collection("Users").doc(email).collection("All_health_parameters").orderBy('Date', 'desc').limit(1).get().then(snapshot => {
          snapshot.forEach(doc => {
            
          let date = doc.data().Date,
          preading = doc.data().PReading,
          sreading = doc.data().SReading,
          dreading = doc.data().DReading,
          creading = doc.data().CReading,
          o2reading = doc.data().O2Reading,
          rrreading = doc.data().RRReading,
          greading = doc.data().GReading;
          let hstatus, pstatus, bpstatus, cstatus, o2status, rrstatus, gstatus;
          console.log(email);
  
          if((preading >= 60 && preading <= 100) && (o2reading >= 98) && (greading <= 140) && (rrreading >= 12 && rrreading <=20) && (creading >= 125 && creading <=200) && (sreading <= 120 && dreading <= 80)){
            hstatus = "<p style='color:green;'>Healthy</p>";
          } else if((preading || sreading || dreading || creading || o2reading || rrreading || greading) == undefined){
            hstatus = "<p style='color:black;'>Not updated</p>";
          } else {
            hstatus = "<p style='color:red;'>Unhealthy</p>";
          }
  
          if(preading < 60){
            pstatus = "<p style='color:orange;'>Low Pulse</p>";
          }else if(preading > 100){
            pstatus= "<p style='color:orange;'>High Pulse</p>";
          } else if(preading == undefined){
            pstatus = "<p style='color:black;'>Not Updated</p>";
          } else {
            pstatus = "<p style='color:green;'>Normal</p>";
          }
  
          if(o2reading < 98){
            o2status = "<p style='color:orange;;'>Hypoxemia</p>";
          }else if(o2reading == undefined){
            o2status = "<p style='color:black;'>Not Updated</p>";
          } else {
            o2status = "<p style='color:green;'>Normal</p>";
          }
  
          if((greading > 140 && greading <= 199)){
            gstatus = "<p style='color:orange;'>Pre-Diabetes</p>";
          }else if(greading >= 200){
            gstatus = "<p style='color:crimson;'>Diabetes</p>";
          }else if(greading == undefined){
            gstatus = "<p style='color:black;'>Not Updated</p>";
          } else {
            gstatus = "<p style='color:green;'>Normal</p>";
          }
  
          if(rrreading > 20){
            rrstatus = "<p style='color:crimson;'>High Respiration rate</p>";
          }else if(rrreading < 12){
            rrstatus = "<p style='color:orange;'>low Respiration rate</p>";
          }else if(rrreading == undefined){
            rrstatus = "<p style='color:black;'>Not Updated</p>";
          } else {
            rrstatus = "<p style='color:green;'>Normal</p>";
          }
  
          if(creading > 200){
            cstatus = "<p style='color:crimson;'>High Cholesterol level</p>";
          }else if(creading < 125){
            cstatus = "<p style='color:orange;'>low Cholesterol level</p>";
          }else if(creading == undefined){
            cstatus = "<p style='color:black;'>Not Updated</p>";
          } else {
            cstatus = "<p style='color:green;'>Normal</p>";
          }
  
          if((sreading > 120 && sreading < 130) && (dreading <= 90)){
            bpstatus = "<p style='color:orange;'>Elevated BP</p>";
          }else if((sreading >= 130 && sreading < 140) && (dreading <= 100)){
            bpstatus = "<p style='color:orange;'>High BP stage-I</p>";
          }else if((sreading >= 140 && sreading < 180) && (dreading <= 120)){
            bpstatus = "<p style='color:crimson;'>High BP stage-II</p>";
          }else if(sreading > 180 && dreading > 120){
            bpstatus = "<p style='color:red;'>High BP stage-III</p>";
          }else if((sreading || dreading) == undefined){
            bpstatus = "<p style='color:black;'>Not Updated</p>";
          } else {
            bpstatus = "<p style='color:green;'>Normal</p>";
          }
      
  
          AddItemsToTable(name, date, hstatus, pstatus, bpstatus, cstatus, o2status, rrstatus, gstatus);
        })
      })
    })})
      document.getElementById("pick").addEventListener("change", function() {
        let option = this.value;
        $("#uhbody").empty();
      if(option == "all"){
        firebase.firestore().collection("Users").get().then(snapshot => {
          snapshot.forEach(doc => {
            let name = doc.data().Name,
              email = doc.data().Email;
            
          
          firebase.firestore().collection("Users").doc(email).collection("All_health_parameters").orderBy('Date', 'desc').limit(1).get().then(snapshot => {
            snapshot.forEach(doc => {
            
            let date = doc.data().Date,
            preading = doc.data().PReading,
            sreading = doc.data().SReading,
            dreading = doc.data().DReading,
            creading = doc.data().CReading,
            o2reading = doc.data().O2Reading,
            rrreading = doc.data().RRReading,
            greading = doc.data().GReading;
            let hstatus, pstatus, bpstatus, cstatus, o2status, rrstatus, gstatus;
            console.log(email);
    
            if((preading >= 60 && preading <= 100) && (o2reading >= 98) && (greading <= 140) && (rrreading >= 12 && rrreading <=20) && (creading >= 125 && creading <=200) && (sreading <= 120 && dreading <= 80)){
              hstatus = "<p style='color:green;'>Healthy</p>";
            } else if((preading || sreading || dreading || creading || o2reading || rrreading || greading) == undefined){
              hstatus = "<p style='color:black;'>Not updated</p>";
            } else {
              hstatus = "<p style='color:red;'>Unhealthy</p>";
            }
    
            if(preading < 60){
              pstatus = "<p style='color:orange;'>Low Pulse</p>";
            }else if(preading > 100){
              pstatus= "<p style='color:orange;'>High Pulse</p>";
            } else if(preading == undefined){
              pstatus = "<p style='color:black;'>Not Updated</p>";
            } else {
              pstatus = "<p style='color:green;'>Normal</p>";
            }
    
            if(o2reading < 98){
              o2status = "<p style='color:orange;;'>Hypoxemia</p>";
            }else if(o2reading == undefined){
              o2status = "<p style='color:black;'>Not Updated</p>";
            } else {
              o2status = "<p style='color:green;'>Normal</p>";
            }
    
            if((greading > 140 && greading <= 199)){
              gstatus = "<p style='color:orange;'>Pre-Diabetes</p>";
            }else if(greading >= 200){
              gstatus = "<p style='color:crimson;'>Diabetes</p>";
            }else if(greading == undefined){
              gstatus = "<p style='color:black;'>Not Updated</p>";
            } else {
              gstatus = "<p style='color:green;'>Normal</p>";
            }
    
            if(rrreading > 20){
              rrstatus = "<p style='color:crimson;'>High Respiration rate</p>";
            }else if(rrreading < 12){
              rrstatus = "<p style='color:orange;'>low Respiration rate</p>";
            }else if(rrreading == undefined){
              rrstatus = "<p style='color:black;'>Not Updated</p>";
            } else {
              rrstatus = "<p style='color:green;'>Normal</p>";
            }
    
            if(creading > 200){
              cstatus = "<p style='color:crimson;'>High Cholesterol level</p>";
            }else if(creading < 125){
              cstatus = "<p style='color:orange;'>low Cholesterol level</p>";
            }else if(creading == undefined){
              cstatus = "<p style='color:black;'>Not Updated</p>";
            } else {
              cstatus = "<p style='color:green;'>Normal</p>";
            }
    
            if((sreading > 120 && sreading < 130) && (dreading <= 90)){
              bpstatus = "<p style='color:orange;'>Elevated BP</p>";
            }else if((sreading >= 130 && sreading < 140) && (dreading <= 100)){
              bpstatus = "<p style='color:orange;'>High BP stage-I</p>";
            }else if((sreading >= 140 && sreading < 180) && (dreading <= 120)){
              bpstatus = "<p style='color:crimson;'>High BP stage-II</p>";
            }else if(sreading > 180 && dreading > 120){
              bpstatus = "<p style='color:red;'>High BP stage-III</p>";
            }else if((sreading || dreading) == undefined){
              bpstatus = "<p style='color:black;'>Not Updated</p>";
            } else {
              bpstatus = "<p style='color:green;'>Normal</p>";
            }
        
    
            AddItemsToTable(name, date, hstatus, pstatus, bpstatus, cstatus, o2status, rrstatus, gstatus);
          })
        })
      })})
      } else if(option == "bydate"){

        firebase.firestore().collection("Users").get().then(snapshot => {
          snapshot.forEach(doc => {
            let name = doc.data().Name,
              email = doc.data().Email;
            
          
          firebase.firestore().collection("Users").doc(email).collection("All_health_parameters").doc(today).get().then(doc => {
            
            let preading = doc.data().PReading,
            sreading = doc.data().SReading,
            dreading = doc.data().DReading,
            creading = doc.data().CReading,
            o2reading = doc.data().O2Reading,
            rrreading = doc.data().RRReading,
            greading = doc.data().GReading;
            let hstatus, pstatus, bpstatus, cstatus, o2status, rrstatus, gstatus;
            console.log(email);

            if((preading >= 60 && preading <= 100) && (o2reading >= 98) && (greading <= 140) && (rrreading >= 12 && rrreading <=20) && (creading >= 125 && creading <=200) && (sreading <= 120 && dreading <= 80)){
              hstatus = "<p style='color:green;'>Healthy</p>";
            } else if((preading || sreading || dreading || creading || o2reading || rrreading || greading) == undefined){
              hstatus = "<p style='color:black;'>Not updated</p>";
            } else {
              hstatus = "<p style='color:red;'>Unhealthy</p>";
            }

            if(preading < 60){
              pstatus = "<p style='color:orange;'>Low Pulse</p>";
            }else if(preading > 100){
              pstatus= "<p style='color:orange;'>High Pulse</p>";
            } else if(preading == undefined){
              pstatus = "<p style='color:black;'>Not Updated</p>";
            } else {
              pstatus = "<p style='color:green;'>Normal</p>";
            }

            if(o2reading < 98){
              o2status = "<p style='color:orange;;'>Hypoxemia</p>";
            }else if(o2reading == undefined){
              o2status = "<p style='color:black;'>Not Updated</p>";
            } else {
              o2status = "<p style='color:green;'>Normal</p>";
            }

            if((greading > 140 && greading <= 199)){
              gstatus = "<p style='color:orange;'>Pre-Diabetes</p>";
            }else if(greading >= 200){
              gstatus = "<p style='color:crimson;'>Diabetes</p>";
            }else if(greading == undefined){
              gstatus = "<p style='color:black;'>Not Updated</p>";
            } else {
              gstatus = "<p style='color:green;'>Normal</p>";
            }

            if(rrreading > 20){
              rrstatus = "<p style='color:crimson;'>High Respiration rate</p>";
            }else if(rrreading < 12){
              rrstatus = "<p style='color:orange;'>low Respiration rate</p>";
            }else if(rrreading == undefined){
              rrstatus = "<p style='color:black;'>Not Updated</p>";
            } else {
              rrstatus = "<p style='color:green;'>Normal</p>";
            }

            if(creading > 200){
              cstatus = "<p style='color:crimson;'>High Cholesterol level</p>";
            }else if(creading < 125){
              cstatus = "<p style='color:orange;'>low Cholesterol level</p>";
            }else if(creading == undefined){
              cstatus = "<p style='color:black;'>Not Updated</p>";
            } else {
              cstatus = "<p style='color:green;'>Normal</p>";
            }

            if((sreading > 120 && sreading < 130) && (dreading <= 90)){
              bpstatus = "<p style='color:orange;'>Elevated BP</p>";
            }else if((sreading >= 130 && sreading < 140) && (dreading <= 100)){
              bpstatus = "<p style='color:orange;'>High BP stage-I</p>";
            }else if((sreading >= 140 && sreading < 180) && (dreading <= 120)){
              bpstatus = "<p style='color:crimson;'>High BP stage-II</p>";
            }else if(sreading > 180 && dreading > 120){
              bpstatus = "<p style='color:red;'>High BP stage-III</p>";
            }else if((sreading || dreading) == undefined){
              bpstatus = "<p style='color:black;'>Not Updated</p>";
            } else {
              bpstatus = "<p style='color:green;'>Normal</p>";
            }
        

            AddItemsToTable(name, today, hstatus, pstatus, bpstatus, cstatus, o2status, rrstatus, gstatus);
          })
        })})

        document.getElementById("date").addEventListener("change", function() {
          let date = this.value;
          $("#uhbody").empty();
          firebase.firestore().collection("Users").get().then(snapshot => {
            snapshot.forEach(doc => {
              let name = doc.data().Name,
                email = doc.data().Email;
              
            
            firebase.firestore().collection("Users").doc(email).collection("All_health_parameters").doc(date).get().then(doc => {
              
              let preading = doc.data().PReading,
              sreading = doc.data().SReading,
              dreading = doc.data().DReading,
              creading = doc.data().CReading,
              o2reading = doc.data().O2Reading,
              rrreading = doc.data().RRReading,
              greading = doc.data().GReading;
              let hstatus, pstatus, bpstatus, cstatus, o2status, rrstatus, gstatus;
              console.log(email);

              if((preading >= 60 && preading <= 100) && (o2reading >= 98) && (greading <= 140) && (rrreading >= 12 && rrreading <=20) && (creading >= 125 && creading <=200) && (sreading <= 120 && dreading <= 80)){
                hstatus = "<p style='color:green;'>Healthy</p>";
              } else if((preading || sreading || dreading || creading || o2reading || rrreading || greading) == undefined){
                hstatus = "<p style='color:black;'>Not updated</p>";
              } else {
                hstatus = "<p style='color:red;'>Unhealthy</p>";
              }

              if(preading < 60){
                pstatus = "<p style='color:orange;'>Low Pulse</p>";
              }else if(preading > 100){
                pstatus= "<p style='color:orange;'>High Pulse</p>";
              } else if(preading == undefined){
                pstatus = "<p style='color:black;'>Not Updated</p>";
              } else {
                pstatus = "<p style='color:green;'>Normal</p>";
              }

              if(o2reading < 98){
                o2status = "<p style='color:orange;;'>Hypoxemia</p>";
              }else if(o2reading == undefined){
                o2status = "<p style='color:black;'>Not Updated</p>";
              } else {
                o2status = "<p style='color:green;'>Normal</p>";
              }

              if((greading > 140 && greading <= 199)){
                gstatus = "<p style='color:orange;'>Pre-Diabetes</p>";
              }else if(greading >= 200){
                gstatus = "<p style='color:crimson;'>Diabetes</p>";
              }else if(greading == undefined){
                gstatus = "<p style='color:black;'>Not Updated</p>";
              } else {
                gstatus = "<p style='color:green;'>Normal</p>";
              }

              if(rrreading > 20){
                rrstatus = "<p style='color:crimson;'>High Respiration rate</p>";
              }else if(rrreading < 12){
                rrstatus = "<p style='color:orange;'>low Respiration rate</p>";
              }else if(rrreading == undefined){
                rrstatus = "<p style='color:black;'>Not Updated</p>";
              } else {
                rrstatus = "<p style='color:green;'>Normal</p>";
              }

              if(creading > 200){
                cstatus = "<p style='color:crimson;'>High Cholesterol level</p>";
              }else if(creading < 125){
                cstatus = "<p style='color:orange;'>low Cholesterol level</p>";
              }else if(creading == undefined){
                cstatus = "<p style='color:black;'>Not Updated</p>";
              } else {
                cstatus = "<p style='color:green;'>Normal</p>";
              }

              if((sreading > 120 && sreading < 130) && (dreading <= 90)){
                bpstatus = "<p style='color:orange;'>Elevated BP</p>";
              }else if((sreading >= 130 && sreading < 140) && (dreading <= 100)){
                bpstatus = "<p style='color:orange;'>High BP stage-I</p>";
              }else if((sreading >= 140 && sreading < 180) && (dreading <= 120)){
                bpstatus = "<p style='color:crimson;'>High BP stage-II</p>";
              }else if(sreading > 180 && dreading > 120){
                bpstatus = "<p style='color:red;'>High BP stage-III</p>";
              }else if((sreading || dreading) == undefined){
                bpstatus = "<p style='color:black;'>Not Updated</p>";
              } else {
                bpstatus = "<p style='color:green;'>Normal</p>";
              }
          

              AddItemsToTable(name, date, hstatus, pstatus, bpstatus, cstatus, o2status, rrstatus, gstatus);
            })
          })})
        });
      }
    })
  }
  window.onload = selectData;

  function AddItemsToTable(name, date, hstatus, pstatus, bpstatus, cstatus, o2status, rrstatus, gstatus){
    let tbody = document.getElementById('uhbody');
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

    td1.innerHTML = name;
    td2.innerHTML = date;
    td3.innerHTML = hstatus;
    td4.innerHTML = pstatus;
    td5.innerHTML = o2status;
    td6.innerHTML = gstatus;
    td7.innerHTML = rrstatus;
    td8.innerHTML = cstatus;
    td9.innerHTML = bpstatus;
    trow.appendChild(td1);
    trow.appendChild(td2);
    trow.appendChild(td3);
    trow.appendChild(td4);
    trow.appendChild(td5);
    trow.appendChild(td6);
    trow.appendChild(td7);
    trow.appendChild(td8);
    trow.appendChild(td9);
    tbody.appendChild(trow);
  }