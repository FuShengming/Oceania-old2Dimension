let validName = false;
let validPwd = false
let validPwdRep = false

$(document).ready(function () {

    $(".login-register").click(function () {
        $(window).attr("location", "/login")
    });

    $(".register-btn").click(function () {
        register()
    });

    // 按下回车
    $(".captcha-input").keypress(function (e) {
        let eCode = e.keyCode ? e.keyCode : e.which ? e.which : e.charCode;
        if (eCode === 13) {
            register();
        }
    });

    $(".login-btn").click(function () {
        register()
    });

    $(".password-input").bind("blur", function (event) {
        let password = event.target.value;
        if (password.length === 0) {
            validPwd = false;
            $("#password-warn").addClass("password-error").addClass("password-required-error").addClass("input-required-error")
                .removeClass("hidden-error").removeClass("password-error-msg").text("请输入密码")
        }
    });

    $("#password-warn").bind("click", function () {
        $("#password-warn").removeClass("password-error").removeClass("password-required-error")
            .addClass("hidden-error").text("");
        $(".password-input").get(0).focus();
    });

    $(".password-input").on("input", function (event) {
        let password = event.target.value;
        if (password.length > 0 && password.length < 6 || password.length > 16) {
            validPwd = false;
            $("#password-warn").addClass("password-error").addClass("password-error-msg")
                .removeClass("hidden-error").removeClass("input-required-error").text("密码长度必须为6-16个字符")
        }
        else if (password.length >= 6 && password.length <= 16) {
            validPwd = true;
            $("#password-warn").removeClass("password-error").removeClass("pass-error-msg")
                .addClass("hidden-error").addClass("input-required-error").text("")
        }
    });

    $(".password-repeat-input").on("blur", function (event) {
        let password = event.target.value;
        if (password.length === 0) {
            validPwdRep = false;
            $("#password-repeat-warn").addClass("password-error").addClass("password-required-error").addClass("input-required-error")
                .removeClass("hidden-error").removeClass("password-error-msg").text("请重复密码")
        }
    });

    $(".password-repeat-input").on("input", function (event) {
        let password = event.target.value;
        if (password.length !== 0 && password !== $(".password-input").val()) {
            validPwdRep = false;
            $("#password-repeat-warn").addClass("password-error").addClass("password-error-msg")
                .removeClass("hidden-error").removeClass("input-required-error").text("两次输入密码不一致")
        }
        else if (password === $(".password-input").val()) {
            validPwdRep = true;
            $("#password-repeat-warn").removeClass("password-error").removeClass("password-error-msg")
                .addClass("hidden-error").addClass("input-required-error").text("")
        }
    });

    $("#login-pwd-repeat-input").on("click", "#password-repeat-warn", function () {
        $("#password-repeat-warn").removeClass("password-error").removeClass("password-required-error")
            .addClass("hidden-error").text("");
        $(".password-repeat-input").get(0).focus();
    });

    $(".name-input").bind("blur", function (event) {
        let username = event.target.value;
        if (username.length === 0) {
            validName = false;
            $("#username-warn").addClass("username-required-error").addClass("input-required-error")
                .removeClass("hidden-error").removeClass("password-error-msg").text("请输入用户名")
        }
    });

    $("#username-warn").bind("click", function () {
        $("#username-warn").removeClass("username-required-error")
            .addClass("hidden-error").text("");
        $(".name-input").get(0).focus();
    });

    $(".name-input").on("input focus", function (event) {
        let username = event.target.value;
        if (username.length > 0 && username.length < 3 || username.length > 16) {
            validName = false;
            $("#username-warn").addClass("password-error-msg")
                .removeClass("hidden-error").removeClass("input-required-error").text("用户名长度必须为3-16个字符")
        }
        else if (username.length >= 3 && username.length <= 16) {
            validName = true;
            $("#username-warn").removeClass("password-error-msg")
                .addClass("hidden-error").addClass("input-required-error").text("")
        }
    });

    $(".captcha-input").bind("blur", function (event) {
        let captcha = event.target.value;
        if (captcha.length === 0) {
            $("#captcha-warn").addClass("captcha-required-error")
                .removeClass("hidden-error").text("请输入验证码")
        }
    });

    $("#captcha-warn").bind("click", function () {
        $("#captcha-warn").removeClass("captcha-error").removeClass("captcha-required-error")
            .addClass("hidden-error").text("");
        $(".captcha-input").get(0).focus();
    });

    $(".captcha-input").bind("focus", function (event) {
        $("#captcha-warn").addClass("hidden-error")
            .removeClass("captcha-error").text("")
    });


});

function register() {
    let formData = getRegisterForm();
    if (!validateRegisterForm(formData) || !verifyCaptcha() || !validPwdRep || !validName || !validPwd) {
        return;
    }
    formData.pwd = md5(formData.name + formData.pwd);

    $.ajax({
        url: "/user/signUp",
        type: "POST",
        contentType:'application/json;charset=utf-8',
        data: JSON.stringify(formData),
        success: function (res) {
            if (res.success) {
                localStorage.setItem("userId", res.content.id);
                $(window).attr("location", "/workspace")
            }
            else if (res.message === "用户名已存在") {
                $("#username-warn").addClass("password-error-msg")
                    .removeClass("hidden-error").removeClass("input-required-error").text("用户名已存在")
            }
            else {
                alert("Error occurred.")
            }
        },
        error: function (res) {
            console.log(res)
        }
    })
}

function getRegisterForm() {
    return {
        name:$(".name-input").val(),
        pwd:$(".password-input").val()
    };
}

function validateRegisterForm(data) {
    let isValidate = true;
    if (!data.name) {
        isValidate = false;
        if ($("#username-warn").hasClass("hidden-error")) {
            $("#username-warn").addClass("username-required-error").addClass("input-required-error")
                .removeClass("hidden-error").removeClass("password-error-msg").text("请输入用户名")
        }
    }
    if (!data.pwd) {
        isValidate = false;
        if ($("#password-warn").hasClass("hidden-error")) {
            $("#password-warn").addClass("password-error").addClass("password-required-error").addClass("input-required-error")
                .removeClass("hidden-error").removeClass("password-error-msg").text("请输入密码")
        }
    }
    if ($(".password-repeat-input").val() === "") {
        isValidate = false;
        if ($("#password-repeat-warn").hasClass("hidden-error")) {
            $("#password-repeat-warn").addClass("password-error").addClass("password-required-error").addClass("input-required-error")
                .removeClass("hidden-error").removeClass("password-error-msg").text("请重复密码")
        }
    }
    else if ($(".password-repeat-input").val() !== data.pwd) {
        isValidate = false;
        $("#password-repeat-warn").addClass("password-error").addClass("password-error-msg")
            .removeClass("hidden-error").removeClass("input-required-error").text("两次输入密码不一致")
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
        $("#captcha-warn").removeClass("hidden-error").addClass("captcha-error").text("验证码输入错误");
        draw(show_num);
        $(".captcha-input").val("");
        return false
    }
}