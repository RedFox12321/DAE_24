package spz.dae24.ejbs;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Hibernate;
import spz.dae24.common.enums.PackageType;
import spz.dae24.common.enums.SensorType;
import spz.dae24.common.enums.Status;
import spz.dae24.dtos.PackageDTO;
import spz.dae24.dtos.VolumeDTO;
import spz.dae24.entities.Package;
import spz.dae24.entities.Product;
import spz.dae24.entities.Sensor;
import spz.dae24.entities.Volume;
import spz.dae24.exceptions.QuantityLowerThanOneException;
import spz.dae24.exceptions.SensorInFaultException;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Stateless
public class VolumeBean {
    @PersistenceContext
    private EntityManager em;

    @EJB
    private VolumeBean volumeBean;
    @EJB
    private SensorBean sensorBean;
    @EJB
    private ProductsVolumeBean productsVolumeBean;

    public List<Volume> findAll() {return em.createNamedQuery("getAllVolumes", Volume.class).getResultList();}

    public Volume find(long code) throws EntityNotFoundException {
        var volume = em.find(Volume.class, code);

        if (volume == null)
            throw new EntityNotFoundException("Volume with code " + code + " not found");

        return volume;
    }

    public Volume findWithSensorsAndProductsVolumes(long code) throws EntityNotFoundException {
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

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void addVolumeToPackageOrder(VolumeDTO volumeDTO, long packageCode) throws QuantityLowerThanOneException, EntityNotFoundException, EntityExistsException, SensorInFaultException {
        volumeBean.create(volumeDTO.getCode(), volumeDTO.getPackageType(), packageCode);

        var productsVolumes = volumeDTO.getProductsVolume();
        if (productsVolumes.isEmpty())
            throw new QuantityLowerThanOneException("Volume with code " + volumeDTO.getCode() + " needs at least 1 product.");

        Set<SensorType> requiredSensors = EnumSet.noneOf(SensorType.class);
        for (var productsVolume : productsVolumes) {
            productsVolumeBean.create(productsVolume.getProductCode(), volumeDTO.getCode(), productsVolume.getQuantity());

            Product product = em.find(Product.class, productsVolume.getProductCode());
            requiredSensors.addAll(product.getRequiredSensors());
        }

        for (var sensor : volumeDTO.getSensors()) {
            sensorBean.create(sensor.getId(), sensor.getType(), volumeDTO.getCode());
            requiredSensors.remove(SensorType.valueOf(sensor.getType()));
        }

        if (!requiredSensors.isEmpty())
            throw new SensorInFaultException("The chosen products, in volume " + volumeDTO.getCode() + ", do not have all required sensors. Sensors in fault ["
                    + requiredSensors.stream()
                    .map(SensorType::getName)
                    .collect(Collectors.joining(", "))
                    + "]");
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deliver(long code) throws EntityNotFoundException {
        var volume = findWithSensorsAndProductsVolumes(code);

        volume.setStatus(Status.DELIVERED);
        for(Sensor s : volume.getSensors()){
            sensorBean.disable(s.getId());
        }

        em.merge(volume);
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void cancel(long code) throws EntityNotFoundException {
        var volume = findWithSensorsAndProductsVolumes(code);

        volume.setStatus(Status.CANCELLED);
        for(Sensor s : volume.getSensors()){
            sensorBean.disable(s.getId());
        }

        em.merge(volume);
    }

    public boolean exists(long code) {return em.find(Volume.class, code) != null;}
}
