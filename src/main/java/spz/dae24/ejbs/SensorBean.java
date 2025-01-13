package spz.dae24.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.*;
import org.hibernate.Hibernate;
import spz.dae24.common.enums.SensorType;
import spz.dae24.entities.Sensor;
import spz.dae24.entities.Volume;
import spz.dae24.exceptions.SensorTypeNotExistException;

import java.util.List;


@Stateless
public class SensorBean {

    @PersistenceContext
    private EntityManager em;

    public Sensor find(long id) throws EntityNotFoundException {
        var sensor = em.find(Sensor.class, id);
        if (sensor == null)
            throw new EntityNotFoundException("Sensor with id " + id + " not found");

        return sensor;
    }

    public Sensor findWithHistory(long id) throws EntityNotFoundException {
        var sensor = find(id);
        Hibernate.initialize(sensor.getHistory());

        return sensor;
    }

    public List<Sensor> findAll() {
        return em.createNamedQuery("getAllSensors", Sensor.class).getResultList();
    }

    public void create(long id, String type, long volumeCode) throws EntityExistsException {
        if (exists(id))
            throw new EntityExistsException("Sensor with id " + id + " already exists");

        var volume = em.find(Volume.class, volumeCode);
        if (volume == null)
            throw new EntityNotFoundException("Volume with id " + volumeCode + " not found");

        SensorType sensorType = null;
        try {
            sensorType = SensorType.valueOf(type.toUpperCase());
        } catch(Exception exception) {
            StringBuilder msg = new StringBuilder("Sensor type " + sensorType + " not found. Possible values: ");

            SensorType[] values = SensorType.values();
            int i = 1;
            for (SensorType sensorTypeEnum : values) {
                msg.append(sensorTypeEnum.name());
                if (i < values.length)
                    msg.append(", ");
            }

            throw new SensorTypeNotExistException(msg.toString());
        }

        em.persist(new Sensor(id, true, sensorType, volume));
    }

    public void disable(long id) throws EntityNotFoundException {
        var sensor = find(id);

        sensor.setActive(false);
        em.merge(sensor);
    }

    public boolean exists(long id) {
        return em.find(Sensor.class, id) != null;
    }
}
