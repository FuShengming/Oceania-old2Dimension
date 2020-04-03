$(document).ready(function () {

    $("login-register").onclick(function () {
        window.location.herf = "/register"
    });

    $("login-btn").click(function () {
        login()
    });

    function login() {
        let formData = getLoginForm();
        if (!validateLoginForm(formData)) {
            return;
        }

        postRequest(
            '/login',
            formData,
            function (res) {
                if (res.success) {
                    sessionStorage.setItem('username', formData.username);
                    sessionStorage.setItem('id', res.content.id);
                    if (formData.username == "root") {
                        sessionStorage.setItem('role', 'admin');
                        window.location.href = "/admin/movie/manage"
                    } else {
                        sessionStorage.setItem('role', 'user');
                        window.location.href = "/user/home"
                    }
                } else {
                    alert(res.message);
                }
            },
            function (error) {
                alert(error)
            });
    }

    function getLoginForm() {
        return {
            username: $('#login-name-input').val(),
            password: $('#login-pwd-input').val()
        };
    }

    function validateLoginForm(data) {
        let isValidate = true;
        if (!data.username) {
            isValidate = false;
            $('#index-name').parent('.input-group').addClass('has-error');
            $('#index-name-error').css("visibility", "visible");
        }
        if (!data.password) {
            isValidate = false;
            $('#index-password').parent('.input-group').addClass('has-error');
            $('#index-password-error').css("visibility", "visible");
        }
        return isValidate;
    }

})