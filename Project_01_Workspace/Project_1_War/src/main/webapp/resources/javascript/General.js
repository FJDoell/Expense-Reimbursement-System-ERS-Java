// Contains functions used by BOTH finance managers and employees

// initialize data, this is sent in all requests anyway
// Even if they modified it mid-request, the worst that happens
// is that it modifies the wrong reimb, which is their fault anyway.

let data = {};
let isManager = false;

// get current user
function getCurrentUser() {
    let xhttp = new XMLHttpRequest();
    let title = document.getElementById("head");
    let message = document.getElementById("message");

    xhttp.onreadystatechange = function () {
        // console.log("readyState is changing: ", xhttp.readyState);

        if (xhttp.readyState == 4 && xhttp.status == 200) {
            // console.log(xhttp.responseText);
            let RespObj = JSON.parse(xhttp.responseText);
            console.log(RespObj);
            displayAllObjects(RespObj);
        }
    }

    xhttp.open('POST', `http://localhost:9003/Project_1_War/json/getCurrentUser`);
    xhttp.send();
}

// Display a message
function displayMessage(message) {
    let ourMessage = document.getElementById("message");
    // generate message
    ourMessage.innerText = message;
}

// Display a header
function displayHead(head) {
    let ourHead = document.getElementById("head");
    // generate head message
    ourHead.innerText = head;
}

// display all the objects I am passed
function displayAllObjects(ourObjectFromJSON) {
    // Initialize our elements
    let ourReturn = document.getElementById("tableMainBody");
    let newRow = document.createElement("tr");
    let newColumn = document.createElement("th");
    let newColumn2 = document.createElement("td");
    let newColumn3 = document.createElement("td");
    let newColumn4 = document.createElement("td");
    let newColumn5 = document.createElement("td");
    let newColumn6 = document.createElement("td");
    let newColumn7 = document.createElement("td");
    let newColumn8 = document.createElement("td");
    let newColumn9 = document.createElement("td");
    let newColumn10 = document.createElement("td");
    let newColumn10Content = "";
    console.log("in our displayAllObjects method");
    console.log("Here's what I've got! Hopefully it's not nothing..." + Object.values(ourObjectFromJSON));

    // clear our return to be fresh
    ourReturn.innerText = "";

    console.log(ourObjectFromJSON.reimbs);
    // for each value...
    if ((ourObjectFromJSON.reimbs) && !(ourObjectFromJSON.reimbs.length === 0)) {
        console.log("In array length greater than 0");
        Object.values(ourObjectFromJSON.reimbs).forEach(val => {
            console.log(val);
            // take that object and dynamically make a table row item per object value(s)
            if (val.id) {
                // Create the elements
                newRow = document.createElement("tr");
                newColumn = document.createElement("th");
                newColumn.innerText = val.id;
                newColumn.scope = "row";
                newColumn2 = document.createElement("td");
                newColumn2.innerText = val.amount;
                newColumn3 = document.createElement("td");
                newColumn3.innerText = val.submitted;
                newColumn4 = document.createElement("td");
                newColumn4.innerText = val.resolved;
                newColumn5 = document.createElement("td");
                newColumn5.innerText = val.description;
                newColumn6 = document.createElement("td");
                newColumn6.innerText = val.authorName;
                newColumn7 = document.createElement("td");
                newColumn7.innerText = val.resolverName;
                newColumn8 = document.createElement("td");
                newColumn8.innerText = val.statusName;
                newColumn9 = document.createElement("td");
                newColumn9.innerText = val.typeName;
                newColumn10 = document.createElement("td");
                if (isManager && !val.resolved) {
                    newColumn10Content = document.createElement("input");
                    newColumn10Content.type = "radio";
                    newColumn10Content.id = val.id;
                    newColumn10Content.name = "select";
                    newColumn10Content.value = val.id;
                    newColumn10.appendChild(newColumn10Content);
                } else {
                    newColumn10.innerText = "";
                }

                // Append to the row
                newRow.appendChild(newColumn);
                newRow.appendChild(newColumn2);
                newRow.appendChild(newColumn3);
                newRow.appendChild(newColumn4);
                newRow.appendChild(newColumn5);
                newRow.appendChild(newColumn6);
                newRow.appendChild(newColumn7);
                newRow.appendChild(newColumn8);
                newRow.appendChild(newColumn9);
                newRow.appendChild(newColumn10);

                // Finally, append the new row to the table body!
                ourReturn.appendChild(newRow);
            }
        });
    } else {
        ourObjectFromJSON.message = "No reimbursements found!";
    }


    // if our head has a value...
    if (ourObjectFromJSON.head) {
        displayHead(ourObjectFromJSON.head);
        ourObjectFromJSON.head = null;
    }

    // if our message has a value...
    if (ourObjectFromJSON.message) {
        displayMessage(ourObjectFromJSON.message);
        ourObjectFromJSON.message = null;
    }
}