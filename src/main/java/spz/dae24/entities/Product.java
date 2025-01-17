package spz.dae24.entities;

import jakarta.persistence.*;
import spz.dae24.common.enums.SensorType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Entity
@NamedQueries({
        @NamedQuery(
                name = "getAllProducts",
                query = "SELECT new Product(p.code, p.name) FROM Product p ORDER BY p.code"
        )
})
@Table(name = "products")
public class Product extends Versionable implements Serializable{
    
    @Id
    private int code;

    private String name;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    @Column(name = "required_sensors")
    private List<SensorType> requiredSensors;

    @OneToMany(mappedBy = "product")
    private final List<ProductsVolume> productsVolumes = new ArrayList<>();

    public Product(){

    }

    public Product(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public Product(int code, String name, List<SensorType> requiredSensors){
        this.code = code;
        this.name = name;
        this.requiredSensors = requiredSensors;
    }

    public int getCode(){
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
    public String getName(){
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public List<SensorType> getRequiredSensors() {
        return requiredSensors;
    }

    public void setRequiredSensors(List<SensorType> requiredSensors) {
        this.requiredSensors = requiredSensors;
    }

    public boolean addProductsVolumes(ProductsVolume productsVolume) {
        return productsVolumes.add(productsVolume);
    }
    public boolean removeProductsVolumes(ProductsVolume productsVolume) {
        return productsVolumes.remove(productsVolume);
    }

}

