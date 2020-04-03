$(function () {
    $("#my_div").text("jQuery works");
    $.ajax({
        type: "post",
        url: "/graphql",
        dataType: "json",
        data: JSON.stringify({
            query: `
            query
            {
                get_domainset
                {
                    domains
                    {
                        id
                        vertices
                        {
                            id
                            funcName
                        }
                    }
                }
            }`
        }),
        success: function (data) {
            console.log(data);
        },
        error: function (err) {
            console.log(err);
        }
    });
});