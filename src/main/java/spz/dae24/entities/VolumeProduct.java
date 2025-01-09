package spz.dae24.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "products_volume")
public class VolumeProduct {
    @Id
    private int id;

    @NotNull
    private int quantity;

    @NotNull
    @ManyToOne
    private Volume volume;

    // TODO: Add Product after someone (?) implements it

    public VolumeProduct() {
    }

    public VolumeProduct(int id, int quantity) {
        this.id = id;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Volume getVolume() {
        return volume;
    }

    public void setVolume(Volume volume) {
        this.volume = volume;
    }
}
