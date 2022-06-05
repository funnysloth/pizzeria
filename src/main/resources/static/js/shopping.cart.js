var $ = jQuery;

// ************************************************
// Shopping Cart API
// ************************************************

var shoppingCart = (function() {
    // =============================
    // Private methods and propeties
    // =============================
    cart = [];

    // Constructor
    function Item(id, price, count) {
        this.id = id;
        this.price = price;
        this.count = count;
        this.totalPrice = price;
    }

    // Save cart
    function saveCart() {
        for (let i = 0; i < cart.length; i++) {
            console.log(cart[i].count);
        }
        console.log(cart);
        Cookies.set("cart",JSON.stringify(cart), {expires: 1});
        $('#cart-img').hide();
        let cartQuantity = $('#cart-quantity');
        cartQuantity.text(shoppingCart.totalCount());
        cartQuantity.show();
    }

    // Load cart
    function loadCart() {
        cart = JSON.parse(Cookies.get('cart'));
        console.log(cart)
    }

    if (Cookies.get("cart") != null ) {
        loadCart();
        $('#cart-img').hide();
        if (cart.length > 0) {
            let cartQuantity = $('#cart-quantity');
            cartQuantity.text(cart.length);
            cartQuantity.show();
        }
    }


    // =============================
    // Public methods and propeties
    // =============================
    var obj = {};

    // Add to cart
    obj.addItemToCart = function(id, price, count) {
        for(var item in cart) {
            if(cart[item].id === id) {
                cart[item].count ++;
                cart[item].totalPrice = price * cart[item].count;
                saveCart();
                return;
            }
        }
        //console.log(count);
        var item = new Item(id, price, count);
        cart.push(item);
        saveCart();
    }

    obj.increaseItemCount = function(id){
        for(var item in cart) {
            if(cart[item].id === id) {
                cart[item].count ++;
                cart[item].totalPrice = cart[item].price * cart[item].count;
                $(".cart-overall[data-id='" + id + "'] .price").html("<p>"+ cart[item].totalPrice + "</p>грн");
                saveCart();
                $(".totalPrice").html("<h3 class='h3 justify-center'> До сплати:  " + obj.totalCart() + " грн</h3>")
                return;
            }
        }
    };
    // Set count from item
    obj.setCountForItem = function(id, count) {
        for(var i in cart) {
            if (cart[i].id === id) {
                cart[i].count = count;
                cart[i].totalPrice = cart[i].price * cart[i].count;
                $(".cart-overall[data-id='" + id + "'] .price").html("<p>"+ cart[i].totalPrice + "</p>грн");
                break;
            }
        }
        saveCart();
        $(".totalPrice").html("<h3 class='h3 justify-center'> До сплати:  " + obj.totalCart() + " грн</h3>")
    };
    // Remove item from cart
    obj.removeItemFromCart = function(id) {
        for(var item in cart) {
            if(cart[item].id === id) {
                cart[item].count --;
                cart[item].totalPrice = cart[item].price * cart[item].count;
                $(".cart-overall[data-id='" + id + "'] .price").html("<p>"+ cart[item].totalPrice + "</p>грн");
                if(cart[item].count === 0) {
                    console.log(cart.indexOf(item));
                    cart.splice(item, 1);
                    $(".cart-overall[data-id='" + id + "']").parent().hide();
                }
                break;
            }
        }
        if (cart.length === 0){
            Cookies.remove("cart");
            $(".cart-overall-container").parent().hide();
            $(".cart-title").append("<div class=\"justify-center empty-cart-text\">\n" +
                "                <p>У вашому кошику поки немає жодної страви. Щоб зробити замовлення, додайте якусь обрану страву до кошику</p>\n" +
                "            </div>");
            $("#cart-quantity").hide()
            $('#cart-img').show();
        }else{
            saveCart();
            $('#cart-quantity').text(shoppingCart.totalCount());
        }
        $(".totalPrice").html("<h3 class='h3 justify-center'> До сплати:  " + obj.totalCart() + " грн</h3>")
    }

    // Remove all items from cart
    obj.removeItemFromCartAll = function(id) {
        for(var item in cart) {
            if(cart[item].id === id) {
                cart.splice(item, 1);
                break;
            }
        }
        if (cart.length === 0){
            Cookies.remove("cart");
            $(".cart-overall-container").parent().hide();
            $(".cart-title").append("<div class=\"justify-center empty-cart-text\">\n" +
                "                <p>У вашому кошику поки немає жодної страви. Щоб зробити замовлення, додайте якусь обрану страву до кошику</p>\n" +
                "            </div>");
            $("#cart-quantity").hide()
            $('#cart-img').show();
        }else {
            saveCart();
            $('#cart-quantity').text(shoppingCart.totalCount());
        }
        $(".cart-overall[data-id='" + id + "']").parent().hide();
        $(".totalPrice").html("<h3 class='h3 justify-center'> До сплати:  " + obj.totalCart() + " грн</h3>")
    }

    // Clear cart
    obj.clearCart = function() {
        cart = [];
        saveCart();
    }

    // Count cart
    obj.totalCount = function() {
        return cart.length;
    }

    // Total cart
    obj.totalCart = function() {
        var totalCart = 0;
        for(var item in cart) {
            totalCart += cart[item].price * cart[item].count;
        }
        return Number(totalCart.toFixed(2));
    }

    // cart : Array
    // Item : Object/Class
    // addItemToCart : Function
    // removeItemFromCart : Function
    // removeItemFromCartAll : Function
    // clearCart : Function
    // countCart : Function
    // totalCart : Function
    // listCart : Function
    // saveCart : Function
    // loadCart : Function
    return obj;
})();

// *****************************************
// Triggers / Events
// *****************************************
// Add item
$('.add-to-cart').click(function(event) {
    event.preventDefault();
    var id = $(this).data('id');
    var price = Number($(this).data('price'));
    shoppingCart.addItemToCart(id, price, 1);
});

$(".inc").click(function (){
    let number = $(".quantity-input[data-id='" + $(this).data("id") + "']");
    shoppingCart.increaseItemCount($(this).data('id'));
    number.val(parseInt(number.val()) + 1);
});

$(".dec-btn").click(function(){
    let number = $(".quantity-input[data-id='" + $(this).data("id") + "']");
    shoppingCart.removeItemFromCart($(this).data('id'));
    number.val(parseInt(number.val()) - 1);
});

$(".quantity-input").change(function () {
    let id = $(this).data('id');
    if($(this).val() < 1)
        $(this).val(1);
    shoppingCart.setCountForItem(id, $(this).val());
})

$(".remove-from-cart").click(function () {
    shoppingCart.removeItemFromCartAll($(this).data("id"))
})

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
    $(".pizza-of-four-price").text(parseInt(firstPartPrice + secondPartPrice + thirdPartPrice + fourthPartPrice));
    if (firstPartPrice !== 0 && secondPartPrice !== 0 && thirdPartPrice !== 0 && fourthPartPrice !== 0){
        $(".add-to-cart-pizza").prop("disabled", false);
    }else{
        $(".add-to-cart-pizza").prop("disabled", true);
    }
})

$(".add-to-cart-pizza").click(function () {
    let id = $("#first-part option:selected").data("id") + " " + $("#second-part option:selected").data("id") + " " +
                $("#third-part option:selected").data("id") + " " + $("#fourth-part option:selected").data("id");
    let price = $(".pizza-of-four-price").text();
    shoppingCart.addItemToCart(id, price, 1);
})

$(document).ready(function () {
    $(".add-to-cart-pizza").prop("disabled", true);
})