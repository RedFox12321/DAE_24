package spz.dae24.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import spz.dae24.entities.SensorHistory;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class SensorHistoryDTO {
    @Min(1)
    private long sensorId;
    private String sensorType;
    @NotBlank
    private String value;
    private Date datetime;

    public SensorHistoryDTO(){}

    public SensorHistoryDTO(long sensorId, String sensorType, String value, Date datetime) {
        this.sensorId = sensorId;
        this.sensorType = sensorType;
        this.value = value;
        this.datetime = datetime;
    }

    public long getSensorId() {
        return sensorId;
    }
    public void setSensorId(long sensorId) {
        this.sensorId = sensorId;
    }

    public String getSensorType() {
        return sensorType;
    }
    public void setSensorType(String sensorType) {
        this.sensorType = sensorType;
    }

    public String getValue() {
        return value;
    }
    public void setValue(String value) {
        this.value = value;
    }

    public Date getDatetime() {
        return datetime;
    }
    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    public static SensorHistoryDTO from(SensorHistory sensorHistory) {
        return new SensorHistoryDTO(
                sensorHistory.getSensor().getId(),
                sensorHistory.getSensor().getType().getName(),
                sensorHistory.getValue(),
                new Date(sensorHistory.getTimestamp())
        );
    }

    public static List<SensorHistoryDTO> from(List<SensorHistory> sensors) {
        return sensors.stream().map(SensorHistoryDTO::from).collect(Collectors.toList());
    }

    public static List<SensorHistoryDTO> fromLast(List<SensorHistory> sensors) {
        if (sensors == null || sensors.isEmpty()) {
            return Collections.emptyList(); 
        }
        SensorHistory lastHistory = sensors.get(sensors.size() - 1); 
        return Collections.singletonList(from(lastHistory)); 
    }
}
