package spz.dae24.ejbs;

import jakarta.persistence.PersistenceContext;
import jakarta.persistence.EntityManager;
import spz.dae24.entities.Product;

import java.util.List;
public class ProductBean {

    @PersistenceContext
    private EntityManager em;

    public List<Product> findAll() {
        return em.createNamedQuery("getAllProducts", Product.class).getResultList();
    }

    public Product find(int code) {
        Product x = em.find(Product.class, code);

        return x;
    }
}
