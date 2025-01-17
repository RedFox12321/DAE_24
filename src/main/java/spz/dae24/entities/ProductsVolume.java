package spz.dae24.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;


@Entity
@NamedQueries({
        @NamedQuery(
                name = "getAllProductsVolume",
                query = "SELECT pv FROM ProductsVolume pv ORDER BY pv.id"
        )
})
@Table(name = "products_volumes")
public class ProductsVolume {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private long id;

        @ManyToOne
        @NotNull
        private Product product;

        @ManyToOne
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
