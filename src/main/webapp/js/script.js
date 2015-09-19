var xmlHttpRequest = newXMLHttpRequest();

function newXMLHttpRequest() {
    var xmlReq = false;

    if (window.XMLHttpRequest) {
        xmlReq = new XMLHttpRequest();
    } else if (window.ActiveXObject) {
        try {
            xmlReq = new ActiveXObject("Msxml2.XMLHTTP");
        } catch (e1) {
            try {
                xmlReq = new ActiveXObject("Microsoft.XMLHTTP");
            } catch (e2) {
                alert("Can not create XMLHttpRequest");
            }
        }
    }
    return xmlReq;
}

function sendRequest(formId) {
    var form = document.getElementById(formId);
    var url = form.getAttribute("action") + "?action=" + formId + "&id=" + form.getElementsByTagName("input")[0].value;
    xmlHttpRequest.open("GET", url, true);
    xmlHttpRequest.onreadystatechange = resultHandler;
    xmlHttpRequest.send(null);
}

function resultHandler() {
    if (xmlHttpRequest.readyState == 4 && xmlHttpRequest.status == 200) {
        var JSONObject = JSON.parse(xmlHttpRequest.responseText);
        var resultDiv = document.getElementById("result");
        resultDiv.innerHTML = "";
        var operation= JSONObject.operation;
        if (operation == "error") {
            var newP = document.createElement('p');
            newP.innerHTML = JSONObject.exception;
            resultDiv.appendChild(newP);
        }else if (operation == "delete") {
            var newP = document.createElement('p');
            newP.innerHTML = JSONObject.delete;
            resultDiv.appendChild(newP);
        } else if (operation == "student") {
            var students = JSONObject.students;
            for (var i = 0; i < students.length; i++) {
                var newP = document.createElement('p');
                var student = students[i];
                newP.innerHTML = student.id + ". " + student.firstName + " " + student.secondName;
                resultDiv.appendChild(newP);
            }
        } else if (operation == "subject") {
            var subjects = JSONObject.subjects;
            for (var i = 0; i < subjects.length; i++) {
                var newP = document.createElement('p');
                var subject = subjects[i];
                newP.innerHTML = subject.id + ". " + subject.title;
                resultDiv.appendChild(newP);
            }
        }
    }
}