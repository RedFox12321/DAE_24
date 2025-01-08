package spz.dae24.entities;

import jakarta.persistence.*;
import java.io.Serializable;


@Entity
@NamedQueries({
        @NamedQuery(
                name = "getAllProducts",
                query = "SELECT new product(p.code, p.name) FROM Product p ORDER BY p.code"
        )
})

public class Product implements Serializable{
    
    @Id
    private int code;

    private String name;


    public Product(int code, String name){
        this.code = code;
        this.name = name;
    }

    public int getCode(){
        return code;
    }

    public String getName(){
        return name;
    }

}

