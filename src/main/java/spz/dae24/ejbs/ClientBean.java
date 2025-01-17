package spz.dae24.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Hibernate;
import spz.dae24.entities.Client;
import spz.dae24.exceptions.EntityExistsException;
import spz.dae24.exceptions.EntityNotFoundException;
import spz.dae24.security.Hasher;

import java.util.List;

@Stateless
public class ClientBean {

    @PersistenceContext
    private EntityManager em;

    private final Hasher hasher = new Hasher();

    public Client find(String username) throws EntityNotFoundException {
        var client = em.find(Client.class, username);
        if (client == null)
            throw new EntityNotFoundException("Client with username " + username + " not found");

        return client;
    }

    public Client findWithPackages(String username) throws EntityNotFoundException {
        var client = find(username);

       Hibernate.initialize(client.getPackages());

        return client;
    }

    public List<Client> findAll() {
        return em.createNamedQuery("getAllClients", Client.class).getResultList();
    }

    public void create(String username, String name, String email, String password) throws EntityExistsException {
        if (exists(username))
            throw new EntityExistsException("Client with username " + username + " already exists");

        em.persist(new Client(username, name, email, hasher.hash(password)));
    }

    public boolean exists(String username) {
        return em.find(Client.class, username) != null;
    }
}
