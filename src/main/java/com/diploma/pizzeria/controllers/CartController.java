package com.diploma.pizzeria.controllers;

import com.diploma.pizzeria.entities.*;
import com.diploma.pizzeria.service.*;
import com.ibm.commons.util.io.json.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

@Controller
public class CartController {

    private static DishService dishService;
    private static CategoriesService categoriesService;
    private static OrdersService ordersService;
    private static OrderDetailsService orderDetailsService;
    private static PizzaOfFourService pizzaOfFourService;
    private static OrderPizzaOfFourService orderPizzaOfFourService;

    @Autowired
    public void setService(DishService service) {
        dishService = service;
    }

    @Autowired
    public void setService(CategoriesService service) {
        categoriesService = service;
    }

    @Autowired
    public void setService(OrdersService service) {
        ordersService = service;
    }

    @Autowired
    public void setService(OrderDetailsService service) {
        orderDetailsService = service;
    }

    @Autowired
    public void setService(PizzaOfFourService service) {pizzaOfFourService = service;}

    @Autowired
    public void setService(OrderPizzaOfFourService service) {orderPizzaOfFourService = service;}

    @GetMapping("/cart")
    public String cart(Model model, HttpSession session, HttpServletRequest request) throws JsonException, ParseException {
        List<Item> cart = new ArrayList<>();
        Cookie[] coockies = request.getCookies();
        JSONParser parser = new JSONParser();
        for (Cookie coockie: coockies) { ///getting cart information from coockies to display it on the page
            if (coockie.getName().equals("cart")) {
                String value = URLDecoder.decode(coockie.getValue(), StandardCharsets.UTF_8);
                try {
                    JSONArray dishes = (JSONArray) parser.parse(value);
                    Iterator<JSONObject> i = dishes.iterator();
                    while (i.hasNext()){
                        JSONObject dish = i.next();
                        if (dish.get("id").toString().contains(" ")){       //checking if there's pizza of four pieces in the cart
                            String[] idList = dish.get("id").toString().split(" ");
                            List<Dish> pizza = new ArrayList<>();
                            for (String id: idList) {
                                pizza.add(dishService.getById((long) Integer.parseInt(id)));
                            }
                            cart.add(new Item(pizza,Integer.parseInt(dish.get("price").toString()),Integer.parseInt(dish.get("count").toString()), (String) dish.get("id")));
                        }else
                            cart.add(new Item(dishService.getById((Long) dish.get("id")), Integer.parseInt(dish.get("count").toString())));
                    }
                }catch (ParseException e){
                    e.printStackTrace();
                }
            }
        }
        if (cart.size() == 0)
            model.addAttribute("cart", null);
        else {
            int totalPrice = 0;
            for (Item dish: cart) {
                totalPrice += dish.getSumPrice();
            }
            model.addAttribute("totalPrice", totalPrice);
            model.addAttribute("cart", cart);
        }
        model.addAttribute("categories", categoriesService.getAllCategories());
        model.addAttribute("user", session.getAttribute("user"));
        return "cart/cart";
    }

    @GetMapping("/order")
    public String order(Model model, HttpSession session, HttpServletRequest request){
        Cookie[] coockies = request.getCookies();
        JSONParser parser = new JSONParser();
        int totalPrice = 0;
        for (Cookie coockie: coockies) {
            if (coockie.getName().equals("cart")) {
                String value = URLDecoder.decode(coockie.getValue(), StandardCharsets.UTF_8);
                try {
                    JSONArray dishes = (JSONArray) parser.parse(value);
                    Iterator<JSONObject> i = dishes.iterator();
                    while (i.hasNext()){
                        JSONObject dish = i.next();
                        totalPrice += Integer.parseInt(dish.get("price").toString());//counting total price of the cart for online payment
                    }
                }catch (ParseException e){
                    e.printStackTrace();
                }
            }
        }
        model.addAttribute("totalPrice", totalPrice);
        session.setAttribute("totalPrice", totalPrice);
        model.addAttribute("categories", categoriesService.getAllCategories());
        model.addAttribute("user", session.getAttribute("user"));
        model.addAttribute("order", new Order());
        return "cart/order";
    }

