package spz.dae24.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.io.Serializable;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(
        name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"username", "email"})
        }
)
@DiscriminatorColumn(name = "Type", discriminatorType = DiscriminatorType.STRING)
public class User extends Versionable implements Serializable {
    @Id
    @NotBlank
    private String username;

    @NotBlank
    private String name;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String password;

    public User() {}

    public User(String username, String name, String email, String password) {
        this.username = username;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public User(String username, String name, String email) {
        this.username = username;
        this.name = name;
        this.email = email;
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

    public @NotBlank String getPassword() {
        return password;
    }

    public void setPassword(@NotBlank String password) {
        this.password = password;
    }
}

