package spz.dae24.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import spz.dae24.common.enums.Status;
import spz.dae24.entities.Package;
import spz.dae24.entities.VolumeProduct;

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
    //@NotNull
    //@OneToMany
    private Package package;

    //@NotNull
    //@ManyToOne
    private List<VolumeProduct> volumeProducts;

    //@NotNull
    //@ManyToOne
    //private List<Sensor> sensors;

    public Volume() {
    }

    public Volume(int code, int number, Status status) {
        this.code = code;
        this.number = number;
        this.status = status;
        this.volumeProducts = new ArrayList<>();
        this.sensors = new ArrayList<>();
    }

    public int getCode() {
        return code;
    }
    public int getNumber() {
        return number;
    }
    public Status getStatus() {
        return status;
    }
    public Package getPackage() {
        return package;
    }

    public
}
