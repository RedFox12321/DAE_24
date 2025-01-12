package spz.dae24.ejbs;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import spz.dae24.common.enums.PackageType;
import spz.dae24.common.enums.SensorType;
import spz.dae24.dtos.ProductsVolumeDTO;
import spz.dae24.dtos.VolumeDTO;
import spz.dae24.entities.Product;
import spz.dae24.entities.ProductsVolume;
import spz.dae24.entities.Volume;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Logger;

@Startup
@Singleton
public class ConfigBean {

    private static final Logger LOGGER = Logger.getLogger("ejbs.ConfigBean");
    @EJB
    private SensorBean sensorBean;
    @EJB
    private SensorHistoryBean sensorHistoryBean;
    @EJB
    private ProductBean productBean;
    @EJB
    private ProductsVolumeBean productsVolumeBean;
    @EJB
    private VolumeBean volumeBean;
    @EJB
    private PackageBean packageBean;

    @PostConstruct
    public void populateDB() {
        LOGGER.info("Initiating database seeding");
        populateProducts();
        populateProductsVolume();
        populatePackages();
        populateVolumes();

        LOGGER.info("Database seeding complete");
    }

    public void populateProducts(){
        List<String> productNames = List.of(
                "Arroz",
                "Feijão dos altos mares",
                "Massa perfeita",
                "Leite da Majéstica Força Ginyu",
                "Café da manhã do Sanji",
                "Iogurte da Prozis",
                "Farinha de Trigo",
                "Ovos da Hornet do Cavaleiro Vazio",
                "Manteiga da Perfeição",
                "Queijo do Soldado Invernal",
                "Presunto da 2B",
                "Carne Bovina"
        );

        for (int i = 0; i < productNames.size(); i++) {
            String productName = productNames.get(i);
            int code = 100 + i;

            try {
                productBean.create(code, productName, null);
            } catch (Exception e) {
                LOGGER.warning("While creating products: " + e.getMessage());
            }
        }
    }

    public void populatePackages(){
        try{
            packageBean.create(10);
            packageBean.create(20);
            packageBean.create(30);
        }
        catch (Exception e){
            LOGGER.warning("While creating packages: " + e.getMessage());
        }
    }

    public void populateVolumes(){
        try {
            volumeBean.create(PackageType.BOX.name(), 1, null);
            volumeBean.create(PackageType.FREEZER.name(), 2, null);
        } catch (Exception e) {
            LOGGER.warning("While creating volumes: " + e.getMessage());
        }
    }

    public void populateProductsVolume(){
        try {
            productsVolumeBean.create(productBean.find(100), 5, volumeBean.find(1));
            productsVolumeBean.create(productBean.find(101), 3, volumeBean.find(1));
            productsVolumeBean.create(productBean.find(102), 4, volumeBean.find(2));
        } catch (Exception e) {
            LOGGER.warning("While creating product volume: " + e.getMessage());
        }
    }

}
