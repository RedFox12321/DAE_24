package spz.dae24.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import spz.dae24.common.enums.SensorType;
import spz.dae24.entities.Product;
import spz.dae24.exceptions.MyEntityExistsException;
import spz.dae24.exceptions.MyEntityNotFoundException;

import java.util.List;

@Stateless
public class ProductBean {

    @PersistenceContext
    private EntityManager em;

    public List<Product> findAll() {
        return em.createNamedQuery("getAllProducts", Product.class).getResultList();
    }

    public Product find(int code) throws MyEntityNotFoundException {
        var product = em.find(Product.class, code);

        if (product == null)
            throw new MyEntityNotFoundException("Product with code " + code + " not found");

        return product;
    }

    public void create(int code, String name, List<String> requiredSensors) throws MyEntityExistsException, IllegalArgumentException {
        if (code < 1)
            throw new IllegalArgumentException("Product code must be a positive number.");

        if (exists(code))
            throw new MyEntityExistsException("Product with code " + code + " already exists");

        em.persist(new Product(code, name, SensorType.parse(requiredSensors)));
    }

    public boolean exists(int code) {
        return em.find(Product.class, code) != null;
    }
}
