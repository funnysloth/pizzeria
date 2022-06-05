package com.diploma.pizzeria.entities;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name="menu")
public class Dish {

    @Id
    @SequenceGenerator(name = "s_menu", initialValue = 50, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "s_menu")
    private Long id;
    private String dishName;
    private int weight;
    private int price;
    private String description;
    private String picturePath;
    private int categoryId;
    private String status;

    @OneToMany(mappedBy = "dish")
    private Set<OrderDetails> orders;

    public Dish(Long id, String dishName, int weight, int price, String description, String picturePath, int categoryId, String status) {
        this.id = id;
        this.dishName = dishName;
        this.weight = weight;
        this.price = price;
        this.description = description;
        this.picturePath = picturePath;
        this.categoryId = categoryId;
        this.status = status;
    }

    public Dish() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPicturePath() {
        return picturePath;
    }

    public void setPicturePath(String picturePath) {
        this.picturePath = picturePath;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Set<OrderDetails> getOrders() {
        return orders;
    }

    public void setOrders(Set<OrderDetails> orders) {
        this.orders = orders;
    }

    @Override
    public String toString() {
        return "Dish{" +
                "id=" + id +
                ", dishName='" + dishName + '\'' +
                ", weight=" + weight +
                ", price=" + price +
                ", description='" + description + '\'' +
                ", picturePath='" + picturePath + '\'' +
                ", categoryId=" + categoryId +
                ", status='" + status + '\'' +
                '}';
    }
}