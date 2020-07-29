$(document).ready(function () {
    let userId = localStorage['userId'];
    if (userId === undefined) window.location.href = "/login";

    let getTasksByUserId = function () {
        $.ajax({
            type: "get",
            url: "/group/getAllTasks/" + userId.toString(),
            headers: {"Authorization": $.cookie('token')},
            success: function (data) {
                if (data.success) {
                    let h = "";
                    let codes = data.content;
                    codes.forEach(function (code) {
                        console.log(code);
                        h += `<tr class="clickable-row" code-id="${code.codeId}">
                                <td>${code.codeName}</td>
                                <td>${code.date === null ? "Never" : new Date(Date.parse(code.date)).toLocaleString("en")}</td>
                                <td class="td-btn">
                                    <button class="btn btn-light btn-setting px-0" type="button" id="dropdownMenuButton"
                                            data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                        <i class="fa fa-ellipsis-h setting-icon"></i>
                                    </button>
                                    <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
                                        <a class="dropdown-item modify-name" href="#" code-id="${code.codeId}">Modify Name</a>
                                        <a class="dropdown-item rmv" href="#" code-id="${code.codeId}">Remove</a>
                                    </div>
                                </td>
                            </tr>`
                    });
                    $("#code-tb").html(h);
                    $(".clickable-row").on('click', function (e) {
                        if (!$(e.target).hasClass("btn-setting") &&
                            !$(e.target).hasClass("setting-icon") &&
                            !$(e.target).hasClass("dropdown-menu") &&
                            !$(e.target).hasClass("dropdown-item")) {
                            console.log('click', $(this).attr("code-id"));
                            // localStorage.setItem('codeId', $(this).attr("code-id"));
                            window.location.href = "/graph?code=" + $(this).attr("code-id");
                        }
                    });
                    $(".rmv").on('click', function () {
                        let codeId = $(this).attr("code-id");
                        if (confirm("Sure to remove this project permanently?")) {
                            $.ajax({
                                type: "post",
                                url: "/code/delete",
                                headers: {"Authorization": $.cookie('token')},
                                dataType: "json",
                                contentType: 'application/json',
                                data: JSON.stringify({
                                    "userId": userId,
                                    "codeId": codeId,
                                }),
                                success: function (data) {
                                    if (data.success) {
                                        getCodesByUserId();
                                    } else {
                                        console.log(data.message);
                                    }
                                },
                                error: function (err) {
                                    console.log(err);
                                }
                            });
                        }
                    });
                } else {
                    console.log(data.message);
                }
            },
            error: function (err) {
                console.log(err);
            }
        });
    };
})