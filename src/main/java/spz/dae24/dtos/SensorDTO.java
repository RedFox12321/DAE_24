package spz.dae24.dtos;

import spz.dae24.entities.Sensor;
import spz.dae24.entities.Volume;

import java.util.List;
import java.util.stream.Collectors;

public class SensorDTO {
    private long id;
    private boolean active;
    private String type;
    private long volumeCode;
    private List<SensorHistoryDTO> history;

    public SensorDTO() {}

    public SensorDTO(long id, boolean active, String type, long volumeCode, List<SensorHistoryDTO> history) {
        this.id = id;
        this.active = active;
        this.type = type;
        this.volumeCode = volumeCode;
        this.history = history;
    }

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public boolean isActive() {
        return active;
    }
    public void setActive(boolean active) {
        this.active = active;
    }

    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }

    public long getVolumeCode() {
        return volumeCode;
    }
    public void setVolumeCode(long volumeCode) {
        this.volumeCode = volumeCode;
    }

    public List<SensorHistoryDTO> getHistory() {
        return history;
    }
    public void setHistory(List<SensorHistoryDTO> history) {
        this.history = history;
    }

    public static SensorDTO from(Sensor sensor) {
        return new SensorDTO(
                sensor.getId(),
                sensor.isActive(),
                sensor.getType().getName(),
                sensor.getVolume().getCode(),
                SensorHistoryDTO.from(sensor.getHistory())
        );
    }

    public static List<SensorDTO> from(List<Sensor> sensors) {
        return sensors.stream().map(SensorDTO::from).collect(Collectors.toList());
    }
}
