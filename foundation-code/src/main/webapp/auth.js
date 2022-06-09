window.onload = function() {
    console.log('The page loaded!');
    let button = document.getElementById('login-button');
    button.addEventListener('click', login);

}

function login() {
    console.log("Login function invoked")
    event.preventDefault();
    // Convenience references
    let usernameInput = document.getElementById('login-username');
    let passwordInput = document.getElementById('login-password');
    let errorContainer = document.getElementById('error-message');

    let username = usernameInput.value;
    let password = passwordInput.value;
    console.log(username, password)
    if (username && password) {

//        errorContainer.setAttribute('hidden', true);

        let respData = fetch('http://localhost:8080/foundation/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({username, password})
        }).then(resp => {
            console.log(`Response status: ${resp.status}`);
            console.log(`Response timestamp: ${Date.now()}`);

            if (resp.status !== 200) {
                errorContainer.removeAttribute('display');
                errorContainer.innerText = "Login failed!";
                return;
            }

            return resp.json();
        })

        if (respData) {
            respData.then(data => {
                let successMsgContainer = document.createElement('p');
                successMsgContainer.setAttribute('class', 'alert alert-success');
                successMsgContainer.innerText = `Login successful! Welcome, ${data['first_name']}!`;
                document.getElementById('parent').appendChild(successMsgContainer);
            });
        }

    } else {

        // Show the error message
        errorContainer.removeAttribute('display');
        errorContainer.innerText = "You must provide a username and password!";

    }

}