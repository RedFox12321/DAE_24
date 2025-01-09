package spz.dae24.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;

@Entity
public class User {
    @Id
    private int id;

    @NotBlank
    private String username;

    @NotBlank
    private String name;

    @NotBlank
    private String email;

    public User() {
    }

    public User(int id, String username, String name, String email) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.email = email;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
}
