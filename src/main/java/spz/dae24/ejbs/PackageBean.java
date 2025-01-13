package spz.dae24.ejbs;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Hibernate;
import spz.dae24.common.enums.Status;
import spz.dae24.entities.Client;
import spz.dae24.entities.Package;

import java.util.List;

@Stateless
public class PackageBean {
    @PersistenceContext
    private EntityManager em;

    @EJB
    private ClientBean clientBean;

    public List<Package> findAll() {return em.createNamedQuery("getAllPackages", Package.class).getResultList();}

    public List<Package> findByStatus(String status) {
        List<Package> packages = findAll();

        packages.removeIf(p -> !p.getStatus().equals(Status.valueOf(status.toUpperCase())));

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

    public void create(long code, long clientId) throws EntityNotFoundException, EntityExistsException {
        if (exists(code))
            throw new EntityExistsException("Package with code " + code + " already exists");

        Client client = em.find(Client.class, clientId);

        if(client == null)
            throw new EntityNotFoundException("Client with id " + clientId + " not found");

        Package pkg = new Package(code, Status.ACTIVE, client);

        em.persist(pkg);

        client.addPackage(pkg);
    }

    public void completePackage(long code) throws EntityNotFoundException {
        Package pkg = em.find(Package.class, code);
        pkg.setStatus(Status.DELIVERED);

        em.merge(pkg);
    }

    public void cancelPackage(long code) throws EntityNotFoundException {
        Package pkg = em.find(Package.class, code);
        pkg.setStatus(Status.CANCELLED);

        em.merge(pkg);
    }

    public boolean exists(long code) {
        return em.find(Package.class, code) != null;
    }
}
