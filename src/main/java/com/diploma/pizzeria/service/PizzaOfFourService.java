package com.diploma.pizzeria.service;

import com.diploma.pizzeria.entities.PizzaOfFour;
import com.diploma.pizzeria.repositories.PizzaOfFourRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PizzaOfFourService {

    private PizzaOfFourRepository repository;

    @Autowired
    public void setRepository(PizzaOfFourRepository repository) {
        this.repository = repository;
    }

    public void savePizza(PizzaOfFour pizza){
        repository.save(pizza);
    }

    public PizzaOfFour findPizzaById(Long id) {
        return repository.findPizzaOfFourById(id);
    }

    public List<PizzaOfFour> findAll(){
         return repository.findAll();
    }
}