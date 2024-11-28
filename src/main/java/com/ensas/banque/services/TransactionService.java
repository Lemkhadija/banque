package com.ensas.banque.services;

import com.ensas.banque.entities.Transaction;
import com.ensas.banque.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    // Enregistrer une transaction
    public void saveTransaction(Transaction transaction) {
        transactionRepository.save(transaction);
    }

    // Récupérer les transactions récentes pour un utilisateur donné
    public List<Transaction> findRecentTransactions(Long userId, int limit) {
        // Crée un objet Pageable avec la limite spécifiée
        Pageable pageable = PageRequest.of(0, limit);
        return transactionRepository.findRecentTransactionsByUserId(userId, pageable);
    }
}
