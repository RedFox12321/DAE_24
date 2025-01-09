package spz.dae24.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.EntityManager;
import spz.dae24.common.enums.SensorType;
import spz.dae24.entities.Product;

import java.util.List;

@Stateless
public class ProductBean {

    @PersistenceContext
    private EntityManager em;

    public List<Product> findAll() {
        return em.createNamedQuery("getAllProducts", Product.class).getResultList();
    }

    public Product find(int code) {
        var product = em.find(Product.class, code);

        if (product == null)
            throw new EntityNotFoundException("Product with code " + code + " not found");

        return product;
    }

    public void create(int code, String name, String requiredSensor) throws EntityExistsException {
        if (exists(code))
            throw new EntityExistsException("Product with code " + code + " already exists");

        em.persist(new Product(code, name, requiredSensor == null ? null : SensorType.valueOf(requiredSensor.toUpperCase())));
    }

    public boolean exists(int code) {
        return em.find(Product.class, code) != null;
    }
}
