/**
 * Created by dort on 20.12.16.
 */

var basic_part_url = 'http://localhost:8080';

$(document).ready(function () {


    $("#auth-btn").click(function () {

        var login = $("#login");
        var password = $("#password");
        if (login != ''  || password != '' ) {
            $.ajax({
                url: basic_part_url + "/account/login",
                method: "get",
                data: {'username': login.val(), 'password': password.val()},
                success: function (data) {
                    var result = JSON.parse(data);
                    userId = result.user_id;
                    localStorage.setItem("userId",userId);
                    location.href = result.nextPage;
                },
                error: function () {
                    alert("Uncorrect password or login. May be you don't confirm account ? ");
                }
            });
        } else {
            alert("Please input correct login");
        }
    });

    $("#reg-btn").click(function () {
        localStorage.clear();
        //проверим капчу
        var captchaResponse = grecaptcha.getResponse();
        var login = $("#login");
        var password = $("#password");
        var email = $("#email");

        if(login != '' && password != '' && email != ''){
            $.ajax({
                url: basic_part_url + "/account/registration",
                method: "post",
                // crossDomain: true,
                xhrFields: {
                    withCredentials: true
                },
                data: {'username': login.val(), 'password': password.val(),'email': email.val(), 'recaptcha': captchaResponse},
                success: function () {
                    $("#login").val('');$("#password").val(''); $("#email").val('');grecaptcha.reset();

                    alert("Please ! Confirm registration by email");
                },
                error: function (data) {
                    alert("Error ! What happened ?");
                }
            });
        }
    });
});

