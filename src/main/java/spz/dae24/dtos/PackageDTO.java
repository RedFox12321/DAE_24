package spz.dae24.dtos;

import java.util.List;
import java.util.stream.Collectors;

import spz.dae24.entities.Package;

public class PackageDTO {
    private long code;
    private String status;
    private List<VolumeDTO> volumes;
    private String clientUsername;

    public PackageDTO() {
    }

    public PackageDTO(long code, String status, List<VolumeDTO> volumes, String clientUsername) {
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

    public List<VolumeDTO> getVolumes() {
        return volumes;
    }
    public void setVolumes(List<VolumeDTO> volumes) {
        this.volumes = volumes;
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
                VolumeDTO.from(_package.getVolumes()),
                _package.getClient().getUsername()
        );
    }

    public static List<PackageDTO> from(List<Package> packages) {
        return packages.stream().map(PackageDTO::from).collect(Collectors.toList());
    }
}
