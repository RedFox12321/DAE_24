package spz.dae24.ejbs;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import spz.dae24.common.enums.SensorType;
import spz.dae24.entities.Product;

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

    @PostConstruct
    public void populateDB() {
        LOGGER.info("Initiating database seeding");
        populateProducts();
        populatePackages();
        populateVolumes();
        populateProductsVolume();
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

    }

    public void populateVolumes(){

    }

    public void populateProductsVolume(){

        try {
        } catch (Exception e) {
        }

    }

}
