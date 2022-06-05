package com.diploma.pizzeria.controllers;

import com.diploma.pizzeria.entities.User;
import com.diploma.pizzeria.entities.Validation;
import com.diploma.pizzeria.service.UserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
public class UserController {

    private static UserService userService;

    @Autowired
    public void setService(UserService service) {
        userService = service;
    }

    @GetMapping("/users")
    public String users(Model model, HttpSession session, @RequestParam("role") String role){
        User user = (User) session.getAttribute("user");
        if (user == null || !user.getUserRole().contains("admin"))
            return "redirect:/";
        model.addAttribute("user", session.getAttribute("user"));
        model.addAttribute("role", role);
        model.addAttribute("users", userService.getUsersByRole(role));
        return "admin/users";
    }

    @GetMapping("/users/add")
    public String addUser(Model model, HttpSession session, @RequestParam("role") String role){
        User user = (User) session.getAttribute("user");
        if (user == null || !user.getUserRole().contains("admin"))
            return "redirect:/";
        model.addAttribute("user", session.getAttribute("user"));
        model.addAttribute("newUser", new User());
        model.addAttribute("role", role);
        return "admin/add_user";
    }

    @PostMapping("/users/add")
    public String validateUser(Model model, @ModelAttribute("newUser") User user, @RequestParam("role") String role,
                               HttpSession session){
        User loggedUser = (User) session.getAttribute("user");
        if (loggedUser == null || !loggedUser.getUserRole().contains("admin"))
            return "redirect:/";
        boolean fail = false;
        Validation validation = new Validation();
        if (!validation.checkEmail(user.getEmail())){
            model.addAttribute("emailFailed", true);
            fail = true;
        }
        if (!validation.checkName(user.getFullName())){
            model.addAttribute("nameFailed", true);
            fail = true;
        }
        if (!validation.checkPassword(user.getPass())) {
            model.addAttribute("passFailed", true);
            fail = true;
        }
        if (!validation.checkPhone(user.getPhoneNumber())) {
            model.addAttribute("phoneFailed", true);
            fail = true;
        }
        if (fail) {
            model.addAttribute("user", session.getAttribute("user"));
            model.addAttribute("newUser", new User());
            model.addAttribute("role", role);
            return "admin/add_user";
        }
        if (userService.existsWithEmail(user.getEmail())){
            model.addAttribute("messageEmail", "Акаунт з даною поштою вже існує");
            model.addAttribute("emailFailed", true);
            model.addAttribute("user", session.getAttribute("user"));
            model.addAttribute("role", role);
            return "admin/add_user";
        }
        user.setPass(DigestUtils.sha256Hex(user.getPass()));
        user.setUserRole(role);
        userService.saveUser(user);
        return "redirect:/users?page=1&role=" + role;
    }

    @GetMapping("/user/delete")
    public String delete(@RequestParam("id") Long id, @RequestParam("role") String role){
        User user = userService.getUserById(id);
        user.setUserRole("client");
        userService.saveUser(user);
        return "redirect:/users?page=1&role=" + role;
    }
}