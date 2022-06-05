package com.diploma.pizzeria.entities;


import java.util.Collection;
import java.util.List;

//Object to work with cart
public class Item {

    private Dish dish;
    private List<Dish> dishes;
    private int price;
    private String pizzaOfFourId;//four ids of dishes with spaces between them
    private int quantity;
    private int sumPrice;

    //constructor for dish cart entity
    public Item(Dish dish, int quantity) {
        this.dish = dish;
        this.quantity = quantity;
        setSumPrice(quantity);
    }

    //constructor for pizzaOfFour cart entity
    public Item(Collection<Dish> dishes, int price, int quantity, String pizzaOfFourId) {
        this.dishes = (List<Dish>) dishes;
        this.price = price;
        this.quantity = quantity;
        this.pizzaOfFourId = pizzaOfFourId;
        setSumPricePizzaOfFour(quantity, price);
    }

    public Item(String pizzaOfFourId, int quantity) {
        this.pizzaOfFourId = pizzaOfFourId;
        this.quantity = quantity;
    }

    public Item() {
    }

    public Dish getDish() {
        return dish;
    }

    public void setDish(Dish dish) {
        this.dish = dish;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }


    public int getSumPrice() {
        return sumPrice;
    }

    public void setSumPrice(int quantity) {
        this.sumPrice = getDish().getPrice() * quantity;
    }

    public void setSumPricePizzaOfFour(int quantity, int price){
        this.sumPrice = price * quantity;
    }

    public List<Dish> getDishes() {
        return dishes;
    }

    public void setDishes(List<Dish> dishes) {
        this.dishes = dishes;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getPizzaOfFourId() {
        return pizzaOfFourId;
    }

    public void setPizzaOfFourId(String pizzaOfFourId) {
        this.pizzaOfFourId = pizzaOfFourId;
    }

    @Override
    public String toString() {
        return "Item{" +
                "dish=" + dish +
                ", dishes=" + dishes +
                ", price=" + price +
                ", pizzaOfFourId='" + pizzaOfFourId + '\'' +
                ", quantity=" + quantity +
                ", sumPrice=" + sumPrice +
                '}';
    }
}