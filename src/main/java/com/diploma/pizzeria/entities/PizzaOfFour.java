package com.diploma.pizzeria.entities;

import javax.persistence.*;
import java.util.Collection;
import java.util.Set;

@Entity
@Table(name = "pizza_of_four")
public class PizzaOfFour {

    @Id
    @SequenceGenerator(name = "s_pizza_of_four", initialValue = 3, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "s_pizza_of_four")
    private Long id;
    private int price;

    //Organizing many to many connection with orders
    @ManyToMany
    @JoinTable(name = "pizza", joinColumns = @JoinColumn(name = "pizza_of_four_id", referencedColumnName = "id"),
    inverseJoinColumns = @JoinColumn(name = "pizza_id", referencedColumnName = "id"))
    private Collection<Dish> dishes;

    //Organizing many to many connection with menu
    @OneToMany(mappedBy = "pizzaOfFour")
    private Set<OrderPizzaOfFour> orders;

    public PizzaOfFour( int price, Collection<Dish> dishes) {
        this.price = price;
        this.dishes = dishes;
    }

    public PizzaOfFour() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Collection<Dish> getDishes() {
        return dishes;
    }

    public void setDishes(Collection<Dish> dishes) {
        this.dishes = dishes;
    }

    public Set<OrderPizzaOfFour> getOrders() {
        return orders;
    }

    public void setOrders(Set<OrderPizzaOfFour> orders) {
        this.orders = orders;
    }

    @Override
    public String toString() {
        return "PizzaOfFour{" +
                "id=" + id +
                ", price=" + price +
                '}';
    }
}