package spz.dae24.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import spz.dae24.common.enums.SensorType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Entity
@NamedQueries({
        @NamedQuery(
                name = "getAllSensors",
                query = "SELECT new Sensor(s.id, s.active, s.type, s.volume) FROM Sensor s ORDER BY s.type"
        )
})
@Table(name = "sensors")
public class Sensor implements Serializable {
    @Id
    private long id;
    private boolean active;

    @Enumerated(EnumType.STRING)
    private SensorType type;

    @ManyToOne
    @NotNull
    private Volume volume;

    @OneToMany(mappedBy = "sensor")
    private final List<SensorHistory> history = new ArrayList<>();

    public Sensor() {}

    public Sensor(long id, boolean active, SensorType type, Volume volume) {
        this.id = id;
        this.active = active;
        this.type = type;
        this.volume = volume;
    }

    public Sensor(long id, boolean active, SensorType type) {
        this.id = id;
        this.active = active;
        this.type = type;
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

    public SensorType getType() {
        return type;
    }
    public void setType(SensorType type) {
        this.type = type;
    }

    public Volume getVolume() {
        return volume;
    }
    public void setVolume(Volume volume) {
        this.volume = volume;
    }

    public List<SensorHistory> getHistory() {
        return history;
    }
    public boolean addHistory(SensorHistory sensorHistory) {
        return history.add(sensorHistory);
    }
    public boolean removeHistory(SensorHistory sensorHistory) {
        return history.remove(sensorHistory);
    }
}
