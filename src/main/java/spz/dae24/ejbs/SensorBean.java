package spz.dae24.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Hibernate;
import spz.dae24.common.enums.SensorType;
import spz.dae24.entities.Sensor;
import spz.dae24.entities.Volume;
import spz.dae24.exceptions.MyEntityExistsException;
import spz.dae24.exceptions.MyEntityNotFoundException;

import java.util.List;


@Stateless
public class SensorBean {

    @PersistenceContext
    private EntityManager em;

    public Sensor find(long id) throws MyEntityNotFoundException {
        var sensor = em.find(Sensor.class, id);
        if (sensor == null)
            throw new MyEntityNotFoundException("Sensor with id " + id + " not found");

        return sensor;
    }

    public Sensor findWithHistory(long id) throws MyEntityNotFoundException {
        var sensor = find(id);

        Hibernate.initialize(sensor.getHistory());

        return sensor;
    }

    public List<Sensor> findAll() {
        return em.createNamedQuery("getAllSensors", Sensor.class).getResultList();
    }

    public void create(long id, String type, long volumeCode) throws MyEntityNotFoundException, MyEntityExistsException, IllegalArgumentException {
        if (id < 1)
            throw new IllegalArgumentException("Sensor id must be a positive number.");

        if (exists(id))
            throw new MyEntityExistsException("Sensor with id " + id + " already exists");

        var volume = em.find(Volume.class, volumeCode);
        if (volume == null)
            throw new MyEntityNotFoundException("Volume with id " + volumeCode + " not found");

        SensorType sensorType = SensorType.parse(type);

        em.persist(new Sensor(id, true, sensorType, volume));
    }

    public void disable(long id) throws MyEntityNotFoundException {
        var sensor = find(id);

        sensor.setActive(false);
        em.merge(sensor);
    }

    public boolean exists(long id) {
        return em.find(Sensor.class, id) != null;
    }
}
