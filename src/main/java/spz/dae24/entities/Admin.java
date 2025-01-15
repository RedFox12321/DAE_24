package spz.dae24.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("Admin")
public class Admin extends User {

    public Admin() {}

    public Admin(String username, String name, String email, String password) {
        super(username, name, email,password);
    }
}
