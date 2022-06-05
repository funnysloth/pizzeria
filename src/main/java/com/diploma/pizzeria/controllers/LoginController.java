package com.diploma.pizzeria.controllers;

import com.diploma.pizzeria.entities.User;
import com.diploma.pizzeria.entities.Validation;
import com.diploma.pizzeria.service.UserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;


@Controller
public class LoginController {

    private static UserService userService;

    @Autowired
    public void setService(UserService service) {
        userService = service;
    }


    @GetMapping("/login")
    public String login(Model model, HttpSession session){
        if (session.getAttribute("user") != null)
            return "redirect:/";
        if (session.getAttribute("cart") == null)
            session.setAttribute("cartQuantity", null);
        model.addAttribute("cartQuantity", session.getAttribute("cartQuantity"));
        return "login";
    }

    @PostMapping("/login")
    public String validateLogin(Model model, @RequestParam("email") String email, @RequestParam("passwd") String password,
                                HttpSession session){
        if (session.getAttribute("user") != null)
            return "redirect:/";
        model.addAttribute("email", email);
        model.addAttribute("password", password);
        Validation validate = new Validation();
        if (!validate.checkEmail(email)){
            model.addAttribute("messageEmail", "Некоректна email адреса");
            model.addAttribute("emailFailed", true);
            return "login";
        }
        if(!userService.existsWithEmail(email)){
            model.addAttribute("messageEmail", "Користувача не знайдено");
            model.addAttribute("emailFailed", true);
            return "login";
        }
        if (userService.existsWithEmail(email)){
            User user = userService.getUserByEmail(email);
            if(!DigestUtils.sha256Hex(password).equals(user.getPass())){
                model.addAttribute("messagePassword", "Невіррний пароль!");
                model.addAttribute("passwordFailed", true);
                return "login";
            }
        }
        session.setAttribute("user", userService.getUserByEmail(email));
        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute("user");
        return "redirect:/";
    }
}