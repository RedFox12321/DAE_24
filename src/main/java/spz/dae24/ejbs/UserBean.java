package spz.dae24.ejbs;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.Hibernate;
import spz.dae24.entities.User;
import spz.dae24.security.Hasher;

@Stateless
public class UserBean {
    @PersistenceContext
    private EntityManager em;
    @Inject
    private Hasher hasher;
    public User findOrFail(String username) {
        try {
            var user = em.find(User.class, username);

            Hibernate.initialize(user);

            return user;
        } catch (Exception e) {
            return null;
        }
    }
    public boolean canLogin(String username, String password) {
        var user = findOrFail(username);

        return user != null && user.getPassword().equals(hasher.hash(password));
    }

    public String findUserType(String username) {
        String sql = "SELECT type FROM users WHERE username=:username";

        return (String) em.createNativeQuery(sql)
        .setParameter("username", username)
        .getSingleResult();
    } 
}


