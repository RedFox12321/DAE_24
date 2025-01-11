package spz.dae24.ejbs;

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

    public List<Package> findAll() {return em.createNamedQuery("getAllPackages", Package.class).getResultList();}

    public List<Package> findByStatus(String status) {
        List<Package> packages = em.createNamedQuery("getAllPackages", Package.class).getResultList();

        for (Package p : packages) {
            if(!p.getStatus().equals(Status.valueOf(status.toUpperCase()))){
                packages.remove(p);
            }
        }

        return packages;
    }

    public List<Package> findByClient(int clientId) throws EntityNotFoundException {
        List<Package> packages = em.createNamedQuery("getAllPackages", Package.class).getResultList();

        var client = em.find(Client.class, clientId);

        if(client == null)
            throw new EntityNotFoundException("Client with id " + clientId + " not found");

        for (Package p : packages) {
            if(!p.getClient().equals(client)){
                packages.remove(p);
            }
        }

        return packages;
    }

    public Package find(long code) throws EntityNotFoundException {
        var _package = em.find(Package.class, code);

        if(_package == null)
            throw new EntityNotFoundException("Package with code " + code + " not found");

        Hibernate.initialize(_package.getVolumes());

        return _package;
    }

    public void create(){

    }
}
