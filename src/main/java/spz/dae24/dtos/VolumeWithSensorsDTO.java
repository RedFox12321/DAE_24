package spz.dae24.dtos;

import spz.dae24.entities.Volume;

import java.util.List;
import java.util.stream.Collectors;

public class VolumeWithSensorsDTO {
    private long code;
    private int number;
    private String status;
    private long packageCode;
    private String packageType;
    private List<SensorDTO> sensors;

    public VolumeWithSensorsDTO() {}

    public VolumeWithSensorsDTO(long code, int number, String status, long packageCode, List<SensorDTO> sensors, String packageType) {
        this.code = code;
        this.number = number;
        this.status = status;
        this.packageCode = packageCode;
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

    public static VolumeWithSensorsDTO from(Volume volume) {
        return new VolumeWithSensorsDTO(
                volume.getCode(),
                volume.getNumber(),
                volume.getStatus().name(),
                volume.getPackage().getCode(),
                SensorDTO.from(volume.getSensors()),
                volume.getPackageType().name()
        );
    }

    public static List<VolumeWithSensorsDTO> from(List<Volume> volumes) {
        return volumes.stream().map(VolumeWithSensorsDTO::from).collect(Collectors.toList());
    }
}
