package com.diploma.pizzeria.service;

import com.diploma.pizzeria.entities.OrderPizzaOfFour;
import com.diploma.pizzeria.repositories.OrderPizzaOfFourRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderPizzaOfFourService {

    private OrderPizzaOfFourRepository repository;

    @Autowired
    public void setRepository(OrderPizzaOfFourRepository repository) {
        this.repository = repository;
    }

    public void delete(OrderPizzaOfFour orderPizzaOfFour){
        repository.delete(orderPizzaOfFour);
    }

    public void saveOrderPizzaOfFour(OrderPizzaOfFour orderPizzaOfFour){
        repository.save(orderPizzaOfFour);
    }

    public List<OrderPizzaOfFour> findOrderPizzaOfFourByOrderId(Long id){
        return repository.findAllByOrder_Id(id);
    }
}