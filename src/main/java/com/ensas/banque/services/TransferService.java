package com.ensas.banque.services;

import com.ensas.banque.entities.User;
import com.ensas.banque.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TransferService {

    @Autowired
    private UserRepository userRepository;

    // Vérifier si le destinataire existe
    public boolean verifyRecipient(Long recipientId) {
        Optional<User> recipient = userRepository.findById(recipientId);
        return recipient.isPresent(); // Retourne true si le destinataire existe
    }

    // Vérifier si l'utilisateur a suffisamment de fonds pour effectuer un transfert
    public boolean hasSufficientBalance(Long senderId, double amount) {
        Optional<User> sender = userRepository.findById(senderId);
        if (sender.isPresent()) {
            return sender.get().getBalance() >= amount;
        }
        return false;  // Retourne false si l'utilisateur n'existe pas
    }

    // Effectuer le transfert si toutes les conditions sont remplies
    public String transferMoney(Long senderId, Long recipientId, double amount) {
        // Vérifier si le destinataire existe
        if (!verifyRecipient(recipientId)) {
            return "Recipient not found"; // Erreur : destinataire non trouvé
        }

        // Vérifier si l'utilisateur a suffisamment de fonds
        if (!hasSufficientBalance(senderId, amount)) {
            return "Insufficient balance"; // Erreur : solde insuffisant
        }

        // Si tout est ok, procéder au transfert
        User sender = userRepository.findById(senderId).get();
        User recipient = userRepository.findById(recipientId).get();

        // Effectuer la transaction
        sender.setBalance(sender.getBalance() - amount);
        recipient.setBalance(recipient.getBalance() + amount);

        // Sauvegarder les modifications dans la base de données
        userRepository.save(sender);
        userRepository.save(recipient);

        return "Transfer successful"; // Transfert réussi
    }
}
