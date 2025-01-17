package spz.dae24.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import spz.dae24.common.enums.PackageType;
import spz.dae24.common.enums.Status;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@NamedQueries({
        @NamedQuery(
                name = "getAllVolumes",
                query = "SELECT new Volume(v.code, v.number, v.status, v.packageType, v._package) FROM Volume v ORDER BY v.code"
        )
})
@Table(name = "volumes")
public class Volume extends Versionable implements Serializable {
    @Id
    private long code;

    @NotNull
    private int number;

    @NotNull
    private Status status;

    @Enumerated(EnumType.STRING)
    @Column(name = "package_type")
    private PackageType packageType;

    @ManyToOne
    @NotNull
    private Package _package;

    @OneToMany(mappedBy = "volume")
    private final List<ProductsVolume> productsVolumes = new ArrayList<>();

    @OneToMany(mappedBy = "volume")
    private final List<Sensor> sensors = new ArrayList<>();

    public Volume() {}

    public Volume(long code, int number, Status status, PackageType packageType, Package _package) {
        this.code = code;
        this.number = number;
        this.status = status;
        this.packageType = packageType;
        this._package = _package;
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
    public void setPackage(Package _package) {
        this._package = _package;
    }

    public PackageType getPackageType() {
        return packageType;
    }
    public void setPackageType(PackageType packageType) {
        this.packageType = packageType;
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

    public List<ProductsVolume> getProductsVolumes() {return productsVolumes;}
    public boolean addProductsVolume(ProductsVolume volumeProduct) {return productsVolumes.add(volumeProduct);}
    public boolean removeProductsVolume(ProductsVolume volumeProduct) {return productsVolumes.remove(volumeProduct);}
}
