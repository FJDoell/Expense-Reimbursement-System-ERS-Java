// Once the page loads, we need event listeners!
window.onload = function (event) { //this is essentially a callback function for the window's completion signal
    document.getElementById("showReimbByAuthor").addEventListener('click', getReimbsByAuthor);
    document.getElementById("addReimb").addEventListener('click', addReimbValidation);

    // Step 1: Initialize
    isManager = false;
    getCurrentUser();
    getReimbsByAuthor();
}

// display reimbs by author
function getReimbsByAuthor() {
    let xhttp = new XMLHttpRequest();

    xhttp.onreadystatechange = function () {
        // console.log("readyState is changing: ", xhttp.readyState);
        if (xhttp.readyState == 4 && xhttp.status == 200) {
            // console.log(xhttp.responseText);
            let RespObj = JSON.parse(xhttp.responseText);
            console.log(RespObj);
            displayAllObjects(RespObj);
        }
    }

    xhttp.open('POST', `http://localhost:9003/Project_1_War/employee/getReimbsByAuthor`);
    xhttp.send(JSON.stringify(data));
}

// Add reimb validation
function addReimbValidation() {
    let amount = 0;
    let message = document.getElementById("message");
    if(document.getElementById("reimbAmt").value) {
        amount = document.getElementById("reimbAmt").value;
    }
    // check if amount is valid
    if(amount > 0) {
        // amount is greater than 0
        if(amount <= 2147483647) {
            // next we need to check if the type is valid
            addReimbValidationPart2();
        } else {
            message.innerText = "ERROR: That number is too high to store.";
        }

    } else {
        message.innerText = "ERROR: Please enter a number greater than 0.";
    }
}

function addReimbValidationPart2() {
    let reimbTypeSelect = document.getElementById("reimbType");
    let filterName = reimbTypeSelect.value;

    if (filterName) {
        // Now we know it exists
        // But we need to get an ID number from this
        if (filterName === "LODGING") {
            setData(1);
            addReimb();
        } else if (filterName === "TRAVEL") {
            setData(2);
            addReimb();
        } else if (filterName === "FOOD") {
            setData(3);
            addReimb();
        } else if (filterName === "FAINT") {
            setData(4);
            addReimb();
        } else if (filterName === "OTHER") {
            setData(5);
            addReimb();
        } else {
            // Oops, this is bad data anyway
            document.getElementById("message").innerText = "ERROR: This reimbursement type does not exist.";
        }
    } else {
        // Oi, they didn't even try
        document.getElementById("message").innerText = "ERROR: Please enter a reimbursement type.";
    }

    function setData(passedTypeId) {
        data = {
            filterId: null,
            appOrDen: false,
            reimb: {
                amount: document.getElementById("reimbAmt").value,
                typeId: passedTypeId
            }
        };
    
        if(document.getElementById("reimbDesc").value) {
            data.reimb.description = document.getElementById("reimbDesc").value;
        } else {
            data.reimb.description = "";
        }
    } // end set data
} // end validation part 2: Electric Boogaloo

// Add a reimb
function addReimb() {
    let xhttp = new XMLHttpRequest();

    xhttp.onreadystatechange = function () {
        // console.log("readyState is changing: ", xhttp.readyState);

        if (xhttp.readyState == 4 && xhttp.status == 200) {
            // console.log(xhttp.responseText);
            let RespObj = JSON.parse(xhttp.responseText);
            console.log(RespObj);
            displayAllObjects(RespObj);
        }
    }

    console.log("This is the JSON text: " + JSON.stringify(data));

    xhttp.open('POST', `http://localhost:9003/Project_1_War/employee/addReimb`);
    xhttp.send(JSON.stringify(data));
}


