package spz.dae24.ejbs;

import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import spz.dae24.entities.Product;
import spz.dae24.entities.ProductsVolume;
import spz.dae24.entities.Volume;
import spz.dae24.exceptions.MyEntityNotFoundException;

import java.util.List;

@Stateless
public class ProductsVolumeBean {

    @PersistenceContext
    private EntityManager em;

    public List<ProductsVolume> findAll() {
        return em.createNamedQuery("getAllProductsVolume", ProductsVolume.class).getResultList();
    }


    public ProductsVolume find(long id) throws MyEntityNotFoundException {
        var productsVolume = em.find(ProductsVolume.class, id);

        if (productsVolume == null)
            throw new MyEntityNotFoundException("ProductsVolume with id " + id + " not found");

        return productsVolume;
    }

    public List<ProductsVolume> findByProductCode(int productCode) throws MyEntityNotFoundException {

        TypedQuery<ProductsVolume> query = em.createNamedQuery("getAllProductsVolumeByProductCode", ProductsVolume.class);
        query.setParameter("productCode", productCode);
        List<ProductsVolume> productsVolume = query.getResultList();

        if (productsVolume.isEmpty()) {
            throw new MyEntityNotFoundException("No ProductsVolume found for product code " + productCode);
        }

        return productsVolume;
    }

    public List<ProductsVolume> findByVolumeCode(long volumeCode) throws MyEntityNotFoundException {

        TypedQuery<ProductsVolume> query = em.createNamedQuery("getAllProductsVolumeByVolumeCode", ProductsVolume.class);
        query.setParameter("volumeCode", volumeCode);
        List<ProductsVolume> productsVolume = query.getResultList();

        if (productsVolume.isEmpty()) {
            throw new MyEntityNotFoundException("No ProductsVolume found for volume code " + volumeCode);
        }

        return productsVolume;
    }

    public long create(int productCode, long volumeCode, int quantity) throws MyEntityNotFoundException, IllegalArgumentException {
        if (quantity < 1)
            throw new IllegalArgumentException("The quantity of products needs to be greater than 0.");

        Product product = em.find(Product.class, productCode);
        if (product == null)
            throw new MyEntityNotFoundException("Product with code " + productCode + " not found");

        Volume volume = em.find(Volume.class, volumeCode);
        if (volume == null)
            throw new MyEntityNotFoundException("Volume with code " + volumeCode + " not found");

        ProductsVolume productsVolume = new ProductsVolume(product, volume, quantity);

        em.persist(productsVolume);

        product.addProductsVolumes(productsVolume);
        volume.addProductsVolume(productsVolume);

        return productsVolume.getId();
    }
}
