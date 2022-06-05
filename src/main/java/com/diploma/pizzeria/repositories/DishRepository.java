package com.diploma.pizzeria.repositories;

import com.diploma.pizzeria.entities.Dish;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface DishRepository extends JpaRepository<Dish, Long>, PagingAndSortingRepository<Dish, Long> {
    List<Dish> findAllByCategoryId(int categoryId);
    Page<Dish> findAllByCategoryIdOrderByIdDesc(int categoryId, Pageable pageable);
    Page<Dish> findAll(Pageable pageable);
    Page<Dish> findAllByStatusOrderByIdDesc(String status, Pageable pageable);
    List<Dish> findAllByStatus(String status);
    List<Dish> findAllByCategoryIdAndStatus(int categoryId, String status);
    Page<Dish> findAllByCategoryIdAndStatusOrderByIdDesc(int categoryId, String status, Pageable pageable);
    List<Dish> findAllByDishNameIsContaining(String dishName);
    Page<Dish> findAllByDishNameContainsOrderByIdDesc(String search, Pageable pageable);
}