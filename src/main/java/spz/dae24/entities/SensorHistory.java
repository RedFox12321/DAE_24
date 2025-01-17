package spz.dae24.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

@Entity
@Table(name = "sensors_history")
@NamedQueries(
        @NamedQuery(
                name = "getAllSensorsHistory",
                query = "SELECT sh FROM SensorHistory sh ORDER BY sh.id, sh.timestamp"
        )
)
public class SensorHistory extends Versionable implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long timestamp;
    @NotBlank
    private String value;
    @ManyToOne
    @NotNull
    private Sensor sensor;

    public SensorHistory() {}

    public SensorHistory(long timestamp, String value, Sensor sensor) {
        this.timestamp = timestamp;
        this.value = value;
        this.sensor = sensor;
    }

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public long getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public @NotBlank String getValue() {
        return value;
    }
    public void setValue(@NotBlank String value) {
        this.value = value;
    }

    public Sensor getSensor() {
        return sensor;
    }
    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }
}
