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

    // Tableau de bord des clients
    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        List<User> clients = userService.findAllClients();
        model.addAttribute("clients", clients);
        return "banquier-dashboard";
    }

    // Afficher les détails d'un client
    @GetMapping("/client/{id}/details")
    public String viewClientDetails(@PathVariable Long id, Model model) {
        User client = userService.findUserById(id);
        if (client != null) {
            model.addAttribute("client", client);
            return "client-details";
        } else {
            return "redirect:/banquier/dashboard";  // Redirige si le client n'est pas trouvé
        }
    }

    // Formulaire pour ajouter un client
    @GetMapping("/client/add")
    public String addClientForm(Model model) {
        model.addAttribute("client", new User());  // Créer un nouvel utilisateur vide
        return "add-client";  // Affiche le formulaire d'ajout de client
    }

    // Soumettre l'ajout d'un client
    @PostMapping("/client/add")
    public String addClientSubmit(@ModelAttribute User client) {
        if (client.getUsername() != null && !client.getUsername().isEmpty()) {
            userService.saveUser(client);  // Enregistrer le client dans la base de données
        }
        return "redirect:/banquier/dashboard";  // Redirige vers le tableau de bord
    }

    // Formulaire pour modifier un client
    @GetMapping("/client/{id}/edit")
    public String editClientForm(@PathVariable Long id, Model model) {
        User client = userService.findUserById(id);
        if (client != null) {
            model.addAttribute("client", client);
            model.addAttribute("successMessage", null);  // Initialiser message vide
            return "edit-client";  // Affiche le formulaire d'édition
        } else {
            return "redirect:/banquier/dashboard";  // Redirige si le client n'existe pas
        }
    }

    // Soumettre les modifications d'un client (simulé, sans changement dans la base)
    @PostMapping("/client/{id}/edit")
    public String editClientSubmit(@PathVariable Long id, @ModelAttribute User client, Model model) {
        // Simuler un succès sans modifier la base de données
        model.addAttribute("client", client);
        model.addAttribute("successMessage", "Modifications enregistrées avec succès !");

        return "edit-client";  // Redirige vers la page d'édition avec le message de succès
    }
    // Supprimer un client
    @PostMapping("/client/{id}/delete")
    public String deleteClient(@PathVariable Long id) {
        userService.deleteUserById(id);  // Supprimer le client par ID
        return "redirect:/banquier/dashboard";  // Rediriger vers le tableau de bord après suppression
    }
}
