package com.example.IdentityService.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {

    @GetMapping("/")
    public String redirectToHome() {
        return "redirect:/home";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/home")
    public String home() {
        return "home";
    }

    @PostMapping("/admin")
    public ModelAndView adminPost(@RequestParam("token") String token, HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("admin");
        // Lưu token vào session
        HttpSession session = request.getSession();
        session.setAttribute("token", token);
        return modelAndView;
    }

    @GetMapping("/admin")
    public String admin() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated()) {
            return "admin";
        }
        return "redirect:/login";
    }

    @PostMapping("/staff")
    public ModelAndView staffPost(@RequestParam("token") String token, HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("staff");
        HttpSession session = request.getSession();
        session.setAttribute("token", token);
        return modelAndView;
    }

    @GetMapping("/staff")
    public String staff() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated()) {
            return "staff";
        }
        return "redirect:/login";
    }
}
