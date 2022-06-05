package com.diploma.pizzeria.repositories;

import com.diploma.pizzeria.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface CategoriesRepository extends JpaRepository<Category, Integer> {
    List<Category> findAllByOrderById();
    Category findCategoryByCategoryEng(String categoryEng);
}