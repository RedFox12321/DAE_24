package spz.dae24.ejbs;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import spz.dae24.exceptions.TypeNotExistException;

import org.hibernate.Hibernate;
import spz.dae24.common.enums.Status;
import spz.dae24.entities.Client;
import spz.dae24.entities.Package;
import spz.dae24.entities.Volume;

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

    public List<Package> findByStatus(String status) {
        Status statusType = null;
        try {
            statusType = Status.valueOf(status.toUpperCase());
        }
        catch(Exception e){
            throw new TypeNotExistException("Status does not exist");
        }
        TypedQuery<Package> query = (TypedQuery<Package>) em.createNamedQuery("getPackagesByStatus", Package.class);
        query.setParameter("status", statusType);
        List<Package> packages = query.getResultList();

        return packages;
    }

    public List<Package> findByClient(long clientId) throws EntityNotFoundException {
        var client = em.find(Client.class, clientId);

        if(client == null)
        throw new EntityNotFoundException("Client with id " + clientId + " not found");

        return client.getPackages();
    }

    public Package find(long code) throws EntityNotFoundException {
        var _package = em.find(Package.class, code);

        if(_package == null)
        throw new EntityNotFoundException("Package with code " + code + " not found");

        return _package;
    }

    public Package findWithVolumes(long code) throws EntityNotFoundException {
        var _package = find(code);

        Hibernate.initialize(_package.getVolumes());

        return _package;
    }


    public void create(long code, String clientUsername) throws EntityNotFoundException, EntityExistsException {
        if (exists(code))
        throw new EntityExistsException("Package with code " + code + " already exists");

        Client client = em.find(Client.class, clientUsername);

        if(client == null)
        throw new EntityNotFoundException("Client with username " + clientUsername + " not found");

        Package pkg = new Package(code, Status.ACTIVE, client);

        em.persist(pkg);

        client.addPackage(pkg);
    }

    public void completePackage(long code) throws EntityNotFoundException {
        Package pkg = find(code);
        pkg.setStatus(Status.DELIVERED);

        em.merge(pkg);
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void cancelPackage(long code) throws EntityNotFoundException {
        Package pkg = findWithVolumes(code);

        pkg.setStatus(Status.CANCELLED);
        for(Volume volume : pkg.getVolumes()) {
          if(!volume.getStatus().equals(Status.DELIVERED))
            volumeBean.cancel(volume.getCode());
        }

        em.merge(pkg);
    }

    public boolean exists(long code) {
        return em.find(Package.class, code) != null;
    }
}
