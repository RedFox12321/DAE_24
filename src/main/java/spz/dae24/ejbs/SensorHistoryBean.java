package spz.dae24.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import spz.dae24.entities.Sensor;
import spz.dae24.entities.SensorHistory;
import spz.dae24.exceptions.MyEntityNotFoundException;

import java.util.List;

@Stateless
public class SensorHistoryBean {

    @PersistenceContext
    private EntityManager em;

    public List<SensorHistory> findAll() {
        return em.createNamedQuery("getAllSensorsHistory", SensorHistory.class).getResultList();
    }

    public SensorHistory find(long id) throws MyEntityNotFoundException {
        var sensorHistory = em.find(SensorHistory.class, id);
        if (sensorHistory == null)
            throw new MyEntityNotFoundException("Sensor history with id " + id + " not found");

        return sensorHistory;
    }

    public long create(long sensorId, String value) throws MyEntityNotFoundException, IllegalArgumentException {
        var sensor = em.find(Sensor.class, sensorId);
        if (sensor == null)
            throw new MyEntityNotFoundException("Sensor with id " + sensorId + " not found");

        if (!sensor.isActive())
            throw new IllegalArgumentException("Sensor with id " + sensorId + " is not active anymore.");

        SensorHistory sensorHistory = new SensorHistory(System.currentTimeMillis(), value, sensor);

        em.persist(sensorHistory);
        sensor.addHistory(sensorHistory);

        return sensorHistory.getId();
    }
}
