package spz.dae24.dtos;

import spz.dae24.entities.Volume;

import java.util.List;
import java.util.stream.Collectors;

public class VolumeWithProductVolumesDTO {
    private long code;
    private int number;
    private String status;
    private long packageCode;
    private String packageType;
    private List<ProductsVolumeDTO> productsVolume;

    public VolumeWithProductVolumesDTO() {}

    public VolumeWithProductVolumesDTO(long code, int number, String status, long packageCode, List<ProductsVolumeDTO> productsVolume, String packageType) {
        this.code = code;
        this.number = number;
        this.status = status;
        this.packageCode = packageCode;
        this.productsVolume = productsVolume;
        this.packageType = packageType;
    }

    public long getCode() {
        return code;
    }
    public void setCode(long code) {
        this.code = code;
    }

    public int getNumber() {
        return number;
    }
    public void setNumber(int number) {
        this.number = number;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    public long getPackageCode() {
        return packageCode;
    }
    public void setPackageCode(long packageCode) {
        this.packageCode = packageCode;
    }

    public List<ProductsVolumeDTO> getProductsVolume() {
        return productsVolume;
    }
    public void setProductsVolume(List<ProductsVolumeDTO> productsVolume) {
        this.productsVolume = productsVolume;
    }

    public String getPackageType() {
        return packageType;
    }
    public void setPackageType(String packageType) {
        this.packageType = packageType;
    }

    public static VolumeWithProductVolumesDTO from(Volume volume) {
        return new VolumeWithProductVolumesDTO(
                volume.getCode(),
                volume.getNumber(),
                volume.getStatus().name(),
                volume.getPackage().getCode(),
                ProductsVolumeDTO.from(volume.getProductsVolumes()),
                volume.getPackageType().name()
        );
    }

    public static List<VolumeWithProductVolumesDTO> from(List<Volume> volumes) {
        return volumes.stream().map(VolumeWithProductVolumesDTO::from).collect(Collectors.toList());
    }
}
