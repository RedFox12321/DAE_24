package spz.dae24.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import spz.dae24.entities.Package;

import java.util.List;
import java.util.stream.Collectors;

public class PackageWithAllDTO {
    @Min(1)
    private long code;
    private String status;
    @NotBlank
    private String clientUsername;
    @NotEmpty
    @NotNull
    private List<VolumeWithSensorsAndProductVolumesDTO> volumes;

    public PackageWithAllDTO() {
    }

    public PackageWithAllDTO(long code, String status, List<VolumeWithSensorsAndProductVolumesDTO> volumes, String clientUsername) {
        this.code = code;
        this.status = status;
        this.volumes = volumes;
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

    public List<VolumeWithSensorsAndProductVolumesDTO> getVolumes() {
        return volumes;
    }
    public void setVolumes(List<VolumeWithSensorsAndProductVolumesDTO> volumes) {
        this.volumes = volumes;
    }

    public String getClientUsername() {
        return clientUsername;
    }
    public void setClientUsername(String clientUsername) {
        this.clientUsername = clientUsername;
    }

    public static PackageWithAllDTO from(Package _package) {
        return new PackageWithAllDTO(
                _package.getCode(),
                _package.getStatus().name(),
                VolumeWithSensorsAndProductVolumesDTO.from(_package.getVolumes()),
                _package.getClient().getUsername()
        );
    }

    public static List<PackageWithAllDTO> from(List<Package> packages) {
        return packages.stream().map(PackageWithAllDTO::from).collect(Collectors.toList());
    }
}
