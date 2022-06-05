package com.diploma.pizzeria.controllers;

import com.diploma.pizzeria.entities.Category;
import com.diploma.pizzeria.service.CategoriesService;
import com.diploma.pizzeria.service.DishService;
import org.hibernate.tool.schema.internal.exec.ScriptTargetOutputToFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class CategoriesController {

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

    @GetMapping("/category/pizza")
    public String pizza(Model model, @RequestParam int page, HttpSession session){
        model.addAttribute("name", "pizza");
        model.addAttribute("user", session.getAttribute("user"));
        model.addAttribute("cartQuantity", session.getAttribute("cartQuantity"));
        model.addAttribute("categories", categoriesService.getAllCategories());
        Category category = categoriesService.getCategoryByCategoryEng("pizza");
        model.addAttribute("pages", dishService.definePageNumberForCategoryAndStatus(category.getId(), "shown", 20));
        model.addAttribute("dishes", dishService.getDishesByCategoryAndStatusPaging(category.getId(), "shown", page-1, 20));
        return "category";
    }

    @GetMapping("/category/deserts")
    public String deserts(Model model, @RequestParam int page, HttpSession session){
        model.addAttribute("name", "deserts");
        model.addAttribute("user", session.getAttribute("user"));
        model.addAttribute("cartQuantity", session.getAttribute("cartQuantity"));
        model.addAttribute("categories", categoriesService.getAllCategories());
        Category category = categoriesService.getCategoryByCategoryEng("deserts");
        model.addAttribute("pages", dishService.definePageNumberForCategoryAndStatus(category.getId(), "shown", 20));
        model.addAttribute("dishes", dishService.getDishesByCategoryAndStatusPaging(category.getId(), "shown", page-1, 20));
        return "category";
    }

    @GetMapping("/category/beverages")
    public String beverage(Model model, @RequestParam int page, HttpSession session){
        model.addAttribute("name", "beverage");
        model.addAttribute("user", session.getAttribute("user"));
        model.addAttribute("cartQuantity", session.getAttribute("cartQuantity"));
        model.addAttribute("categories", categoriesService.getAllCategories());
        Category category = categoriesService.getCategoryByCategoryEng("beverages");
        model.addAttribute("pages", dishService.definePageNumberForCategoryAndStatus(category.getId(), "shown", 20));
        model.addAttribute("dishes", dishService.getDishesByCategoryAndStatusPaging(category.getId(), "shown", page-1, 20));
        return "category";
    }

    @GetMapping("/category/appetizers")
    public String appetizer(Model model, @RequestParam int page, HttpSession session){
        model.addAttribute("name", "appetizers");
        model.addAttribute("user", session.getAttribute("user"));
        model.addAttribute("cartQuantity", session.getAttribute("cartQuantity"));
        model.addAttribute("categories", categoriesService.getAllCategories());
        Category category = categoriesService.getCategoryByCategoryEng("appetizers");
        model.addAttribute("pages", dishService.definePageNumberForCategoryAndStatus(category.getId(), "shown", 20));
        model.addAttribute("dishes", dishService.getDishesByCategoryAndStatusPaging(category.getId(), "shown", page-1, 20));
        return "category";
    }

}