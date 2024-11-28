package com.ensas.banque.controllers;

import com.ensas.banque.entities.User;
import com.ensas.banque.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public String processLogin(@RequestParam String username, @RequestParam String password, Model model) {
        User user = userService.findByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            if ("CLIENT".equals(user.getRole())) {
                return "redirect:/client/dashboard";
            } else {
                return "redirect:/banquier-dashboard";
            }
        }
        model.addAttribute("error", "Identifiants incorrects");
        return "login";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping("/register")
    public String processRegister(@RequestParam String username, @RequestParam String password, @RequestParam String role, Model model) {
        if (userService.findByUsername(username) != null) {
            model.addAttribute("error", "Le nom d'utilisateur est déjà pris.");
            return "register";
        }

        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(password); // Pensez à hacher le mot de passe avant de le stocker
        newUser.setRole(role);
        newUser.setBalance(0.0);

        userService.saveUser(newUser);

        model.addAttribute("success", "Votre compte a été créé avec succès.");
        return "login";
    }

    @GetMapping("/logout")
    public String logout() {
        // La déconnexion est gérée automatiquement par Spring Security

        return "redirect:/login"; // Redirige vers la page de connexion
    }

}
