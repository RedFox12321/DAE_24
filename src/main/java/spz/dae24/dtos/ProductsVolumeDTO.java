package spz.dae24.dtos;

import spz.dae24.entities.ProductsVolume;

import java.util.List;
import java.util.stream.Collectors;

public class ProductsVolumeDTO {

    private long id;
    private int productCode;
    private int volumeCode;
    private int quantity;

    public ProductsVolumeDTO(long id, int productCode /* int volumeCode*/, int quantity){
        this.id = id;
        this.productCode = productCode;
//        this.volumeCode = volumeCode;
        this.quantity = quantity;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getProductCode() {
        return productCode;
    }

    public void setProductCode(int productCode) {
        this.productCode = productCode;
    }

    public int getVolumeCode() {
        return volumeCode;
    }

    public void setVolumeCode(int volumeCode) {
        this.volumeCode = volumeCode;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public static ProductsVolumeDTO from(ProductsVolume productsVolume) {
        return new ProductsVolumeDTO(
                productsVolume.getId(),
                productsVolume.getProduct().getCode(),
//                productsVolume.getVolume().getCode(),
                productsVolume.getQuantity()

        );
    }

    public static List<ProductsVolumeDTO> from(List<ProductsVolume> productsVolume) {
        return productsVolume.stream().map(ProductsVolumeDTO::from).collect(Collectors.toList());
    }
}
