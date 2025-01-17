package spz.dae24.dtos;

import spz.dae24.entities.User;

import java.util.List;
import java.util.stream.Collectors;

public class UserDTO {
    private String username;
    private String name;
    private String email;
    private String type;

    public UserDTO() {}

    public UserDTO(String email, String name, String username) {
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public static UserDTO from(User user) {
        return new UserDTO(
                user.getEmail(),
                user.getName(),
                user.getUsername()
        );
    }

    public static List<UserDTO> from(List<User> users) {
        return users.stream().map(UserDTO::from).collect(Collectors.toList());
    }
}
