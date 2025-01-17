package spz.dae24.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import spz.dae24.entities.Product;
import spz.dae24.entities.ProductsVolume;
import spz.dae24.entities.Volume;

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

    public void create(int productCode, long volumeCode, int quantity) throws EntityNotFoundException {
        Product product = em.find(Product.class, productCode);
        if (product == null)
            throw new EntityNotFoundException("Product with code " + productCode + " not found");

        Volume volume = em.find(Volume.class, volumeCode);
        if (volume == null)
            throw new EntityNotFoundException("Volume with code " + volumeCode + " not found");

        ProductsVolume productsVolume = new ProductsVolume(product, volume, quantity);

        em.persist(productsVolume);

        product.addProductsVolumes(productsVolume);
        volume.addProductsVolume(productsVolume);
    }
}
