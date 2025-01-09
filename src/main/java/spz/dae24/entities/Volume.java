package spz.dae24.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import spz.dae24.common.enums.Status;

import java.util.ArrayList;
import java.util.List;

@Entity
@NamedQueries({
        @NamedQuery(
                name = "getAllVolumes",
                query = "SELECT new Volume(v.code, v.number, v.status) FROM Volume v ORDER BY v.code"
        )
})
public class Volume {
    @Id
    private long code;

    @NotNull
    private int number;

    @NotNull
    private Status status;

    @NotNull
    @ManyToOne
    private Package _package;

    @OneToMany(mappedBy = "volume")
    private final List<ProductsVolume> volumeProducts = new ArrayList<>();

    @OneToMany(mappedBy = "volume")
    private final List<Sensor> sensors = new ArrayList<>();

    public Volume() {
    }

    public Volume(long code, int number, Status status) {
        this.code = code;
        this.number = number;
        this.status = status;
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

    public List<ProductsVolume> getVolumeProducts() {return volumeProducts;}
    public boolean addVolumeProduct(ProductsVolume volumeProduct) {return volumeProducts.add(volumeProduct);}
    public boolean removeVolumeProduct(ProductsVolume volumeProduct) {return volumeProducts.remove(volumeProduct);}
}
