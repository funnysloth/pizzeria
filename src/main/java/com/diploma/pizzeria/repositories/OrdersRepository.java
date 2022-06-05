package com.diploma.pizzeria.repositories;

import com.diploma.pizzeria.entities.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public interface OrdersRepository extends JpaRepository<Order, Long> , PagingAndSortingRepository<Order, Long> {
    Page<Order> findAllByOrderByOrderDateDesc(Pageable pageable);
    Page<Order> findAllByOrderDateBetweenOrderByOrderDateDesc(Timestamp start, Timestamp end, Pageable pageable);
    List<Order> findAllByOrderDateBetweenOrderByOrderDateDesc(Timestamp start, Timestamp end);
    Page<Order> findAllByOrderDateBeforeOrderByOrderDateDesc(Timestamp end, Pageable pageable);
    List<Order> findAllByOrderDateBeforeOrderByOrderDateDesc(Timestamp end);
    Page<Order> findAllByOrderDateAfterOrderByOrderDateDesc(Timestamp start, Pageable pageable);
    List<Order> findAllByOrderDateAfterOrderByOrderDateDesc(Timestamp start);
    Optional<Order> findById(long id);
    List<Order> findAllByUserIdOrderByOrderDateDesc(long id);
}