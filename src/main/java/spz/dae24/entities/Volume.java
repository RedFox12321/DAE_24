package spz.dae24.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import spz.dae24.common.enums.Status;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Volume {
    @Id
    private int code;

    @NotNull
    private int number;

    @NotNull
    private Status status;

    @NotNull
    @ManyToOne
    private Package _package;

    @OneToMany
    private List<VolumeProduct> volumeProducts = new ArrayList<>();

    @OneToMany(mappedBy = "volume")
    private final List<Sensor> sensors = new ArrayList<>();

    public Volume() {
    }

    public Volume(int code, int number, Status status) {
        this.code = code;
        this.number = number;
        this.status = status;
    }

    public int getCode() {
        return code;
    }
    public void setCode(int code) {
        this.code = code;
    }

    public int getNumber() {
        return number;
    }
    public void setNumber(@NotNull int number) {
        this.number = number;
    }

    public Status getStatus() {
        return status;
    }
    public void setStatus(@NotNull Status status) {
        this.status = status;
    }

    public Package getPackage() {
        return _package;
    }
    public void set_package(Package _package) {
        this._package = _package;
    }

    public List<Sensor> getSensors() {
        return sensors;
    }
    public boolean addSensor(Sensor sensor) {
        return sensors.add(sensor);
    }
    public boolean removeSensor(Sensor sensor) {
        return sensors.remove(sensor);
    }

    public List<VolumeProduct> getVolumeProducts() {return volumeProducts;}
    public boolean addVolumeProduct(VolumeProduct volumeProduct) {return volumeProducts.add(volumeProduct);}
    public boolean removeVolumeProduct(VolumeProduct volumeProduct) {return volumeProducts.remove(volumeProduct);}
}
