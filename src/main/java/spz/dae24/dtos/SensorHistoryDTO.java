package spz.dae24.dtos;

import spz.dae24.entities.SensorHistory;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class SensorHistoryDTO {
    private long sensorId;
    private String sensorType;
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
}
