package com.diploma.pizzeria.controllers;

import com.diploma.pizzeria.entities.Category;
import com.diploma.pizzeria.entities.Item;
import com.diploma.pizzeria.entities.User;
import com.diploma.pizzeria.service.CategoriesService;
import com.diploma.pizzeria.service.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.*;
import java.util.ArrayList;
import java.util.List;

@Controller
public class MainController {

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

    @GetMapping("/")
    public String home(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        model.addAttribute("user", session.getAttribute("user"));
        model.addAttribute("title", "Головна сторінка");
        model.addAttribute("categories", categoriesService.getAllCategories());
        Category categoryPizza = categoriesService.getCategoryByCategoryEng("pizza");
        Category categoryBeverages = categoriesService.getCategoryByCategoryEng("beverages");
        model.addAttribute("dishesPizza", dishService.getDishesByCategoryAndStatusPaging(categoryPizza.getId(),"shown", 0, 10));
        model.addAttribute("dishesBeverages", dishService.getDishesByCategoryAndStatusPaging(categoryBeverages.getId(), "shown", 0, 10));
        return "home";
    }

    @GetMapping("/about")
    public String about(Model model, HttpSession session){
        model.addAttribute("user", session.getAttribute("user"));
        model.addAttribute("title", "Головна сторінка");
        model.addAttribute("categories", categoriesService.getAllCategories());
        return "about";
    }

    @GetMapping("/contacts")
    public String contacts(Model model, HttpSession session){
        model.addAttribute("user", session.getAttribute("user"));
        model.addAttribute("title", "Головна сторінка");
        model.addAttribute("categories", categoriesService.getAllCategories());
        return "contacts";
    }
}