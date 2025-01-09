package spz.dae24.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Hibernate;
import spz.dae24.common.enums.Status;
import spz.dae24.entities.Volume;

import java.util.List;

@Stateless
public class VolumeBean {
    @PersistenceContext
    private EntityManager em;

    public List<Volume> findAll() {return em.createNamedQuery("getAllVolumes", Volume.class).getResultList();}

    public Volume find(long code) throws EntityNotFoundException {
        var volume = em.find(Volume.class, code);

        if (volume == null)
            throw new EntityNotFoundException("Volume with code " + code + " not found");

        Hibernate.initialize(volume.getVolumeProducts());

        return volume;
    }

    public void create(long code, int number, String status) throws EntityExistsException{
        if(exists(code))
            throw new EntityExistsException("Volume with code " + code + " already exists");

        em.persist(new Volume(code, number, Status.valueOf(status.toUpperCase())));
    }

    public void updateStatus(long code, String status) throws EntityNotFoundException {
        var v = find(code);

        v.setStatus(Status.valueOf(status.toUpperCase()));
        em.merge(v);
    }

    public boolean exists(long code) {return em.find(Volume.class, code) != null;}
}
