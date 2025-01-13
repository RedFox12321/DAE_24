package spz.dae24.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import spz.dae24.entities.Client;
import spz.dae24.security.Hasher;

import java.util.List;


@Stateless
public class ClientBean {

    @PersistenceContext
    private EntityManager em;

    private final Hasher hasher = new Hasher();

    public Client find(long id) throws EntityNotFoundException {
        var client = em.find(Client.class, id);
        if (client == null)
            throw new EntityNotFoundException("Client with id " + id + " not found");

        return client;
    }

    public List<Client> findAll() {
        return em.createNamedQuery("getAllClients", Client.class).getResultList();
    }

    public void create(int id, String username, String name, String email, String password) throws EntityExistsException {
        if (exists(id))
            throw new EntityExistsException("Client with id " + id + " already exists");

        em.persist(new Client(id, username, name, email, hasher.hash(password)));
    }

    public boolean exists(long id) {
        return em.find(Client.class, id) != null;
    }
}
