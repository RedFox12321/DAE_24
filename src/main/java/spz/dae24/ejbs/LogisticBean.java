package spz.dae24.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import spz.dae24.entities.Logistic;
import spz.dae24.exceptions.MyEntityExistsException;
import spz.dae24.exceptions.MyEntityNotFoundException;
import spz.dae24.security.Hasher;

@Stateless
public class LogisticBean {

    @PersistenceContext
    private EntityManager em;

    private final Hasher hasher = new Hasher();

    public Logistic find(String username) throws MyEntityNotFoundException {
        var admin = em.find(Logistic.class, username);
        if (admin == null)
            throw new MyEntityNotFoundException("Logistic personnel with username " + username + " not found");

        return admin;
    }

    public void create(String username, String name, String email, String password) throws MyEntityExistsException {
        if (exists(username))
            throw new MyEntityExistsException("Logistic personnel with username " + username + " already exists");

        em.persist(new Logistic(username, name, email, hasher.hash(password)));
    }

    public boolean exists(String username) {
        return em.find(Logistic.class, username) != null;
    }
}
