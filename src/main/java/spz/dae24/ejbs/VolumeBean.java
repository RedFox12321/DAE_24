package spz.dae24.ejbs;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Hibernate;
import spz.dae24.common.enums.PackageType;
import spz.dae24.common.enums.Status;
import spz.dae24.dtos.SensorDTO;
import spz.dae24.dtos.VolumeDTO;
import spz.dae24.entities.Package;
import spz.dae24.entities.Sensor;
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

        return volume;
    }

    public Volume findWithSensorsAndProductVolumes(long code) throws EntityNotFoundException {
        Volume volume = find(code);

        Hibernate.initialize(volume.getProductsVolumes());
        Hibernate.initialize(volume.getSensors());

        return volume;
    }

    public void create(long code, String packageType, long packageCode) throws EntityNotFoundException, EntityExistsException {
        if (exists(code))
            throw new EntityExistsException("Volume with code " + code + " already exists");

        Package pkg = em.find(Package.class, packageCode);
        if(pkg == null)
            throw new EntityNotFoundException("Package with code " + packageCode + " not found");

        Volume volume = new Volume(code, pkg.getVolumeCount() + 1, Status.ACTIVE, PackageType.valueOf(packageType.toUpperCase()), pkg);

        em.persist(volume);
        pkg.addVolume(volume);
    }

    public void updateStatus(long code, String status) throws EntityNotFoundException {
        var volume = find(code);

        volume.setStatus(Status.valueOf(status.toUpperCase()));
        em.merge(volume);
    }

    public boolean exists(long code) {return em.find(Volume.class, code) != null;}
}
