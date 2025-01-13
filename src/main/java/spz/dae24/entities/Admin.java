package spz.dae24.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("Admin")
public class Admin extends User {

    public Admin() {}

    public Admin(long id, String username, String name, String email, String password) {
        super(id, username, name, email,password);
    }
}
