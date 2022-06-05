package com.diploma.pizzeria.entities;

import javax.persistence.*;

@Entity
@Table(name = "order_pizza_of_four")
public class OrderPizzaOfFour {

    @EmbeddedId
    private OrderPizzaOfFourKey id;

    @ManyToOne
    @MapsId("orderId")
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @MapsId("pizzaOfFourId")
    @JoinColumn(name = "pizza_of_four_id")
    private PizzaOfFour pizzaOfFour;

    @Column(name = "quantity")
    private Long quantity;

    public OrderPizzaOfFour(Order order, PizzaOfFour pizzaOfFour, Long quantity) {
        this.id = new OrderPizzaOfFourKey(order.getId(), pizzaOfFour.getId());
        this.order = order;
        this.pizzaOfFour = pizzaOfFour;
        this.quantity = quantity;
    }

    public OrderPizzaOfFour() {
    }

    public OrderPizzaOfFourKey getId() {
        return id;
    }

    public void setId(OrderPizzaOfFourKey id) {
        this.id = id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public PizzaOfFour getPizzaOfFour() {
        return pizzaOfFour;
    }

    public void setPizzaOfFour(PizzaOfFour pizzaOfFour) {
        this.pizzaOfFour = pizzaOfFour;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "OrderPizzaOfFour{" +
                "id=" + id +
                ", order=" + order +
                ", pizzaOfFour=" + pizzaOfFour +
                ", quantity=" + quantity +
                '}';
    }
}