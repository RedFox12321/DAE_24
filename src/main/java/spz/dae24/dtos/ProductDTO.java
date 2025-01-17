package spz.dae24.dtos;

import spz.dae24.entities.Product;

import java.util.List;
import java.util.stream.Collectors;

public class ProductDTO {
    private int code;
    private String name;

    public ProductDTO() {
    }

    public ProductDTO(int code, String name){
        this.code = code;
        this.name = name;
    }

    public long getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
         this.name = name;
     }

    public static ProductDTO from(Product product) {
        return new ProductDTO(
            product.getCode(),
            product.getName()
        );
    }

    public static List<ProductDTO> from(List<Product> products) {
        return products.stream().map(ProductDTO::from).collect(Collectors.toList());
    }
}
