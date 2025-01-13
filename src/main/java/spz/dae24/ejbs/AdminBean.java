package spz.dae24.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import spz.dae24.entities.Admin;
import spz.dae24.security.Hasher;

@Stateless
public class AdminBean {

    @PersistenceContext
    private EntityManager em;

    private final Hasher hasher = new Hasher();

    public Admin find(long id) throws EntityNotFoundException {
        var admin = em.find(Admin.class, id);
        if (admin == null)
            throw new EntityNotFoundException("Admin with id " + id + " not found");

        return admin;
    }

    public void create(int id, String username, String name, String email, String password) throws EntityExistsException {
        if (exists(id))
            throw new EntityExistsException("Admin with id " + id + " already exists");

        em.persist(new Admin(id, username, name, email, hasher.hash(password)));
    }

    public boolean exists(long id) {
        return em.find(Admin.class, id) != null;
    }
}
