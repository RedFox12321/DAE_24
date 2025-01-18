package spz.dae24.ejbs;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Hibernate;
import spz.dae24.common.enums.PackageType;
import spz.dae24.common.enums.SensorType;
import spz.dae24.common.enums.Status;
import spz.dae24.dtos.VolumeWithSensorsAndProductVolumesDTO;
import spz.dae24.entities.Package;
import spz.dae24.entities.Product;
import spz.dae24.entities.Sensor;
import spz.dae24.entities.Volume;
import spz.dae24.exceptions.MyEntityExistsException;
import spz.dae24.exceptions.MyEntityNotFoundException;

import java.util.EnumSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Stateless
public class VolumeBean {
    @PersistenceContext
    private EntityManager em;

    @EJB
    private PackageBean packageBean;
    @EJB
    private VolumeBean volumeBean;
    @EJB
    private SensorBean sensorBean;
    @EJB
    private ProductsVolumeBean productsVolumeBean;

    public List<Volume> findAll() {return em.createNamedQuery("getAllVolumes", Volume.class).getResultList();}

    public Volume find(long code) throws MyEntityNotFoundException {
        var volume = em.find(Volume.class, code);

        if (volume == null)
            throw new MyEntityNotFoundException("Volume with code " + code + " not found");

        return volume;
    }

    public Volume findWithSensorsAndProductsVolumes(long code) throws MyEntityNotFoundException {
        Volume volume = find(code);

        Hibernate.initialize(volume.getProductsVolumes());
        Hibernate.initialize(volume.getSensors());

        return volume;
    }

    public void create(long code, String packageType, long packageCode) throws IllegalArgumentException, MyEntityExistsException, MyEntityNotFoundException {
        if (code < 1)
            throw new IllegalArgumentException("Volume code must be a positive number.");

        if (exists(code))
            throw new MyEntityExistsException("Volume with code " + code + " already exists");

        Package pkg = em.find(Package.class, packageCode);
        if(pkg == null)
            throw new MyEntityNotFoundException("Package with code " + packageCode + " not found");

        Volume volume = new Volume(code, pkg.getVolumeCount() + 1, Status.ACTIVE, PackageType.parse(packageType), pkg);

        em.persist(volume);
        pkg.addVolume(volume);
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void addVolumeToPackageOrder(VolumeWithSensorsAndProductVolumesDTO volumeDTO, long packageCode) throws IllegalArgumentException, MyEntityNotFoundException, MyEntityExistsException {
        var productsVolumes = volumeDTO.getProductsVolume();
        if (productsVolumes == null || productsVolumes.isEmpty())
            throw new IllegalArgumentException("Volume with code " + volumeDTO.getCode() + " needs at least 1 product.");

        volumeBean.create(volumeDTO.getCode(), volumeDTO.getPackageType(), packageCode);
        var pkg = em.find(Package.class, packageCode);
        if (!pkg.getStatus().equals(Status.ACTIVE))
            throw new IllegalArgumentException("Package with code " + packageCode + " has been " + pkg.getStatus().name() + " already.");

        Set<SensorType> requiredSensors = EnumSet.noneOf(SensorType.class);
        for (var productsVolume : productsVolumes) {
            productsVolumeBean.create(productsVolume.getProductCode(), volumeDTO.getCode(), productsVolume.getQuantity());

            Product product = em.find(Product.class, productsVolume.getProductCode());
            requiredSensors.addAll(product.getRequiredSensors());
        }

        if (volumeDTO.getSensors() != null)
            for (var sensor : volumeDTO.getSensors()) {
                sensorBean.create(sensor.getId(), sensor.getType(), volumeDTO.getCode());
                requiredSensors.remove(SensorType.valueOf(sensor.getType()));
            }

        if (!requiredSensors.isEmpty())
            throw new IllegalArgumentException("The chosen products, in volume " + volumeDTO.getCode() + ", do not have all required sensors. Sensors in fault ["
                    + requiredSensors.stream()
                    .map(SensorType::getName)
                    .collect(Collectors.joining(", "))
                    + "]");
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void deliver(long code) throws MyEntityNotFoundException, IllegalArgumentException {
        var volume = find(code);

        if (!volume.getStatus().equals(Status.ACTIVE))
            throw new IllegalArgumentException("Volume with code " + code + " has been " + volume.getPackage().getStatus().name() + " already.");

        volume.setStatus(Status.DELIVERED);
        em.merge(volume);

        for(Sensor s : volume.getSensors()){
            sensorBean.disable(s.getId());
        }

        packageBean.tryCompletePackage(volume.getPackage().getCode());
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void cancel(long code) throws MyEntityNotFoundException {
        var volume = find(code);

        if (!volume.getStatus().equals(Status.ACTIVE))
            throw new IllegalArgumentException("Volume with code " + code + " has been " + volume.getPackage().getStatus().name() + " already.");

        volume.setStatus(Status.CANCELLED);
        em.merge(volume);

        for(Sensor s : volume.getSensors()){
            sensorBean.disable(s.getId());
        }
    }

    public boolean exists(long code) {return em.find(Volume.class, code) != null;}
}
