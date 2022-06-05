package com.diploma.pizzeria.entities;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

//Composite primary key for OrderDetails entity
@Embeddable
public class OrderDetailsKey implements Serializable {

    @Column(name="order_id")
    Long orderId;

    @Column(name="dish_id")
    Long dishId;

    public OrderDetailsKey() {
    }

    public OrderDetailsKey(Long orderId, Long dishId) {
        this.orderId = orderId;
        this.dishId = dishId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getDishId() {
        return dishId;
    }

    public void setDishId(Long dishId) {
        this.dishId = dishId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderDetailsKey that = (OrderDetailsKey) o;
        return Objects.equals(orderId, that.orderId) && Objects.equals(dishId, that.dishId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, dishId);
    }

    @Override
    public String toString() {
        return "OrderDetailsKey{" +
                "orderId=" + orderId +
                ", dishId=" + dishId +
                '}';
    }
}