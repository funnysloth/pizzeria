var $ = jQuery;

$(document).ready(function () {
    //making active link highlighted
    $(".account-menu-link").each(function () {
        if ($(this).prop('href') === window.location.href) {
            $(this).addClass('active');
        }
    })

    //validating changed data
    $("#email").change(function () {
        let regex = new RegExp("^([\\w-\.]+@([\\w-]+\\.)+[\\w-]{2,4})+$");
        if (!regex.test($(this).val())){
            $(".error-email").show();
            $("#change-btn").prop("disabled", true);
        }else {
            $(".error-email").hide();
            $("#change-email").val("true");
            $("#change-btn").prop("disabled", false);
        }
    })

    $("#fullName").change(function () {
        let regex = new RegExp("^[a-zA-Z а-яА-ЯІіЇїЄє]+$");
        if (!regex.test($(this).val())){
            $(".error-name").show();
            $("#change-btn").prop("disabled", true);
        }
        else {
            $(".error-name").hide();
            $("#change-btn").prop("disabled", false);
        }
    })

    $("#passwd").change(function () {
        if ($(this).val().length === 0)
            $("#passwdRepeat").attr("required", false);
        else {
            let regex = new RegExp("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])[0-9a-zA-Z]{8,}$");
            $("#passwdRepeat").attr("required", true);
            if (!regex.test($(this).val())) {
                $(".error-pass").show();
            } else {
                $(".error-pass").hide();
            }
        }
    })

    $("#passwdRepeat").change(function () {
        if($(this).val() !== $("#passwd").val()){
            $(".error-repeat-pass").show();
        }else {
            $(".error-repeat-pass").hide();
        }
    })
})