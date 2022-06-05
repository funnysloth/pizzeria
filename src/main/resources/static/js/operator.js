var $ = jQuery;

$(document).ready(function () {

    var todayDate = new Date();
    var dd = todayDate.getDate();
    var mm = todayDate.getMonth() + 1; //January is 0!
    if (mm < 10)
        mm = "0" + mm;
    var yyyy = todayDate.getFullYear();

    today = yyyy + '-' + mm + '-' + dd;

    $(".date").change(function () {
        let date = new Date($(this).val());
        if(date > todayDate)
            $(this).val(today)
    });
})