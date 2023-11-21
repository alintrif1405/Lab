$(document).ready(function () {
    function showAlert(message) {
        let alertElement = $('.alert');

        alertElement.addClass("show");
        alertElement.removeClass("hide");
        alertElement.addClass("showAlert");

        $('.msg').text(message); // Set the alert message

        setTimeout(function () {
            alertElement.removeClass("show");
            alertElement.addClass("hide");
        }, 5000);
    }

    $('button').click(function () {

        validateForm();
    });


    $('.close-btn').click(function () {
        let alertElement = $('.alert');

        alertElement.removeClass("show");
        alertElement.addClass("hide");
    });

    $('.show-password').click(function(){
        let passwordInput = $('.password-input');


        if (passwordInput.attr('type') === 'password') {
            passwordInput.attr('type', 'text');
        } else {
            passwordInput.attr('type', 'password');

        }
    });

    function validateForm() {
        let firstName = document.forms["createAccount"]["f_name"].value;
        let lastName = document.forms["createAccount"]["l_name"].value;
        let email = document.forms["createAccount"]["email"].value;
        let password = document.forms["createAccount"]["password"].value;


        let lettersOnly = /^[A-Za-z]+$/;
        let passwordChecker = /^[A-Za-z@#$%^&+=!0-9]+$/
        let emailChecker = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/

        if (firstName === "" || !lettersOnly.test(firstName)) {
            showAlert("First name should contain only letters");
            return false;
        } else if (lastName === "" || !lettersOnly.test(lastName)) {
            showAlert("Last name should contain only letters");
            return false;
        } else if (!emailChecker.test(email)) {
            showAlert("Wrong email");
            return false;
        } else if (!passwordChecker.test(password) || password.length < 8) {
            showAlert("Password wrong or too short")
        } else {
            return true;
        }

    }

    function submitForm(){
        let formData = {
            f_name: $('#f_name').val(),
            l_name: $('#l_name').val(),
            email: $('#email').val(),
            password: $('#password').val(),
            role: $('#role').val()
        };
        $.ajax({
            url: 'http://localhost:8080/users/register',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(formData),
            success: function (response) {
                showAlert("Account created successfully");
            },
            error: function (xhr, status, error) {
                showAlert("Error creating account. Please try again.");
            }
        });
    }

});