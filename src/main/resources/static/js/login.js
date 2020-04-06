$(document).ready(function () {

    $(".login-register").click(function () {
        $(window).attr("location", "/register")
    });

    $(".login-btn").click(function () {
        login()
    });

    // 按下回车登录
    $(".captcha-input").keypress(function (e) {
        let eCode = e.keyCode ? e.keyCode : e.which ? e.which : e.charCode;
        if (eCode === 13) {
            login();
        }
    });

    $(".password-wrapper").on("blur", ".password-input", function (event) {
        let password = event.target.value;
        if (password.length === 0) {
            $("#password-warn").addClass("password-error").addClass("password-required-error")
                .removeClass("hidden-error").text("请输入密码")
        }
    });

    $("#login-pwd-input").on("click", "#password-warn", function () {
        $("#password-warn").removeClass("password-error").removeClass("password-required-error")
            .addClass("hidden-error").addClass("input-required-error").text("");
        $(".password-input").get(0).focus();
    });

    $(".username-input").bind("blur", function (event) {
        let username = event.target.value;
        if (username.length === 0) {
            $("#username-warn").addClass("username-required-error")
                .removeClass("hidden-error").text("请输入用户名")
        }
    });

    $("#username-warn").bind("click", function () {
        $("#username-warn").removeClass("username-required-error")
            .addClass("hidden-error").text("");
        $(".username-input").get(0).focus();
    });

    $(".captcha-input").bind("blur", function (event) {
        let captcha = event.target.value;
        if (captcha.length === 0) {
            $("#captcha-warn").addClass("captcha-required-error")
                .removeClass("hidden-error").removeClass("captcha-error").text("请输入验证码")
        }
    });

    $(".captcha-input").bind("focus", function (event) {
            $("#captcha-warn").addClass("hidden-error")
                .removeClass("captcha-error").text("")
    });

    $("#captcha-warn").bind("click", function () {
        $("#captcha-warn").removeClass("captcha-error").removeClass("captcha-required-error")
            .addClass("hidden-error").text("");
        $(".captcha-input").get(0).focus();
    });

    $("#show-password").click(function () {
        console.log("xxx")
        let temp = $(".password-input").val();
        let tp = $(".password-input").attr("type");
        let input_html0 = "<div class=\"input-error-msg input-required-error hidden-error\" id=\"password-warn\"></div>"
        let input_html1 = "<input name=\"password\" type=\"password\" class=\"Input password-input\" placeholder=\"密码\" value=\"" + temp + "\">";
        let input_html2 = "<input name=\"password\" type=\"text\" class=\"Input password-input\" placeholder=\"密码\" value=\"" + temp + "\">";
        if (tp === "text") {
            $(".password-wrapper").html(input_html1 + input_html0)
        }
        else if (tp === "password") {
            $(".password-wrapper").html(input_html2 + input_html0)
        }
    });



});

function login() {
    let formData = getLoginForm();
    if (!validateLoginForm(formData) || !verifyCaptcha()) {
        return;
    }
    formData.pwd = md5(formData.name + formData.pwd);

    $.ajax({
        url: "/user/login",
        type: "POST",
        contentType:'application/json;charset=utf-8',
        data: JSON.stringify(formData),
        success: function (res) {
            if (res.success) {
                localStorage.setItem("token", res.content.token)
                //$(window).attr("location", "/graphpage") //todo
            }
            else if (res.message === "name or pwd is not correct") {
                $("#password-warn").addClass("password-error-msg")
                    .removeClass("hidden-error").removeClass("input-required-error").text("用户名已存在")
            }
        },
        error: function (res) {
            console.log(res)
        }
    })

    // postRequest(
    //     '/user/login',
    //     formData,
    //     function (res) {
    //         if (res.success) {
    //             sessionStorage.setItem('username', formData.username);
    //             sessionStorage.setItem('id', res.content.id);
    //             if (formData.name == "root") {
    //                 sessionStorage.setItem('role', 'admin');
    //                 window.location.href = "/admin/movie/manage"
    //             } else {
    //                 sessionStorage.setItem('role', 'user');
    //                 window.location.href = "/user/home"
    //             }
    //         } else {
    //             alert(res.message);
    //         }
    //     },
    //     function (error) {
    //         alert(error)
    //     });
}

function getLoginForm() {
    return {
        name:$(".username-input").val(),
        pwd:$(".password-input").val()
    };
}

function validateLoginForm(data) {
    let isValidate = true;
    if (!data.name) {
        isValidate = false;
        if ($("#username-warn").hasClass("hidden-error")) {
            $("#username-warn").addClass("username-required-error")
                .removeClass("hidden-error").text("请输入用户名")
        }
    }
    if (!data.pwd) {
        isValidate = false;
        if ($("#password-warn").hasClass("hidden-error")) {
            $("#password-warn").addClass("password-error").addClass("password-required-error")
                .removeClass("hidden-error").text("请输入密码")
        }
    }
    return isValidate;
}

function verifyCaptcha() {
    let val = $(".captcha-input").val();
    let num = show_num.join("");
    if (val.toUpperCase() === num.toUpperCase()) {
        return true
    }
    else {
        $("#captcha-warn").removeClass("hidden-error").removeClass("captcha-required-error")
            .addClass("captcha-error").text("验证码输入错误");
        draw(show_num);
        $(".captcha-input").val("");
        return false
    }
}