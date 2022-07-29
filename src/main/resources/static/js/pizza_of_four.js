var $ = jQuery;

$(document).ready(function($) {

    let pizza_of_four = new URL(window.location.href).searchParams.get("pizzaOfFour");
    if (pizza_of_four != null) {
        let pizzas = pizza_of_four.split(",");
        let first_part = $("option[data-id='" + pizzas[0] + "']");
        let second_part = $("option[data-id='" + pizzas[1] + "']");
        let third_part = $("option[data-id='" + pizzas[2] + "']");
        let fourth_part = $("option[data-id='" + pizzas[3] + "']");
        let first_part_img = $(".first-part");
        let second_part_img = $(".second-part");
        let third_part_img = $(".third-part");
        let fourth_part_img = $(".fourth-part");
        console.log(pizzas);

        $("#first-part option[data-id='" + pizzas[0] + "']").prop("selected", "selected");
        $(".first-part .not-selected").hide();
        first_part_img.css("background-image", "url(" + first_part.data("picture") + ")");
        first_part_img.css("background-size", "600px 600px");
        $("#second-part option[data-id='" + pizzas[1] + "']").prop("selected", "selected");
        $(".second-part .not-selected").hide();
        second_part_img.css("background-image", "url(" + second_part.data("picture") + ")");
        second_part_img.css("background-size", "600px 600px");
        second_part_img.css('background-position', "top right");
        $("#third-part option[data-id='" + pizzas[2] + "']").prop("selected", "selected");
        $(".third-part .not-selected").hide();
        third_part_img.css("background-image", "url(" + third_part.data("picture") + ")");
        third_part_img.css("background-size", "600px 600px");
        third_part_img.css('background-position', "bottom left")
        $("#fourth-part option[data-id='" + pizzas[3] + "']").prop("selected", "selected");
        $(".fourth-part .not-selected").hide();
        fourth_part_img.css("background-image", "url(" + fourth_part.data("picture") + ")");
        fourth_part_img.css("background-size", "600px 600px");
        fourth_part_img.css('background-position', "bottom right");
        let firstPartPrice = parseInt(first_part.data("price"))/4;
        let secondPartPrice = parseInt(second_part.data("price"))/4;
        let thirdPartPrice = parseInt(third_part.data("price"))/4;
        let fourthPartPrice = parseInt(fourth_part.data("price"))/4;
        $(".pizza-of-four-price").text(parseInt(firstPartPrice + secondPartPrice + thirdPartPrice + fourthPartPrice + ""));
        showShare(true);
    }
    else
        $(".add-to-cart-pizza").prop("disabled", true);

    $(".select-pizza").change(function () {
        if ($(this).val() === "none"){
            $("." + $(this).attr("id") + " .not-selected").show();
            $("." + $(this).attr("id") + " .selected").remove();
        }else {
            $("." + $(this).attr("id") + " .not-selected").hide();
            let pizza_part = $("." + $(this).attr("id"));
            pizza_part.css("background-image", "url(" + $("#" + $(this).attr("id") + " option:selected").data("picture") + ")");
            pizza_part.css("background-size", "600px 600px");
            switch ($(this).attr("id")){
                case 'second-part':
                    pizza_part.css('background-position', "top right");
                    break;
                case 'third-part':
                    console.log("some")
                    pizza_part.css('background-position', "bottom left");
                    break;
                case 'fourth-part':
                    pizza_part.css('background-position', "bottom right");
                    break;
            }
        }
        let first_part_price = parseInt($("#first-part option:selected").data("price"))/4;
        let second_part_price = parseInt($("#second-part option:selected").data("price"))/4;
        let third_part_price = parseInt($("#third-part option:selected").data("price"))/4;
        let fourth_part_price = parseInt($("#fourth-part option:selected").data("price"))/4;
        $(".pizza-of-four-price").text(parseInt(first_part_price + second_part_price + third_part_price + fourth_part_price + ""));
        if (first_part_price !== 0 && second_part_price !== 0 && third_part_price !== 0 && fourth_part_price !== 0){
            showShare(false);
        }else{
            $(".add-to-cart-pizza").prop("disabled", true);
        }
    })

    function showShare(shared){
        $(".add-to-cart-pizza").prop("disabled", false);
        let share = $(".share-container");
        share.show();
        let url = window.location.href;
        share.css("display", "flex");
        if(!shared){
            let first_dish = $("#first-part option:selected").data("id");
            let second_dish = $("#second-part option:selected").data("id");
            let third_dish = $("#third-part option:selected").data("id");
            let fourth_dish = $("#fourth-part option:selected").data("id");
            url = url + "?" + "pizzaOfFour=" + first_dish + "," + second_dish
                + "," + third_dish + "," + fourth_dish;
        }
        $(".share-tg").prop("href", "https://t.me/share/url?url=" + url);
        $(".share-fb").prop("href", "https://www.facebook.com/sharer/sharer.php/url?url=" + url);
        $(".share-twitter").prop("href", "https://twitter.com/intent/tweet?url=" + url);
    }

    $(".copy-link-btn").click(function () {
        if (pizza_of_four != null){
            navigator.clipboard.writeText(window.location.href).then(function () {

            })
        }
        else {
            navigator.clipboard.writeText(window.location.href + $("#first-part option:selected").data("id") + ","
                + $("#second-part option:selected").data("id") + "," + $("#third-part option:selected").data("id") + ","
                + $("#fourth-part option:selected").data("id")).then(function () {

            })
        }
    })
})