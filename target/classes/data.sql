-- Insertion des utilisateurs (Clients et Banquiers)
INSERT INTO user (username, password, role, balance) VALUES
                                                         ('client1', 'password123', 'CLIENT', 1000.0),
                                                         ('client2', 'password456', 'CLIENT', 1500.0),
                                                         ('banquier1', 'banquierpass', 'BANQUIER', 0.0);

-- Vous pouvez ajouter d'autres clients et banquiers ici si nécessaire.
