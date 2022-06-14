var $ = jQuery;

$(document).ready(function($) {

    let pizzaOfFour = new URL(window.location.href).searchParams.get("pizzaOfFour");
    if (pizzaOfFour.length > 0) {
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
    }
})