package com.diploma.pizzeria.controllers;

import com.diploma.pizzeria.entities.Dish;
import com.diploma.pizzeria.entities.User;
import com.diploma.pizzeria.entities.Validation;
import com.diploma.pizzeria.service.CategoriesService;
import com.diploma.pizzeria.service.DishService;
import com.diploma.pizzeria.utils.S3Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class DishController {

    private static DishService dishService;
    private static CategoriesService categoriesService;
    private static final String UPLOAD_URL = "https://pizzeria-dish-images.s3.eu-central-1.amazonaws.com/";

    @Autowired
    public void setService(DishService service) {
        dishService = service;
    }

    @Autowired
    public void setService(CategoriesService service) {
        categoriesService = service;
    }

    @GetMapping("/products")
    public String products(Model model, HttpSession session, @RequestParam int page){
        User user = (User) session.getAttribute("user");
        if (user == null || !user.getUserRole().contains("admin"))
            return "redirect:/";
        model.addAttribute("user", session.getAttribute("user"));
        model.addAttribute("pages", dishService.definePageNumber(20));
        model.addAttribute("dishes", dishService.getAllDishes(page-1, 20));
        model.addAttribute("categories", categoriesService.getAllCategories());
        model.addAttribute("categoryService", categoriesService);
        model.addAttribute("filter", false);
        return "admin/products";
    }

    @GetMapping("/products/filter")
    public String filter(Model model, HttpSession session, @RequestParam int page, @RequestParam String category,
                         @RequestParam String status){
        User user = (User) session.getAttribute("user");
        if (user == null || !user.getUserRole().contains("admin"))
            return "redirect:/";
        if (category.equals("none") && status.equals("none"))
            return "redirect:/products?page=1";
        model.addAttribute("user", session.getAttribute("user"));
        if (!category.equals("none") && status.equals("none")){
            model.addAttribute("pages", dishService.definePageNumberForCategory(Integer.parseInt(category), 20));
            model.addAttribute("dishes", dishService.getDishesByCategoryPaging(Integer.parseInt(category),page-1, 20));
        }
        else if (category.equals("none")){
            model.addAttribute("pages", dishService.definePageNumberForStatus(status, 20));
            model.addAttribute("dishes", dishService.getDishesByStatusPaging(status,page-1, 20));
        }
        else {
            model.addAttribute("pages", dishService.definePageNumberForCategoryAndStatus(Integer.parseInt(category), status, 20));
            model.addAttribute("dishes", dishService.getDishesByCategoryAndStatusPaging(Integer.parseInt(category), status, page-1, 20));
        }
        model.addAttribute("category", category);
        model.addAttribute("status", status);
        model.addAttribute("categories", categoriesService.getAllCategories());
        model.addAttribute("categoryService", categoriesService);
        model.addAttribute("filter", true);
        return "admin/products";
    }

    @GetMapping("/products/search")
    public String search(Model model, HttpSession session, @RequestParam int page, @RequestParam String search){
        User user = (User) session.getAttribute("user");
        if (user == null || !user.getUserRole().contains("admin"))
            return "redirect:/";
        if (search.equals(""))
            return "redirect:/products?page=1";
        model.addAttribute("user", session.getAttribute("user"));
        model.addAttribute("dishes", dishService.getDishesBySearchNamePaging(search, page-1, 20));
        model.addAttribute("search", search);
        model.addAttribute("categories", categoriesService.getAllCategories());
        model.addAttribute("categoryService", categoriesService);
        model.addAttribute("filter", true);
        return "admin/products";
    }

    @GetMapping("/products/edit")
    public String edit(Model model, HttpSession session, @RequestParam Long id){
        User user = (User) session.getAttribute("user");
        if (user == null || !user.getUserRole().contains("admin"))
            return "redirect:/";
        model.addAttribute("user", session.getAttribute("user"));
        model.addAttribute("categories", categoriesService.getAllCategories());
        model.addAttribute("dish", dishService.getById(id));
        return "admin/edit_product";
    }

    @PostMapping("/products/edit")
    public String editProduct(@RequestParam("file") MultipartFile file, @RequestParam("description") String description,
                              @RequestParam("action") String action, @RequestParam("id") Long id, @RequestParam("category") int category,
                              @RequestParam("dishName") String dishName, @RequestParam("weight") int weight,
                              @RequestParam("price") int price, HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null || !user.getUserRole().contains("admin"))
            return "redirect:/";
        Dish dish = dishService.getById(id);
        switch (action) {
            case "show":
                dish.setStatus("shown");
                dishService.saveDish(dish);
                break;
            case "hide":
                dish.setStatus("hidden");
                dishService.saveDish(dish);
                break;
            default:
                boolean fail = false;
                Validation validation = new Validation();
                if (!validation.checkTextInput(dishName)){
                    model.addAttribute("dishNameFailed", true);
                    fail = true;
                }
                if (!validation.checkTextInput(description)){
                    model.addAttribute("descriptionFailed", true);
                    fail = true;
                }
                if (fail) {
                    model.addAttribute("user", session.getAttribute("user"));
                    model.addAttribute("categories", categoriesService.getAllCategories());
                    model.addAttribute("dish", dishService.getById(id));
                    return "admin/edit_product";
                }
                if (!file.isEmpty()) {
                    String fileName = file.getOriginalFilename();
                    try {
                        S3Util.uploadFile(fileName, file.getInputStream());
                        dish.setPicturePath(UPLOAD_URL + fileName);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                dish.setDishName(dishName);
                dish.setWeight(weight);
                dish.setPrice(price);
                dish.setDescription(description);
                dish.setCategoryId(category);
                dishService.saveDish(dish);
                break;
        }
        return "redirect:/products?page=1";
    }

    @GetMapping("/products/add")
    public String add(Model model, HttpSession session){
        User user = (User) session.getAttribute("user");
        if (user == null || !user.getUserRole().contains("admin"))
            return "redirect:/";
        model.addAttribute("user", session.getAttribute("user"));
        model.addAttribute("categories", categoriesService.getAllCategories());
        model.addAttribute("dish", new Dish());
        return "admin/add_product";
    }

    @PostMapping("/products/add")
    public String addProduct(@ModelAttribute Dish dish, @RequestParam("file") MultipartFile file, @RequestParam("category") int category,
                             HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null || !user.getUserRole().contains("admin"))
            return "redirect:/";
        boolean fail = false;
        Validation validation = new Validation();
        if (!validation.checkTextInput(dish.getDishName())){
            model.addAttribute("dishNameFailed", true);
            fail = true;
        }
        if (!validation.checkTextInput(dish.getDescription())){
            model.addAttribute("descriptionFailed", true);
            fail = true;
        }
        if (fail) {
            model.addAttribute("user", session.getAttribute("user"));
            model.addAttribute("categories", categoriesService.getAllCategories());
            model.addAttribute("dish", new Dish());
            return "admin/add_product";
        }
        if (!file.isEmpty()){
            String fileName = file.getOriginalFilename();
            try {
                S3Util.uploadFile(fileName, file.getInputStream());
                dish.setPicturePath(UPLOAD_URL + fileName);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        dish.setStatus("shown");
        dish.setCategoryId(category);
        dishService.saveDish(dish);
        return "redirect:/products?page=1";
    }
}