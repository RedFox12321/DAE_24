package spz.dae24.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import spz.dae24.entities.Product;
import spz.dae24.entities.ProductsVolume;

import java.util.List;

@Stateless
public class ProductsVolumeBean {

    @PersistenceContext
    private EntityManager em;

    public List<ProductsVolume> findAll() {
        return em.createNamedQuery("getAllProductsVolume", ProductsVolume.class).getResultList();
    }


    public ProductsVolume find(long id) {
        var productsVolume = em.find(ProductsVolume.class, id);

        if (productsVolume == null)
            throw new EntityNotFoundException("ProductsVolume with id " + id + " not found");

        return productsVolume;
    }

    public void create(long id, Product product, int quantity) throws EntityExistsException {
        if (exists(id))
            throw new EntityExistsException("ProductsVolume with id " + id + " already exists");

        Product productManaged = em.merge(product);
        //Falta adicionar o volume
        em.persist(new ProductsVolume(id, productManaged, quantity));
    }

    public boolean exists(long id) {
        return em.find(ProductsVolume.class, id) != null;
    }

}
