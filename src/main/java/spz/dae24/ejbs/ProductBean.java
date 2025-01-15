package spz.dae24.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.EntityManager;
import spz.dae24.common.enums.SensorType;
import spz.dae24.entities.Product;
import spz.dae24.exceptions.SensorTypeNotExistException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    public void create(int code, String name, List<String> requiredSensors) throws EntityExistsException, SensorTypeNotExistException {
        if (exists(code))
            throw new EntityExistsException("Product with code " + code + " already exists");

        List<SensorType> sensorTypes = new ArrayList<>();
        for (String sensor : requiredSensors) {
            try {
                sensorTypes.add(SensorType.valueOf(sensor.toUpperCase()));
            } catch (Exception e){
                StringBuilder msg = new StringBuilder("Sensor type " + sensor + " not found. Possible values: ");

                SensorType[] values = SensorType.values();
                int i = 1;
                for (SensorType sensorType : values) {
                    msg.append(sensorType.name());
                    if (i < values.length)
                        msg.append(", ");
                }

                throw new SensorTypeNotExistException(msg.toString());
            }
        }

        em.persist(new Product(code, name, sensorTypes));
    }

    public boolean exists(int code) {
        return em.find(Product.class, code) != null;
    }
}
