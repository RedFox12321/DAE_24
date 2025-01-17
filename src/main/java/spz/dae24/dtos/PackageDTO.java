package spz.dae24.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import spz.dae24.entities.Package;

import java.util.List;
import java.util.stream.Collectors;

public class PackageDTO {
    @Min(1)
    private long code;
    @NotBlank
    private String status;
    @NotBlank
    private String clientUsername;

    public PackageDTO() {
    }

    public PackageDTO(long code, String status, String clientUsername) {
        this.code = code;
        this.status = status;
        this.clientUsername = clientUsername;
    }

    public long getCode() {
        return code;
    }
    public void setCode(long code) {
        this.code = code;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    public String getClientUsername() {
        return clientUsername;
    }
    public void setClientUsername(String clientUsername) {
        this.clientUsername = clientUsername;
    }

    public static PackageDTO from(Package _package) {
        return new PackageDTO(
                _package.getCode(),
                _package.getStatus().name(),
                _package.getClient().getUsername()
        );
    }

    public static List<PackageDTO> from(List<Package> packages) {
        return packages.stream().map(PackageDTO::from).collect(Collectors.toList());
    }
}
