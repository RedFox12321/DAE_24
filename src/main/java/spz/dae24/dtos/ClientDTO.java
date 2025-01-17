package spz.dae24.dtos;

import jakarta.validation.constraints.NotBlank;
import spz.dae24.entities.Client;

import java.util.List;
import java.util.stream.Collectors;

public class ClientDTO {
    @NotBlank
    private String username;
    @NotBlank
    private String name;
    @NotBlank
    private String email;

    public ClientDTO() {}

    public ClientDTO(String email, String name, String username) {
        this.email = email;
        this.name = name;
        this.username = username;
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

    public static ClientDTO from(Client client) {
        return new ClientDTO(
                client.getEmail(),
                client.getName(),
                client.getUsername()
        );
    }

    public static List<ClientDTO> from(List<Client> clients) {
        return clients.stream().map(ClientDTO::from).collect(Collectors.toList());
    }
}
