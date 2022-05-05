// Once the page loads, we need event listeners!
window.onload = function (event) { //this is essentially a callback function for the window's completion signal
    // This never triggers???
    if (event.persisted) {
        console.log("this was 'backed' into")
        window.location.replace("http://localhost:9003/Project_1_War");
    }

    document.getElementById("showAllReimbs").addEventListener('click', getAllReimbs);
    document.getElementById("showByStatus").addEventListener('click', getReimbByStatusValidate);
    document.getElementById("appReimb").addEventListener('click', function(){updateReimbValidate(true);});
    document.getElementById("denReimb").addEventListener('click', function(){updateReimbValidate(false);});

    // Step 1: Initialize
    isManager = true;
    getCurrentUser();
    data.filterId = 1;
    getByStatus();
}

// display all reimbs
function getAllReimbs() {
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

    xhttp.open('POST', `http://localhost:9003/Project_1_War/finance/getAllReimbs`);
    xhttp.send();
}

// Validate the reimbursement status before running the request
function getReimbByStatusValidate() {
    let statusSelect = document.getElementById("reimbStatus");
    let filterName = statusSelect.value;

    if (filterName) {
        // Now we know it exists
        // But we need to get an ID number from this
        if (filterName === "PENDING") {
            data.filterId = 1;
            getByStatus();
        } else if (filterName === "APPROVED") {
            data.filterId = 2;
            getByStatus();
        } else if (filterName === "DENIED") {
            data.filterId = 3;
            getByStatus();
        } else {
            // Oops, this is bad data anyway
            document.getElementById("message").innerText = "ERROR: This status does not exist.";
        }
    } else {
        // Oi, they didn't even try
        document.getElementById("message").innerText = "ERROR: Please enter a status.";
    }
}

// display reimbs by status
function getByStatus() {
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

    xhttp.open('POST', `http://localhost:9003/Project_1_War/finance/getReimbsByStatus`);
    xhttp.send(JSON.stringify(data));
}

// Check for radio button selection
function updateReimbValidate(boolean) {
    // If false, we are denying
    // If true, we are approving
    let radios = document.getElementsByName("select");
    let found = false;
    let message = document.getElementById("message");

    console.log("In update Reimb Validate");
    console.log(boolean);
    if (radios[0]) {
        // We have something
        for (i = 0; i < radios.length; i++) {
            if (radios[i].checked) {
                // we found one that is checked!
                data.filterId = radios[i].id;
                found = true;
                break;
            }
        }
        // Was one found or not?
        if(found) {
            message.innerText = "Radio button selection found.";
            // Now to use the boolean to either approve or deny!
            if(boolean) {
                data.appOrDen = true;
                updateReimb();
            } else {
                data.appOrDen = false;
                updateReimb();
            }
        } else {
            message.innerText = "ERROR: Please select a reimbursement.";
        }
    } else {
        // We have no radio buttons
        message.innerText = "ERROR: No selectors found!";
    }
}

// Update a Reimb status (Approve or deny)
function updateReimb() {
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

    xhttp.open('POST', `http://localhost:9003/Project_1_War/finance/appOrDen`);
    xhttp.send(JSON.stringify(data));
}