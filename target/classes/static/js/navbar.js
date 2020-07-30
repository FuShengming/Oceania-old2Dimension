$(document).ready(function () {
    let userId = localStorage['userId'];
    if (userId !== undefined && userId !== 1) {
        let socket = null;
        let openSocket = function () {
            if (typeof (WebSocket) == "undefined") {
                console.log("Can't Support WebSocket");
            } else {
                let socketUrl = "ws://localhost:8086/websocket/invitation/" + userId;
                socket = new WebSocket(socketUrl);
                socket.onopen = function () {
                    console.log("websocket is on.")
                };
                socket.onmessage = function (msg) {
                    $("#n-count").text(msg.data);
                    if (Number(msg.data) > 0) {
                        $("#n-count").show();
                    } else {
                        $("#n-count").hide();
                    }
                    let serverMsg = "get info：" + JSON.stringify(msg.data);
                    console.log(serverMsg);
                };
                socket.onclose = function () {
                    console.log("websocket is off.");
                };
                //发生了错误事件
                socket.onerror = function () {
                    console.log("websocket occurs an error.");
                }
            }
        };
        openSocket();
    }

    $("#sign-out-btn").on('click', function () {
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