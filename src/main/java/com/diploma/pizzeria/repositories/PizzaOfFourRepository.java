package com.diploma.pizzeria.repositories;

import com.diploma.pizzeria.entities.Dish;
import com.diploma.pizzeria.entities.PizzaOfFour;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface PizzaOfFourRepository extends JpaRepository<PizzaOfFour, Long> {
    PizzaOfFour findPizzaOfFourById(Long id);
    List<PizzaOfFour> findAll();
}