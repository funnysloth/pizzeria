package com.diploma.pizzeria.controllers;

import com.diploma.pizzeria.entities.Category;
import com.diploma.pizzeria.entities.Dish;
import com.diploma.pizzeria.service.CategoriesService;
import com.diploma.pizzeria.service.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class PizzaOfFourController {

    private static CategoriesService categoriesService;
    private static DishService dishService;

    @Autowired
    public void setService(CategoriesService service) {
        categoriesService = service;
    }

    @Autowired
    public void setService(DishService service) {
        dishService = service;
    }

    @GetMapping("/pizza-of-four")
    public String pizzaOfFour(Model model, HttpServletRequest request, HttpSession session){
        model.addAttribute("user", session.getAttribute("user"));
        model.addAttribute("title", "Головна сторінка");
        model.addAttribute("categories", categoriesService.getAllCategories());
        Category categoryPizza = categoriesService.getCategoryByCategoryEng("pizza");
        model.addAttribute("dishesPizza", dishService.getDishesByCategory(categoryPizza.getId()));
        return "pizza_of_four";
    }
}