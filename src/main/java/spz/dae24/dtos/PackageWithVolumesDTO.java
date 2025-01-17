package spz.dae24.dtos;

import spz.dae24.entities.Package;

import java.util.List;
import java.util.stream.Collectors;

public class PackageWithVolumesDTO {
    private long code;
    private String status;
    private List<VolumeDTO> volumes;
    private String clientUsername;

    public PackageWithVolumesDTO() {
    }

    public PackageWithVolumesDTO(long code, String status, List<VolumeDTO> volumes, String clientUsername) {
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

    public static PackageWithVolumesDTO from(Package _package) {
        return new PackageWithVolumesDTO(
                _package.getCode(),
                _package.getStatus().name(),
                VolumeDTO.from(_package.getVolumes()),
                _package.getClient().getUsername()
        );
    }

    public static List<PackageWithVolumesDTO> from(List<Package> packages) {
        return packages.stream().map(PackageWithVolumesDTO::from).collect(Collectors.toList());
    }
}
