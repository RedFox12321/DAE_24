package spz.dae24.dtos;

import spz.dae24.entities.Client;

import java.util.List;
import java.util.stream.Collectors;

public class ClientWithPackagesDTO {
    private String username;
    private String name;
    private String email;
    private List<PackageDTO> packages;

    public ClientWithPackagesDTO() {}

    public ClientWithPackagesDTO(List<PackageDTO> packages, String email, String name, String username) {
        this.packages = packages;
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

    public List<PackageDTO> getPackages() {
        return packages;
    }

    public void setPackages(List<PackageDTO> packages) {
        this.packages = packages;
    }

    public static ClientWithPackagesDTO from(Client client) {
        return new ClientWithPackagesDTO(
                PackageDTO.from(client.getPackages()),
                client.getEmail(),
                client.getName(),
                client.getUsername()
        );
    }

    public static List<ClientWithPackagesDTO> from(List<Client> clients) {
        return clients.stream().map(ClientWithPackagesDTO::from).collect(Collectors.toList());
    }
}
