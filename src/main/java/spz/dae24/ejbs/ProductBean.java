package spz.dae24.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.validation.constraints.Min;
import spz.dae24.common.enums.SensorType;
import spz.dae24.entities.Product;
import spz.dae24.exceptions.EntityExistsException;
import spz.dae24.exceptions.EntityNotFoundException;

import java.util.ArrayList;
import java.util.List;

@Stateless
public class ProductBean {

    @PersistenceContext
    private EntityManager em;

    public List<Product> findAll() {
        return em.createNamedQuery("getAllProducts", Product.class).getResultList();
    }

    public Product find(int code) throws EntityNotFoundException {
        var product = em.find(Product.class, code);

        if (product == null)
            throw new EntityNotFoundException("Product with code " + code + " not found");

        return product;
    }

    public void create(@Min(1) int code, String name, List<String> requiredSensors) throws EntityExistsException, IllegalArgumentException {
        if (exists(code))
            throw new EntityExistsException("Product with code " + code + " already exists");

        List<SensorType> sensorTypes = new ArrayList<>();
        for (String sensor : requiredSensors) {
            SensorType.parse(sensor);
        }

        em.persist(new Product(code, name, sensorTypes));
    }

    public boolean exists(int code) {
        return em.find(Product.class, code) != null;
    }
}
