package spz.dae24.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import spz.dae24.entities.Volume;

import java.util.List;
import java.util.stream.Collectors;

public class VolumeWithSensorsAndProductVolumesDTO {
    @Min(1)
    private long code;
    private int number;
    private String status;
    @Min(1)
    private long packageCode;
    @NotBlank
    private String packageType;
    @NotEmpty
    private List<ProductsVolumeDTO> productsVolume;
    private List<SensorDTO> sensors;

    public VolumeWithSensorsAndProductVolumesDTO() {}

    public VolumeWithSensorsAndProductVolumesDTO(long code, int number, String status, long packageCode, List<ProductsVolumeDTO> productsVolume, List<SensorDTO> sensors, String packageType) {
        this.code = code;
        this.number = number;
        this.status = status;
        this.packageCode = packageCode;
        this.productsVolume = productsVolume;
        this.sensors = sensors;
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

    public List<SensorDTO> getSensors() {
        return sensors;
    }
    public void setSensors(List<SensorDTO> sensors) {
        this.sensors = sensors;
    }

    public static VolumeWithSensorsAndProductVolumesDTO from(Volume volume) {
        return new VolumeWithSensorsAndProductVolumesDTO(
                volume.getCode(),
                volume.getNumber(),
                volume.getStatus().name(),
                volume.getPackage().getCode(),
                ProductsVolumeDTO.from(volume.getProductsVolumes()),
                SensorDTO.from(volume.getSensors()),
                volume.getPackageType().name()
        );
    }

    public static List<VolumeWithSensorsAndProductVolumesDTO> from(List<Volume> volumes) {
        return volumes.stream().map(VolumeWithSensorsAndProductVolumesDTO::from).collect(Collectors.toList());
    }
}
