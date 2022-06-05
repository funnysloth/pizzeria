package com.diploma.pizzeria.controllers;

import com.diploma.pizzeria.entities.*;
import com.diploma.pizzeria.service.*;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class AccountController {

    private static CategoriesService categoriesService;
    private static UserService userService;
    private static OrdersService ordersService;
    private static OrderDetailsService orderDetailsService;
    private static OrderPizzaOfFourService orderPizzaOfFourService;

    @Autowired
    public void setService(UserService service) {
        userService = service;
    }

    @Autowired
    public void setService(CategoriesService service) {
        categoriesService = service;
    }


    @Autowired
    public void setService(OrdersService service) {ordersService = service;}

    @Autowired
    public void setService(OrderDetailsService service) {orderDetailsService = service;}

    @Autowired
    public void setService(OrderPizzaOfFourService service) {orderPizzaOfFourService = service;}

    @GetMapping("/user/account")
    public String account(Model model, HttpSession session) {
        if (session.getAttribute("user") == null)
            return "redirect:/";
        model.addAttribute("user", (User) session.getAttribute("user"));
        model.addAttribute("title", "Головна сторінка");
        model.addAttribute("categories", categoriesService.getAllCategories());
        return "user/account";
    }

    @PostMapping("/user/account")
    private String validateChanges(Model model, @RequestParam("email") String email,
                                   @RequestParam(value = "passwd", required = false) String password,
                                   @RequestParam("fullName") String fullName,
                                   HttpSession session,
                                   @RequestParam("changeEmail") String changeEmail){
        if (session.getAttribute("user") == null)
            return "redirect:/";
        boolean fail = false;
        Validation validation = new Validation();
        if (!validation.checkEmail(email)){
            model.addAttribute("emailFailed", true);
            fail = true;
        }
        if (!validation.checkName(fullName)){
            model.addAttribute("nameFailed", true);
            fail = true;
        }
        if (!password.isBlank()) {
            if (!validation.checkPassword(password)) {
                model.addAttribute("passFailed", true);
                fail = true;
            }
        }
        if (fail) {
            model.addAttribute("user", session.getAttribute("user"));
            model.addAttribute("title", "Головна сторінка");
            model.addAttribute("categories", categoriesService.getAllCategories());
            return "/user/account";
        }
        if (changeEmail.equals("true")) {
            if (userService.existsWithEmail(email)) {
                model.addAttribute("messageEmail", "Акаунт з даною поштою вже існує");
                model.addAttribute("emailFailed", true);
                return "/user/account";
            }
        }
        User user = (User) session.getAttribute("user");
        user.setEmail(email);
        user.setFullName(fullName);
        if (!password.isBlank())
            user.setPass(DigestUtils.sha256Hex(password));
        userService.saveUser(user);
        session.setAttribute("user", userService.getUserById(user.getId()));
        model.addAttribute("title", "Головна сторінка");
        model.addAttribute("user", session.getAttribute("user"));
        model.addAttribute("categories", categoriesService.getAllCategories());
        return "/user/account";
    }

    @GetMapping("/user/my_orders")
    public String orders(Model model, HttpSession session){
        if (session.getAttribute("user") == null)
            return "redirect:/";
        model.addAttribute("user", session.getAttribute("user"));
        model.addAttribute("title", "Головна сторінка");
        model.addAttribute("categories", categoriesService.getAllCategories());
        User user = (User) session.getAttribute("user");
        List<Order> orders = ordersService.getOrdersByUserId(user.getId());
        List<OrderInformation> ordersInformation = new ArrayList<>();
        for (Order order: orders) {
            List<OrderDetails> orderDetails = orderDetailsService.findByOrderId(order.getId());
            List<OrderPizzaOfFour> orderPizzaOfFours = orderPizzaOfFourService.findOrderPizzaOfFourByOrderId(order.getId());
            List<Item> pizzaOfFourItems = new ArrayList<>();
            List<Item> orderItems = new ArrayList<>();
            for (OrderDetails orderDetail : orderDetails) {
                orderItems.add(new Item(orderDetail.getDish(), Math.toIntExact(orderDetail.getQuantity())));
            }
            for (OrderPizzaOfFour orderPizzaOfFour: orderPizzaOfFours){
                pizzaOfFourItems.add(new Item(orderPizzaOfFour.getPizzaOfFour().getId().toString(),
                        Math.toIntExact(orderPizzaOfFour.getQuantity())));
            }
            ordersInformation.add(new OrderInformation(order, orderItems, pizzaOfFourItems));
        }
        model.addAttribute("ordersInformation", ordersInformation);
        return "user/my_orders";
    }
}