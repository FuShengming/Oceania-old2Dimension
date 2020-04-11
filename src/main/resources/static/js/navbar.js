$(document).ready(function () {
    $("#sign-out-btn").click(function () {
        if (confirm("是否要退出登录？")) {
            $.ajax({
                url: "/logout",
                type: "POST",
                success: function (res) {
                    localStorage.removeItem("userId");
                    $(window).attr("location", "/index")
                },
                error: function (res) {
                    console.log(res)
                }
            })
        }
    })
});