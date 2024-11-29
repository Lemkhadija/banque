package com.ensas.banque.services;

import com.ensas.banque.entities.User;
import com.ensas.banque.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    private User user;
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    public User findUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Utilisateur introuvable pour l'ID : " + id));
    }
    public void saveUser(User user) {
        userRepository.save(user);
    }

    public List<User> findAllClients() {
        return userRepository.findByRole("CLIENT");  // Supposons qu'on filtre les utilisateurs par rôle
    }
    public User save(User user) { return userRepository.save(user); }
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }
}


