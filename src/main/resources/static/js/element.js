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

    $(".select-pizza").change(function () {
        if(!$(".add-to-cart-pizza").prop("disabled")) {
            let share = $(".share-container");
            share.show();
            share.css("display", "flex");
            let first_dish = $("#first-part option:selected").data("id");
            let second_dish = $("#second-part option:selected").data("id");
            let third_dish = $("#third-part option:selected").data("id");
            let fourth_dish = $("#fourth-part option:selected").data("id");
            let url = "localhost:8080/pizza-of-four?pizzaOfFour=" + first_dish  + "," + second_dish
                + "," + third_dish + "," + fourth_dish;
            $(".share-tg").prop("href", "https://t.me/share/url?url=" + url);
            $(".share-fb").prop("href", "https://www.facebook.com/sharer/sharer.php/url?url=" + url);
            $(".share-twitter").prop("href", "https://twitter.com/intent/tweet?url=" + url);
        }
    })

    $(".copy-link-btn").click(function () {
        navigator.clipboard.writeText("localhost:8080/pizza-of-four?pizzaOfFour=" + $("#first-part option:selected").data("id") + ","
            + $("#second-part option:selected").data("id") + "," + $("#third-part option:selected").data("id") + ","
            + $("#fourth-part option:selected").data("id")).then(function () {
            
        })
    })
})