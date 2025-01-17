package spz.dae24.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import spz.dae24.entities.Logistic;
import spz.dae24.exceptions.EntityExistsException;
import spz.dae24.exceptions.EntityNotFoundException;
import spz.dae24.security.Hasher;

@Stateless
public class LogisticBean {

    @PersistenceContext
    private EntityManager em;

    private final Hasher hasher = new Hasher();

    public Logistic find(String username) throws EntityNotFoundException {
        var admin = em.find(Logistic.class, username);
        if (admin == null)
            throw new EntityNotFoundException("Logistic personnel with username " + username + " not found");

        return admin;
    }

    public void create(String username, String name, String email, String password) throws EntityExistsException {
        if (exists(username))
            throw new EntityExistsException("Logistic personnel with username " + username + " already exists");

        em.persist(new Logistic(username, name, email, hasher.hash(password)));
    }

    public boolean exists(String username) {
        return em.find(Logistic.class, username) != null;
    }
}
