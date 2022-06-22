var $ = jQuery;

$(document).ready(function($) {

    let pizzaOfFour = new URL(window.location.href).searchParams.get("pizzaOfFour");
    if (pizzaOfFour != null) {
        let pizzas = pizzaOfFour.split(",");

        console.log(pizzas);

        $("#first-part option[data-id='" + pizzas[0] + "']").prop("selected", "selected");
        $(".first-part .not-selected").hide();
        $(".first-part").append("<p class='selected'>" + $("#first-part option:selected").val() + "</p>")
        $("#second-part option[data-id='" + pizzas[1] + "']").prop("selected", "selected");
        $(".second-part .not-selected").hide();
        $(".second-part").append("<p class='selected'>" + $("#second-part option:selected").val() + "</p>")
        $("#third-part option[data-id='" + pizzas[2] + "']").prop("selected", "selected");
        $(".third-part .not-selected").hide();
        $(".third-part").append("<p class='selected'>" + $("#third-part option:selected").val() + "</p>")
        $("#fourth-part option[data-id='" + pizzas[3] + "']").prop("selected", "selected");
        $(".fourth-part .not-selected").hide();
        $(".fourth-part").append("<p class='selected'>" + $("#fourth-part option:selected").val() + "</p>")
        let firstPartPrice = parseInt($("#first-part option:selected").data("price"))/4;
        let secondPartPrice = parseInt($("#second-part option:selected").data("price"))/4;
        let thirdPartPrice = parseInt($("#third-part option:selected").data("price"))/4;
        let fourthPartPrice = parseInt($("#fourth-part option:selected").data("price"))/4;
        $(".pizza-of-four-price").text(parseInt(firstPartPrice + secondPartPrice + thirdPartPrice + fourthPartPrice + ""));
    }
    else
        $(".add-to-cart-pizza").prop("disabled", true);

    $(".select-pizza").change(function () {
        if ($(this).val() === "none"){
            $("." + $(this).attr("id") + " .not-selected").show();
            $("." + $(this).attr("id") + " .selected").remove();
        }else {
            $("." + $(this).attr("id") + " .not-selected").hide();
            $("." + $(this).attr("id")).append("<p class='selected'>" + $(this).val() + "</p>");
        }
        let firstPartPrice = parseInt($("#first-part option:selected").data("price"))/4;
        let secondPartPrice = parseInt($("#second-part option:selected").data("price"))/4;
        let thirdPartPrice = parseInt($("#third-part option:selected").data("price"))/4;
        let fourthPartPrice = parseInt($("#fourth-part option:selected").data("price"))/4;
        $(".pizza-of-four-price").text(parseInt(firstPartPrice + secondPartPrice + thirdPartPrice + fourthPartPrice + ""));
        if (firstPartPrice !== 0 && secondPartPrice !== 0 && thirdPartPrice !== 0 && fourthPartPrice !== 0){
            $(".add-to-cart-pizza").prop("disabled", false);
            let share = $(".share-container");
            share.show();
            let share_url = window.location.href;
            share.css("display", "flex");
            let first_dish = $("#first-part option:selected").data("id");
            let second_dish = $("#second-part option:selected").data("id");
            let third_dish = $("#third-part option:selected").data("id");
            let fourth_dish = $("#fourth-part option:selected").data("id");
            let url = share_url + "?" + "pizzaOfFour=" + first_dish  + "," + second_dish
                + "," + third_dish + "," + fourth_dish;
            $(".share-tg").prop("href", "https://t.me/share/url?url=" + url);
            $(".share-fb").prop("href", "https://www.facebook.com/sharer/sharer.php/url?url=" + url);
            $(".share-twitter").prop("href", "https://twitter.com/intent/tweet?url=" + url);
        }else{
            $(".add-to-cart-pizza").prop("disabled", true);
        }
    })
})