    @PostMapping("/order")
    public String validateOrder(@ModelAttribute(name = "order") Order order, HttpServletRequest request,
                                @RequestParam("street") String street, @RequestParam("house") String house,
                                @RequestParam(value = "entrance", required = false) String entrance,
                                @RequestParam(value = "apartment", required = false) String apartment,
                                @RequestParam(value = "code", required = false) String code, HttpSession session,
                                HttpServletResponse response){
        User user = (User) session.getAttribute("user");
        if (user != null) {
            order.setUserId(user.getId());
            order.setFullName(user.getFullName());
            order.setEmail(user.getEmail());
        }
        order.setAddress("" + street + "," + house);
        if (entrance.length() > 0){
            order.setAddress(order.getAddress() + "," + entrance);
        }
        if (apartment.length() > 0){
            order.setAddress(order.getAddress() + "," + apartment);

        }
        if (code.length() > 0){
            order.setAddress(order.getAddress() + "," + code);

        }
        if (order.getPayment().equals("online")){
            order.setStatus("Сплачено, виконується");
            order.setPayment("онлайн");
        }else{
            order.setStatus("готівкою");
        }
        order.setPrice((Integer) session.getAttribute("totalPrice"));
        order.setOrderDate(Timestamp.valueOf(LocalDateTime.now()));
        ordersService.saveOrder(order);
        Cookie[] coockies = request.getCookies();
        JSONParser parser = new JSONParser();
        for (Cookie coockie: coockies) {///getting cart information from coockies to dave it in the database
            if (coockie.getName().equals("cart")) {
                String value = URLDecoder.decode(coockie.getValue(), StandardCharsets.UTF_8);
                try {
                    JSONArray dishes = (JSONArray) parser.parse(value);
                    Iterator<JSONObject> i = dishes.iterator();
                    while (i.hasNext()){
                        JSONObject dish = i.next();
                        if (dish.get("id").toString().contains(" ")){   //checking if there's pizza of four pieces in the cart
                            String[] idList = dish.get("id").toString().split(" ");
                            Collection<Dish> pizzas = new ArrayList<>();
                            for (String id: idList) {
                                pizzas.add(dishService.getById((long) Integer.parseInt(id)));
                            }
                            List<PizzaOfFour> pizzasOfFour = pizzaOfFourService.findAll();
                            Long id = null;
                            for (PizzaOfFour pizza:pizzasOfFour) {
                                if (pizza.getDishes().containsAll(pizzas))
                                    id = pizza.getId();
                            }
                            if (id != null)
                                orderPizzaOfFourService.saveOrderPizzaOfFour(new OrderPizzaOfFour(order,
                                        pizzaOfFourService.findPizzaById(id), (Long) dish.get("count")));
                            else {
                                PizzaOfFour pizzaOfFour = new PizzaOfFour(Integer.parseInt( dish.get("price").toString()), pizzas);
                                pizzaOfFourService.savePizza(pizzaOfFour);
                                orderPizzaOfFourService.saveOrderPizzaOfFour(new OrderPizzaOfFour(order, pizzaOfFour, (Long) dish.get("count")));
                            }
                        }else
                            orderDetailsService.saveOrderDetails(new OrderDetails(order, dishService.getById((Long) dish.get("id")),
                                    (Long) dish.get("count")));
                    }
                }catch (ParseException e){
                    e.printStackTrace();
                }
                coockie.setMaxAge(0);
                response.addCookie(coockie);
            }
        }
        session.removeAttribute("totalPrice");
        return "redirect:/success";
    }

    @GetMapping("/success")
    public String success(Model model, HttpSession session){
        model.addAttribute("categories", categoriesService.getAllCategories());
        model.addAttribute("user", session.getAttribute("user"));
        return "cart/success";
    }

}