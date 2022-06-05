package com.diploma.pizzeria.entities;

import javax.persistence.*;

@Entity
@Table(name= "order_details")
public class OrderDetails {

    @EmbeddedId
    private OrderDetailsKey id;

    @ManyToOne
    @MapsId("orderId")
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @MapsId("dishId")
    @JoinColumn(name = "dish_id")
    private Dish dish;

    @Column(name = "quantity")
    private Long quantity;

    public OrderDetails(Order order, Dish dish, Long quantity) {
        this.id = new OrderDetailsKey(order.getId(), dish.getId());
        this.order = order;
        this.dish = dish;
        this.quantity = quantity;
    }

    public OrderDetails() {
    }

    public OrderDetailsKey getId() {
        return id;
    }

    public void setId(OrderDetailsKey id) {
        this.id = id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Dish getDish() {
        return dish;
    }

    public void setDish(Dish dish) {
        this.dish = dish;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;}

    @Override
    public String toString() {
        return "OrderDetails{" +
                "id=" + id +
                ", order=" + order +
                ", dish=" + dish +
                ", quantity=" + quantity +
                '}';
    }
}