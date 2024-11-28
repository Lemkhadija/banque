package com.ensas.banque.controllers;

import com.ensas.banque.entities.Transaction;
import com.ensas.banque.entities.User;
import com.ensas.banque.services.TransactionService;
import com.ensas.banque.services.TransferService;
import com.ensas.banque.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/client")
public class ClientController {

    @Autowired
    private UserService userService;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private TransferService transferService;

    /**
     * Affiche le tableau de bord du client.
     */
    @GetMapping("/dashboard")
    public String clientDashboard(Model model) {
        Long clientId = 1L; // Exemple d'ID de l'utilisateur connecté. À remplacer par la gestion de session.

        try {
            // Récupérer les informations du client
            User client = userService.findUserById(clientId);
            model.addAttribute("clientName", client.getUsername());
            model.addAttribute("balance", client.getBalance());

            // Récupérer les 5 dernières transactions
            List<Transaction> recentTransactions = transactionService.findRecentTransactions(clientId, 5);
            model.addAttribute("recentTransactions", recentTransactions);

        } catch (Exception e) {
            model.addAttribute("error", "Erreur lors du chargement du tableau de bord : " + e.getMessage());
        }

        return "client-dashboard"; // Vue du tableau de bord
    }

    /**
     * Affiche la page de transfert.
     */
    @GetMapping("/transfer")
    public String transfer(Model model) {
        return "client-transfer"; // Vue pour le formulaire de transfert
    }

    /**
     * Traitement du transfert d'argent.
     */
    @PostMapping("/transfer")
    public String processTransfer(@RequestParam Long recipientId,
                                  @RequestParam double amount,
                                  Model model) {
        Long senderId = 1L; // Exemple d'ID de l'utilisateur connecté. À adapter selon votre gestion d'authentification.

        try {
            // Vérification des paramètres
            if (amount <= 0) {
                throw new IllegalArgumentException("Le montant doit être supérieur à 0.");
            }

            // Appeler le service pour effectuer le transfert
            String message = transferService.transferMoney(senderId, recipientId, amount);

            // Ajouter un message de succès au modèle
            model.addAttribute("success", message);

        } catch (IllegalArgumentException e) {
            // Ajouter un message d'erreur en cas de problème d'entrée utilisateur
            model.addAttribute("error", e.getMessage());
        } catch (Exception e) {
            // Gérer les autres erreurs possibles
            model.addAttribute("error", "Une erreur inattendue s'est produite. Veuillez réessayer.");
        }

        // Retourner à la page de transfert avec les résultats
        return "client-transfer";
    }
    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String token) {
        String jwt = token.substring(7); // Supprimer le préfixe "Bearer "
        //blacklistService.addToBlacklist(jwt); // Ajouter à une liste noire
        return ResponseEntity.ok("Logged out successfully");
    }
}
