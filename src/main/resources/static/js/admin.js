var $ = jQuery;

$(document).ready(function($) {
    //making active link highlighted
    $('.page-link').each(function() {
        if ($(this).prop('href') === window.location.href) {
            $(this).addClass('current-page');
        }
    });

    let header = $("#sticky-header");
    let btn = $("#back-to-top-btn");
    let cat = new URL(window.location.href).searchParams.get("category");
    let status = new URL(window.location.href).searchParams.get("status");
    let category = new URL(window.location.href).searchParams.get("category");
    let page = new URL(window.location.href).searchParams.get("page");
    let counter = 1;

    //displaying row numbers
    $("td.count").each(function () {
        $(this).text((20*(page-1))+counter);
        counter++;
    })

    if (cat !== "none" && cat !== null){
        $("option[value='" + cat + "']").attr("selected", "selected");
    }

    if (status !== "none" && status !== null){
        $("option[value='" + status + "']").attr("selected", "selected");
    }

    $("option[value='" + $("#category").data("cat") + "']").attr("selected", "selected");

    //send the right action variable to the server
    $(".hide-btn").click(function () {
        $(".select-action option[value='hide']").attr("selected", "selected");
    })

    $("button[value='show']").click(function () {
        $(".select-action option[value='show']").attr("selected", "selected");
    })
})