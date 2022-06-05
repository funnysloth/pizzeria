package com.diploma.pizzeria.controllers;

import com.diploma.pizzeria.entities.*;
import com.diploma.pizzeria.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Controller
public class OrdersController {

    private static OrdersService ordersService;
    private static OrderDetailsService orderDetailsService;
    private static OrderPizzaOfFourService orderPizzaOfFourService;

    @Autowired
    public void setService(OrderDetailsService service) {orderDetailsService = service;}

    @Autowired
    public void setService(OrderPizzaOfFourService service) {orderPizzaOfFourService = service;}

    @Autowired
    public void setService(OrdersService service) {
        ordersService = service;
    }


    @GetMapping("/orders")
    public String orders(Model model, HttpSession session, @RequestParam("page") int page){
        User user = (User) session.getAttribute("user");
        if (user == null || !user.getUserRole().contains("operator"))
            return "redirect:/";
        model.addAttribute("user", session.getAttribute("user"));
        model.addAttribute("pages", ordersService.definePageNumber(20));
        model.addAttribute("orders", ordersService.getAllOrdersPaging(page-1, 20));
        int income = 0;
        List<Order> orders = ordersService.getAllOrders();
        for (Order order:orders) {
            income += order.getPrice();
        }
        model.addAttribute("income", income);
        model.addAttribute("filter", false);
        return "operator/orders";
    }

    @GetMapping("/orders/filter")
    public String filter(Model model, HttpSession session, @RequestParam("page") int page, @RequestParam("start") String start,
                         @RequestParam("end") String end){
        User user = (User) session.getAttribute("user");
        if (user == null || !user.getUserRole().contains("operator"))
            return "redirect:/";
        if (start.equals("") && end.equals(""))
            return "redirect:/orders?page=1";
        int income = 0;
        if (!start.equals("") && end.equals("")) {
            Timestamp startDate = Timestamp.valueOf(start + " 00:00:00");
            model.addAttribute("pages", ordersService.definePageNumberForOrderDateAfter(20, startDate));
            model.addAttribute("orders", ordersService.getOrdersByOrderDatAfterPaging(page-1, 20, startDate));
            List<Order> orders = ordersService.getOrdersByOrderDateAfter(startDate);
            for (Order order:orders) {
                income += order.getPrice();
            }
        }else if(start.equals("")) {
            Timestamp endDate = Timestamp.valueOf(end + " 23:59:59");
            model.addAttribute("pages", ordersService.definePageNumberForOrderDateBefore(20, endDate));
            model.addAttribute("orders", ordersService.getOrdersByOrderDateBeforePaging(page-1, 20, endDate));
            List<Order> orders = ordersService.getOrdersByOrderDateBefore(endDate);
            for (Order order:orders) {
                income += order.getPrice();
            }
        }
        else{
            Timestamp startDate = Timestamp.valueOf(start + " 00:00:00");
            Timestamp endDate = Timestamp.valueOf(end + " 23:59:59");
            model.addAttribute("pages", ordersService.definePageNumberForOrderDateBetween(20, startDate, endDate));
            model.addAttribute("orders", ordersService.getOrdersByOrderDateBetweenPaging(page-1, 20, startDate, endDate));
            List<Order> orders = ordersService.getOrdersByOrderDateBetween(startDate, endDate);
            for (Order order:orders) {
                income += order.getPrice();
            }
        }
        model.addAttribute("income", income);
        model.addAttribute("user", session.getAttribute("user"));
        model.addAttribute("filter", true);
        model.addAttribute("start", start);
        model.addAttribute("end", end);
        return "operator/orders";
    }

    @GetMapping("/orders/view")
    public String view(Model model, HttpSession session, @RequestParam("id") Long id){
        User user = (User) session.getAttribute("user");
        if (user == null || !user.getUserRole().contains("operator"))
            return "redirect:/";
        model.addAttribute("user", session.getAttribute("user"));
        Order order = ordersService.getOrderById(id);
        String[] address = order.getAddress().split(",");
        model.addAttribute("address", address);
        List<OrderDetails> orderDetails = orderDetailsService.findByOrderId(order.getId());
        List<OrderPizzaOfFour> orderPizzaOfFours = orderPizzaOfFourService.findOrderPizzaOfFourByOrderId(order.getId());
        List<Item> pizzaOfFourItems = new ArrayList<>();
        List<Item> orderItems = new ArrayList<>();
        for (OrderDetails orderDetail : orderDetails) {
            orderItems.add(new Item(orderDetail.getDish(), Math.toIntExact(orderDetail.getQuantity())));
        }
        for (OrderPizzaOfFour orderPizzaOfFour: orderPizzaOfFours){
            pizzaOfFourItems.add(new Item(orderPizzaOfFour.getPizzaOfFour().getDishes(), orderPizzaOfFour.getPizzaOfFour().getPrice(),
                    Math.toIntExact(orderPizzaOfFour.getQuantity()), orderPizzaOfFour.getPizzaOfFour().getId().toString()));
        }
        model.addAttribute("orderInformation", new OrderInformation(order, orderItems, pizzaOfFourItems));
        return "operator/view_order";
    }

    @GetMapping("/orders/finish")
    public String finish(@RequestParam("id") Long id, HttpSession session){
        User user = (User) session.getAttribute("user");
        if (user == null || !user.getUserRole().contains("operator"))
            return "redirect:/";
        Order order = ordersService.getOrderById(id);
        order.setStatus("завершено");
        ordersService.saveOrder(order);
        return "redirect:/orders?page=1";
    }

    @GetMapping("/orders/cancel")
    public String cancel(@RequestParam("id") Long id, HttpSession session){
        User user = (User) session.getAttribute("user");
        if (user == null || !user.getUserRole().contains("operator"))
            return "redirect:/";
        List<OrderDetails> orderDetails = orderDetailsService.findByOrderId(id);
        List<OrderPizzaOfFour> orderPizzaOfFourDetails = orderPizzaOfFourService.findOrderPizzaOfFourByOrderId(id);
        for (OrderDetails orderDetail:orderDetails) {
            orderDetailsService.delete(orderDetail);
        }
        for (OrderPizzaOfFour orderPizzaOfFourDetail:orderPizzaOfFourDetails) {
            orderPizzaOfFourService.delete(orderPizzaOfFourDetail);
        }
        ordersService.deleteOrder(id);
        return "redirect:/orders?page=1";
    }
}