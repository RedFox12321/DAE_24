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

    public List<Volume> findAll() {return em.createNamedQuery("getAllVolumes", Volume.class).getResultList();}

    public Volume find(long code) throws EntityNotFoundException {
        var volume = em.find(Volume.class, code);

        if (volume == null)
            throw new EntityNotFoundException("Volume with code " + code + " not found");

        Hibernate.initialize(volume.getVolumeProducts());

        return volume;
    }

    public void create(long code, int number, String status, List<ProductsVolumeDTO> productsVolumes, List<SensorDTO> sensors, String packageType) throws EntityExistsException, EntityNotFoundException {
        if(exists(code))
            throw new EntityExistsException("Volume with code " + code + " already exists");

        Volume volume = new Volume(code, number, Status.valueOf(status.toUpperCase()), PackageType.valueOf(packageType.toUpperCase()));

        for(ProductsVolumeDTO pvDTO : productsVolumes){
            if(!productsVolumeBean.exists(pvDTO.getId()))
                throw new EntityNotFoundException("Product volume with id " + pvDTO.getId() + " not found");

            ProductsVolume pv = productsVolumeBean.find(pvDTO.getId());
            pv.setVolume(volume);
            volume.getVolumeProducts().add(pv);
        }

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
