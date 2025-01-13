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
import spz.dae24.dtos.ProductsVolumeDTO;
import spz.dae24.dtos.SensorDTO;
import spz.dae24.entities.Package;
import spz.dae24.entities.Product;
import spz.dae24.entities.ProductsVolume;
import spz.dae24.entities.Sensor;
import spz.dae24.entities.Volume;

import java.util.List;

@Stateless
public class VolumeBean {
    @PersistenceContext
    private EntityManager em;

    @EJB
    private ProductsVolumeBean productsVolumeBean;

    @EJB
    private SensorBean sensorBean;

    @EJB
    private PackageBean packageBean;

    public List<Volume> findAll() {return em.createNamedQuery("getAllVolumes", Volume.class).getResultList();}

    public Volume find(long code) throws EntityNotFoundException {
        var volume = em.find(Volume.class, code);

        if (volume == null)
            throw new EntityNotFoundException("Volume with code " + code + " not found");

        Hibernate.initialize(volume.getVolumeProducts());

        return volume;
    }

    public void create(PackageType packageType, Package _package, List<SensorDTO> sensors) throws EntityNotFoundException {
        Package packageManaged = em.merge(_package);

        Volume volume = new Volume(packageManaged.getVolumes().size() + 1, Status.CANCELLED,packageType);

        volume.setPackage(packageManaged);
        packageManaged.addVolume(volume);

        for(SensorDTO sDTO : sensors){
            if(!sensorBean.exists(sDTO.getId()))
                throw new EntityNotFoundException("Sensor with id " + sDTO.getId() + " not found");

            Sensor s = sensorBean.find(sDTO.getId());
            s.setVolume(volume);
            volume.getSensors().add(s);
        }
        em.persist(volume);
    }

    public void updateStatus(long code, String status) throws EntityNotFoundException {
        var v = find(code);

        v.setStatus(Status.valueOf(status.toUpperCase()));
        em.merge(v);
    }

    public boolean exists(long code) {return em.find(Volume.class, code) != null;}
}
