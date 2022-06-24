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

import javax.servlet.http.HttpSession;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
public class RegistrationController {

    private static UserService userService;

    @Autowired
    public void setService(UserService service) {
        userService = service;
    }

    @GetMapping("/registration")
    public String registration(Model model, HttpSession session){
        if (session.getAttribute("user") != null)
            return "redirect:/";
        if (session.getAttribute("cart") == null)
            session.setAttribute("cartQuantity", null);
        model.addAttribute("registration", new User());
        return "registration";
    }

    @PostMapping("/registration")
    public String validateRegistration(Model model, @ModelAttribute("registration") User user, HttpSession session){
        if (session.getAttribute("user") != null)
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
        if (!validation.checkPassword(user.getPass())){
            model.addAttribute("passFailed", true);
            fail = true;
        }
        if (fail)
            return "registration";
        if (userService.existsWithEmail(user.getEmail())){
            model.addAttribute("messageEmail", "Акаунт з даною поштою вже існує");
            model.addAttribute("emailFailed", true);
            return "registration";
        }
        user.setPass(DigestUtils.sha256Hex(user.getPass()));
        user.setUserRole("client");
        userService.saveUser(user);
        session.setAttribute("user", userService.getUserByEmail(user.getEmail()));
        session.setMaxInactiveInterval(-1);
        return "redirect:/";
    }
}