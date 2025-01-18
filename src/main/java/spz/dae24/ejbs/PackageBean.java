package spz.dae24.ejbs;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.hibernate.Hibernate;
import spz.dae24.common.enums.Status;
import spz.dae24.dtos.PackageWithAllDTO;
import spz.dae24.entities.Client;
import spz.dae24.entities.Package;
import spz.dae24.entities.Volume;
import spz.dae24.exceptions.MyEntityExistsException;
import spz.dae24.exceptions.MyEntityNotFoundException;

import java.util.List;

@Stateless
public class PackageBean {
    @PersistenceContext
    private EntityManager em;

    @EJB
    private ClientBean clientBean;
    @EJB
    private VolumeBean volumeBean;

    public List<Package> findAll() {
        return em.createNamedQuery("getAllPackages", Package.class).getResultList();
    }

    public List<Package> findByStatus(String status) throws IllegalArgumentException {
        Status statusEnum = Status.parse(status);

        TypedQuery<Package> query = em.createNamedQuery("getPackagesByStatus", Package.class);
        query.setParameter("status", statusEnum);

        return query.getResultList();
    }

    public List<Package> findByClient(long clientId) throws MyEntityNotFoundException {
        var client = em.find(Client.class, clientId);

        if(client == null)
            throw new MyEntityNotFoundException("Client with id " + clientId + " not found");

        return client.getPackages();
    }

    public Package find(long code) throws MyEntityNotFoundException {
        var _package = em.find(Package.class, code);

        if(_package == null)
            throw new MyEntityNotFoundException("Package with code " + code + " not found");

        return _package;
    }

    public Package findWithVolumes(long code) throws MyEntityNotFoundException {
        var _package = find(code);

        Hibernate.initialize(_package.getVolumes());

        return _package;
    }


    public void create(long code, String clientUsername) throws MyEntityNotFoundException, MyEntityExistsException, IllegalArgumentException {
        if (code < 1)
            throw new IllegalArgumentException("Package code must be a positive number.");

        if (exists(code))
            throw new MyEntityExistsException("Package with code " + code + " already exists");

        Client client = em.find(Client.class, clientUsername);

        if(client == null)
            throw new MyEntityNotFoundException("Client with username " + clientUsername + " not found");

        Package pkg = new Package(code, Status.ACTIVE, client);

        em.persist(pkg);

        client.addPackage(pkg);
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void makePackageOrder(PackageWithAllDTO packageDTO) throws IllegalArgumentException, MyEntityNotFoundException, MyEntityExistsException {
        var volumesDTO = packageDTO.getVolumes();
        if (volumesDTO == null || volumesDTO.isEmpty())
            throw new IllegalArgumentException("Package needs at least 1 volume.");

        create(packageDTO.getCode(), packageDTO.getClientUsername());

        for (var volumeDTO : volumesDTO) {
            volumeBean.addVolumeToPackageOrder(volumeDTO, packageDTO.getCode());
        }
    }

    public void completePackage(long code) throws MyEntityNotFoundException {
        Package pkg = find(code);
        pkg.setStatus(Status.DELIVERED);

        em.merge(pkg);
    }

    public void tryCompletePackage(long code) throws MyEntityNotFoundException {
        Package pkg = find(code);

        if (!pkg.getStatus().equals(Status.ACTIVE))
            throw new IllegalArgumentException("Package with code " + code + " has been " + pkg.getStatus().name() + " already.");

        var activeVolumes = pkg.getVolumes().stream().filter(volume -> volume.getStatus().equals(Status.ACTIVE)).count();
        if (activeVolumes == 0){
            pkg.setStatus(Status.DELIVERED);

            em.merge(pkg);
        }
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void cancelPackage(long code) throws MyEntityNotFoundException {
        Package pkg = findWithVolumes(code);

        if (!pkg.getStatus().equals(Status.ACTIVE))
            throw new IllegalArgumentException("Package with code " + code + " has been " + pkg.getStatus().name() + " already.");

        pkg.setStatus(Status.CANCELLED);

        em.merge(pkg);

        for(Volume volume : pkg.getVolumes()) {
            if(volume.getStatus().equals(Status.ACTIVE))
                volumeBean.cancel(volume.getCode());
        }
    }

    public boolean exists(long code) {
        return em.find(Package.class, code) != null;
    }
}
