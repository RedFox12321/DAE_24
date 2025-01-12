package spz.dae24.entities;

import jakarta.persistence.Entity;

@Entity
public class Client extends User {
    public Client() {
    }

    public Client(int id, String username, String name, String email) {
        super(id, username, name, email);
    }
}
