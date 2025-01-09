package spz.dae24.ejbs;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import spz.dae24.entities.Sensor;
import spz.dae24.entities.SensorHistory;
import spz.dae24.exceptions.SensorNotActiveException;

import java.util.List;

@Stateless
public class SensorHistoryBean {

    @PersistenceContext
    private EntityManager em;

    public List<SensorHistory> findAll() {
        return em.createNamedQuery("getAllSensorsHistory", SensorHistory.class).getResultList();
    }

    public SensorHistory find(long id) {
        var sensorHistory = em.find(SensorHistory.class, id);
        if (sensorHistory == null)
            throw new EntityNotFoundException("Sensor history with id " + id + " not found");

        return sensorHistory;
    }

    public void create(long sensorId, String value) throws EntityExistsException {
        var sensor = em.find(Sensor.class, sensorId);
        if (!sensor.isActive())
            throw new SensorNotActiveException("Sensor with id " + sensorId + " is not active anymore.");

        SensorHistory sensorHistory = new SensorHistory(System.currentTimeMillis(), value, sensor);

        em.persist(sensorHistory);
        sensor.addHistory(sensorHistory);
    }


}
