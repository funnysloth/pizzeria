package com.diploma.pizzeria.entities;

import javax.persistence.*;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name= "orders")
public class Order {

    @Id
    @SequenceGenerator(name = "s_orders", initialValue = 30, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "s_orders")
    private Long id;
    private String fullName;
    private String phoneNumber;
    private String email;
    private String status;
    @Column(name = "commentary")
    private String comment;
    private Timestamp orderDate;
    private String address;
    private Long userId;
    private String payment;
    private String change;
    private int price;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<OrderDetails> order = new HashSet<>();

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<OrderPizzaOfFour> orders = new HashSet<>();

    public Order(String fullName, String phoneNumber, String email, String status, String comment, Timestamp orderDate, String address, int price) {
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.status = status;
        this.comment = comment;
        this.orderDate = orderDate;
        this.address = address;
        this.price = price;
    }

    public Order(String phoneNumber, String status, String comment, Timestamp orderDate, String address, Long userId, int price) {
        this.phoneNumber = phoneNumber;
        this.status = status;
        this.comment = comment;
        this.orderDate = orderDate;
        this.address = address;
        this.userId = userId;
        this.price = price;
    }

    public Order() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getOrderDate() throws ParseException {
        return new SimpleDateFormat("dd-MM-yyyy HH:mm").format(orderDate);
    }

    public void setOrderDate(Timestamp orderDate) {
        this.orderDate = orderDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public String getChange() {
        return change;
    }

    public void setChange(String change) {
        this.change = change;
    }

    public Set<OrderDetails> getOrder() {
        return order;
    }

    public void setOrder(Set<OrderDetails> order) {
        this.order = order;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Set<OrderPizzaOfFour> getOrders() {
        return orders;
    }

    public void setOrders(Set<OrderPizzaOfFour> orders) {
        this.orders = orders;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", fullName='" + fullName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                ", status='" + status + '\'' +
                ", comment='" + comment + '\'' +
                ", orderDate=" + orderDate +
                ", address='" + address + '\'' +
                ", userId=" + userId +
                ", payment='" + payment + '\'' +
                ", change='" + change + '\'' +
                ", price=" + price +
                '}';
    }
}