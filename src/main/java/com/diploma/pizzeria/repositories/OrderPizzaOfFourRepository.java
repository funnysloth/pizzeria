package com.diploma.pizzeria.repositories;

import com.diploma.pizzeria.entities.OrderPizzaOfFour;
import com.diploma.pizzeria.entities.OrderPizzaOfFourKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderPizzaOfFourRepository extends JpaRepository<OrderPizzaOfFour, OrderPizzaOfFourKey> {
    List<OrderPizzaOfFour> findAllByOrder_Id(Long id);
}