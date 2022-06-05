package com.diploma.pizzeria.service;

import com.diploma.pizzeria.entities.OrderDetails;
import com.diploma.pizzeria.repositories.OrderDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderDetailsService {

    private OrderDetailsRepository repository;

    @Autowired
    public void setRepository(OrderDetailsRepository repository) {
        this.repository = repository;
    }

    public void delete(OrderDetails orderDetails){
        repository.delete(orderDetails);
    }

    public List<OrderDetails> findByOrderId(Long id){
        return repository.findAllByOrder_Id(id);
    }

    public void saveOrderDetails(OrderDetails orderDetails){
        repository.save(orderDetails);
    }
}