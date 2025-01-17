package spz.dae24.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@NamedQueries({
        @NamedQuery(
                name = "getAllClients",
                query = "SELECT new Client(c.username,c.name,c.email) FROM Client c"
        )
})
@DiscriminatorValue("Client")
public class Client extends User {

    @OneToMany(mappedBy = "client")
    private final List<Package> packages = new ArrayList<>();

    public Client() {}

    public Client(String username, String name, String email) {
        super(username, name, email);
    }

    public Client(String username, String name, String email, String password) {
        super(username, name, email,password);
    }

    public List<Package> getPackages() {
        return packages;
    }
    public boolean addPackage(Package pack) {
        return packages.add(pack);
    }
}
