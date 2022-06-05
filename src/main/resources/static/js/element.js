var $ = jQuery;

$(document).ready(function($) {
    //making active link highlighted
    $('.categories').each(function() {
        if ($(this).prop('href') === window.location.href) {
            $(this).addClass('current');
        }
    });
    $('.page-link').each(function() {
        if ($(this).prop('href') === window.location.href) {
            $(this).addClass('current-page');
        }
    });

    let header = $("#sticky-header");

    let btn = $("#back-to-top-btn");

    //sticky header
    if ($(window).scrollTop() > document.getElementById("nav").offsetTop)
    {
        header.parent().addClass("sticky");
        header.parent().css("background-color", "#fff");
        header.css("padding-top", "unset");
        header.css("padding-bottom", "unset");
    }
    else
    {
        header.parent().removeClass("sticky");
        header.parent().css("background-color", "inherit");
        header.css("padding-top", "1em");
        header.css("padding-bottom", "1em");
    }

    $(window).scroll(function () {
        if ($(this).scrollTop() > document.getElementById("nav").offsetTop) {
            header.parent().addClass("sticky");
            header.parent().css("background-color", "#fff");
            header.css("padding-top", "unset");
            header.css("padding-bottom", "unset");
        }
        else
        {
            header.parent().removeClass("sticky");
            header.parent().css("background-color", "inherit");
            header.css("padding-top", "1em");
            header.css("padding-bottom", "1em");
        }
        if ($(window).scrollTop() > 300) {
            btn.addClass('show');
        } else {
            btn.removeClass('show');
        }
    });

    //bact to top button
    btn.on('click', function(e) {
        e.preventDefault();
        $('html, body').animate({scrollTop:0}, '300');
    });
})