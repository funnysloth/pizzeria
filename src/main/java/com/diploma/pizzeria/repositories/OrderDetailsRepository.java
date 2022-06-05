package com.diploma.pizzeria.repositories;

import com.diploma.pizzeria.entities.OrderDetails;
import com.diploma.pizzeria.entities.OrderDetailsKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderDetailsRepository extends JpaRepository<OrderDetails, OrderDetailsKey> {
    List<OrderDetails> findAllByOrder_Id(Long id);
}