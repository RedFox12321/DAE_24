package spz.dae24.ejbs;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
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

    public List<Package> findByClient(int clientId) throws EntityNotFoundException {
        List<Package> packages = findAll();

        var client = em.find(Client.class, clientId);

        if(client == null)
            throw new EntityNotFoundException("Client with id " + clientId + " not found");

        packages.removeIf(p -> !p.getClient().equals(client));

        return packages;
    }

    public Package find(long code) throws EntityNotFoundException {
        var _package = em.find(Package.class, code);

        if(_package == null)
            throw new EntityNotFoundException("Package with code " + code + " not found");

        Hibernate.initialize(_package.getVolumes());

        return _package;
    }

    public void create(int clientId) throws EntityNotFoundException {
        if(!clientBean.exists(clientId))
            throw new EntityNotFoundException("Client with id " + clientId + " not found");

        Client client = em.find(Client.class, clientId);

        Package pkg = new Package(Status.ACTIVE);
        pkg.setClient(client);

        client.addPackage(pkg);

        em.persist(pkg);
    }

    public boolean exists(long code){
        return em.find(Package.class, code) != null;
    }
}
