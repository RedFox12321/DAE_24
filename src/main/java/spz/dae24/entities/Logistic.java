package spz.dae24.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("Logistic")
public class Logistic extends User {

    public Logistic() {}

    public Logistic(String username, String name, String email, String password) {
        super(username, name, email, password);
    }
}
