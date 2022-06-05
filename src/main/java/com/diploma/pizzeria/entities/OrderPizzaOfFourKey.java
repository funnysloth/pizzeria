package com.diploma.pizzeria.entities;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

// Composite primary key for PizzaOfFour entity
@Embeddable
public class OrderPizzaOfFourKey implements Serializable {

    @Column(name = "order_id")
    Long orderId;

    @Column(name = "pizza_of_four_id")
    Long pizzaOfFourId;

    public OrderPizzaOfFourKey(Long orderId, Long pizzaOfFourId) {
        this.orderId = orderId;
        this.pizzaOfFourId = pizzaOfFourId;
    }

    public OrderPizzaOfFourKey() {
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getPizzaOfFourId() {
        return pizzaOfFourId;
    }

    public void setPizzaOfFourId(Long pizzaOfFourId) {
        this.pizzaOfFourId = pizzaOfFourId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderPizzaOfFourKey that = (OrderPizzaOfFourKey) o;
        return orderId.equals(that.orderId) && pizzaOfFourId.equals(that.pizzaOfFourId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, pizzaOfFourId);
    }

    @Override
    public String toString() {
        return "OrderPizzaOfFourKey{" +
                "orderId=" + orderId +
                ", pizzaOfFourId=" + pizzaOfFourId +
                '}';
    }
}