function uploadTable() {
    $.ajax({
        url : "projects",
        method: "get",
        dataType: "html",
        beforeSend: () => {
            $("#v-pills-home").html("<img src='resources/circle-loading.gif' style='display: block;width: 100px;margin: auto;'>")
        },
        success: resp => {
            $("#v-pills-home").html(resp);
        }
    });
}