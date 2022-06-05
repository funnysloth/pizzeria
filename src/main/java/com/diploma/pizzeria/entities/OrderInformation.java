package com.diploma.pizzeria.entities;

import java.util.List;

//Object to gather all of the information about order and send it to view_order html page
public class OrderInformation {

    private Order order;
    private List<Item> items;
    private List<Item> pizzaOfFourItems;

    public OrderInformation(Order order, List<Item> items, List<Item> pizzaOfFourItems) {
        this.order = order;
        this.items = items;
        this.pizzaOfFourItems = pizzaOfFourItems;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public List<Item> getPizzaOfFourItems() {
        return pizzaOfFourItems;
    }

    public void setPizzaOfFourItems(List<Item> pizzaOfFourItems) {
        this.pizzaOfFourItems = pizzaOfFourItems;
    }

    @Override
    public String toString() {
        return "OrderInformation{" +
                "order=" + order +
                ", items=" + items +
                ", pizzaOfFourItems=" + pizzaOfFourItems +
                '}';
    }
}