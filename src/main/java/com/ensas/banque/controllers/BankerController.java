package com.ensas.banque.controllers;

import com.ensas.banque.entities.User;
import com.ensas.banque.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/banquier")
public class BankerController {

    @Autowired
    private UserService userService;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        List<User> clients = userService.findAllClients();
        model.addAttribute("clients", clients);
        return "banquier-dashboard";
    }

    @GetMapping("/client/details")
    public String viewClientDetails(@PathVariable Long id, Model model) {
        User client = userService.findUserById(id);
        model.addAttribute("client", client);
        return "client-details";
    }

    @GetMapping("/client/edit")
    public String editClientForm(@PathVariable Long id, Model model) {
        User client = userService.findUserById(id);
        model.addAttribute("client", client);
        return "edit-client";
    }

    @PostMapping("/client/edit")
    public String editClientSubmit(@PathVariable Long id, @ModelAttribute User client) {
        client.setId(id);
        userService.saveUser(client);
        return "redirect:/banquier/dashboard";
    }

    @PostMapping("/client/delete")
    public String deleteClient(@PathVariable Long id) {
        userService.deleteUserById(id);
        return "redirect:/banquier/dashboard";
    }
}
