package spz.dae24.entities;

import jakarta.persistence.*;
import spz.dae24.common.enums.SensorType;

import java.io.Serializable;


@Entity
@NamedQueries({
        @NamedQuery(
                name = "getAllProducts",
                query = "SELECT p FROM Product p ORDER BY p.code"
        )
})

public class Product implements Serializable{
    
    @Id
    private int code;

    private String name;

    @Enumerated(EnumType.STRING)
    private SensorType requiredSensors;

    public Product(){

    }
    public Product(int code, String name, SensorType requiredSensors){
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
}

