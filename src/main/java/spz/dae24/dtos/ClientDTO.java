package spz.dae24.dtos;

import jakarta.validation.constraints.NotBlank;
import spz.dae24.entities.Client;
import spz.dae24.entities.Package;
import spz.dae24.entities.Sensor;
import spz.dae24.entities.Volume;

import java.util.List;
import java.util.stream.Collectors;

public class ClientDTO {
    private int id;
    private String username;
    private String name;
    private String email;
    private List<Package> packages;

    public ClientDTO() {}

    public ClientDTO(List<Package> packages, String email, String name, String username, int id) {
        this.packages = packages;
        this.email = email;
        this.name = name;
        this.username = username;
        this.id = id;
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

    public List<Package> getPackages() {
        return packages;
    }

    public void setPackages(List<Package> packages) {
        this.packages = packages;
    }

    public static ClientDTO from(Client client) {
        return new ClientDTO(
                client.getPackages(),
                client.getEmail(),
                client.getName(),
                client.getUsername(),
                client.getId());
    }

    public static List<ClientDTO> from(List<Client> clients) {
        return clients.stream().map(ClientDTO::from).collect(Collectors.toList());
    }
}
