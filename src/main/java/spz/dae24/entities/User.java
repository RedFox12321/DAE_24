package spz.dae24.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class User {
    @Id
    private int id;

    private String username;
    private String name;
    private String email;
}
