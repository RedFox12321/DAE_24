package spz.dae24.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;


@Entity
@NamedQueries({
    @NamedQuery(
        name = "getAllProductsVolume",
        query = "SELECT pv FROM ProductsVolume pv ORDER BY pv.id"
    ),
    @NamedQuery(
        name = "getAllProductsVolumeByProductCode",
        query = "SELECT pv FROM ProductsVolume pv WHERE pv.product.code=:productCode ORDER BY pv.id"
    ),
    @NamedQuery(
        name = "getAllProductsVolumeByVolumeCode",
        query = "SELECT pv FROM ProductsVolume pv WHERE pv.volume.code=:volumeCode ORDER BY pv.id"
    )
})
@Table(name = "products_volumes")
public class ProductsVolume extends Versionable implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @NotNull
    private Product product;

    @ManyToOne
    @NotNull
    private Volume volume;

    public ProductsVolume() {
    }

    public ProductsVolume(Product product, Volume volume, int quantity) {
            this.product = product;
            this.volume = volume;
            this.quantity = quantity;
    }

    private int quantity;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
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
