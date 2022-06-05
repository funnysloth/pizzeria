var $ = jQuery;

$(document).ready(function($) {

    $("#email").change(function () {
        let regex = new RegExp("^([\\w-\.]+@([\\w-]+\\.)+[\\w-]{2,4})?$");
        if (!regex.test($(this).val())){
            $(".error-email").show();
        }else {
            $(".error-email").hide();
        }
    })

    $("#fullName").change(function () {
        let regex = new RegExp("^[a-zA-Z а-яА-ЯІіЇїЄє]+$");
        if (!regex.test($(this).val())){
            $(".error-name").show();
        }
        else {
            $(".error-name").hide();
        }
    })

    $("#passwd").change(function () {
        let regex = new RegExp("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])[0-9a-zA-Z]{8,}$");
        if (!regex.test($(this).val())){
            $(".error-pass").show();
        }else {
            $(".error-pass").hide();
        }
    })

    $("#passwdRepeat").change(function () {
        if($(this).val() !== $("#passwd").val()){
            $(".error-repeat-pass").show();
            $("#reg-btn").prop("disabled", true);
        }else {
            $(".error-repeat-pass").hide();
            $("#reg-btn").prop("disabled", false);
        }
    })

    $("input[name='phoneNumber']").change(function () {
        let regex = new RegExp("^(\\+?\\d{1,3})?\\d{9}$");
        if (!regex.test($(this).val())){
            $(".error-phone").show();
        }
        else {
            $(".error-phone").hide();
        }
    })
})