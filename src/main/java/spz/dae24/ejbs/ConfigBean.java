package spz.dae24.ejbs;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import spz.dae24.common.enums.PackageType;
import spz.dae24.common.enums.SensorType;
import spz.dae24.common.enums.Status;

import java.util.*;
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
    @EJB
    private ClientBean clientBean;
    @EJB
    private AdminBean adminBean;

    // create test objects
    private List<String> clientUsernames = new ArrayList<>();
    private int currentVolumeCode = 1;
    private int currentSensorCode = 1;


    List<PackageType> PACKAGE_VALUES = List.of(PackageType.values());

    @PostConstruct
    public void populateDB() {
        LOGGER.info("Initiating database seeding");
        populateProducts();
        populateClients();
        populatePackages();

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
                "Carne Bovina",
                "Televisão BSUS 16:9"
        );

        int code = 100;
        List<String> sensors = new ArrayList<>();
        for (String productName : productNames) {
            try {
                switch (code) {
                    case 100 -> sensors.add(SensorType.ATMOSPHERIC_PRESSURE.name());
                    case 103, 105, 107, 108 -> sensors.add(SensorType.TEMPERATURE.name());
                    case 112 -> {
                        sensors.add(SensorType.ACCELERATION.name());
                        sensors.add(SensorType.GPS.name());
                    }
                }
                productBean.create(code++, productName, sensors);
            } catch (Exception e) {
                LOGGER.warning("While creating products: " + e.getMessage());
            }
            sensors.clear();
        }
    }

    public void populatePackages(){
        Random random = ThreadLocalRandom.current();
        for (int i = 1; i < 4; i++) {
            try{
                packageBean.create(i, clientUsernames.get(random.nextInt(clientUsernames.size())));
                populateWithVolumes(i);
                packageBean.completePackage(i);
            }
            catch (Exception e){
                LOGGER.warning("While creating packages: " + e.getMessage());
            }

        }
    }

    public void populateWithHistory(long sensorId, SensorType type) {
        int minimumValueRegisters = 7;
        Random random = ThreadLocalRandom.current();

        String value = "";

        switch (type) {
            case TEMPERATURE -> value = String.format("%.2f", random.nextDouble(-10, 40));
            case GPS -> value = String.format("%.6f,%.6f", random.nextDouble(), random.nextDouble());
            case ACCELERATION -> value = String.format("%.2f", random.nextDouble(0, 10));
            case ATMOSPHERIC_PRESSURE -> value = String.format("%.2f", random.nextDouble(950, 1050));
        }
        for (int i = 0; i < minimumValueRegisters + random.nextInt(20); i++) {
            sensorHistoryBean.create(sensorId, value);
            switch (type) {
                case TEMPERATURE -> value = String.format("%.2f", Math.max(-10, Math.min(40, Double.parseDouble(value) + random.nextDouble(-1, 1))));
                case GPS -> {
                    String[] oldGpsValues = value.split(",");
                    double[] newGpsValues = new double[2];
                    for (int j = 0; j < 2; j++) {
                        newGpsValues[j] = Math.max(-180, Math.min(180, Double.parseDouble(oldGpsValues[j]) + random.nextDouble(-0.01, 0.01)));
                    }
                    value = String.format("%.6f,%.6f", newGpsValues[0], newGpsValues[1]);
                }
                case ACCELERATION -> value = String.format("%.2f", Math.max(0, Math.min(10, Double.parseDouble(value) + random.nextDouble(-0.1, 0.1))));
                case ATMOSPHERIC_PRESSURE -> value = String.format("%.2f", Math.max(950, Math.min(1050, Double.parseDouble(value) + random.nextDouble(-0.5, 0.5))));
            }
        }
    }

    public void populateWithSensors(long volumeCode, PackageType packageType, List<Integer> productsUsed){
        Random random = ThreadLocalRandom.current();
        try {
            for (Integer productUsed : productsUsed) {
                switch (productUsed) {
                    case 100 -> {
                        sensorBean.create(currentSensorCode, SensorType.ATMOSPHERIC_PRESSURE.name(), volumeCode);
                        populateWithHistory(currentSensorCode, SensorType.ATMOSPHERIC_PRESSURE);
                        sensorBean.disable(currentSensorCode++);
                    }
                    case 103, 105, 107, 108 -> {
                        sensorBean.create(currentSensorCode, SensorType.TEMPERATURE.name(), volumeCode);
                        populateWithHistory(currentSensorCode, SensorType.TEMPERATURE);
                        sensorBean.disable(currentSensorCode++);
                    }
                    case 112 -> {
                        sensorBean.create(currentSensorCode, SensorType.ACCELERATION.name(), volumeCode);
                        populateWithHistory(currentSensorCode, SensorType.ACCELERATION);
                        sensorBean.disable(currentSensorCode++);

                        sensorBean.create(currentSensorCode, SensorType.GPS.name(), volumeCode);
                        populateWithHistory(currentSensorCode, SensorType.GPS);
                        sensorBean.disable(currentSensorCode++);
                    }
                }
            }

            if (packageType == PackageType.FREEZER) {
                sensorBean.create(currentSensorCode, SensorType.TEMPERATURE.name(), volumeCode);
                populateWithHistory(currentSensorCode, SensorType.TEMPERATURE);
                sensorBean.disable(currentSensorCode++);
            }

            for (int i = 0; i < random.nextInt(2); i++) {
                switch (random.nextInt(3)) {
                    case 0 -> {
                        sensorBean.create(currentSensorCode, SensorType.GPS.name(), volumeCode);
                        populateWithHistory(currentSensorCode, SensorType.GPS);
                        sensorBean.disable(currentSensorCode++);
                    }
                    case 1 -> {
                        sensorBean.create(currentSensorCode, SensorType.ACCELERATION.name(), volumeCode);
                        populateWithHistory(currentSensorCode, SensorType.ACCELERATION);
                        sensorBean.disable(currentSensorCode++);
                    }
                    case 2 -> {
                        sensorBean.create(currentSensorCode, SensorType.ATMOSPHERIC_PRESSURE.name(), volumeCode);
                        populateWithHistory(currentSensorCode, SensorType.ATMOSPHERIC_PRESSURE);
                        sensorBean.disable(currentSensorCode++);
                    }
                }
            }
        } catch (Exception e) {
           LOGGER.warning("While creating sensors: " + e.getMessage());
        }
    }

    public void populateWithVolumes(long packageCode) {
        int minimumNumberOfVolumes = 1;
        Random random = ThreadLocalRandom.current();
        try {
            List<Integer> productCodes = new ArrayList<>();
            for (int i = 0; i < minimumNumberOfVolumes + random.nextInt(3); i++) {
                PackageType type = PACKAGE_VALUES.get(random.nextInt(PACKAGE_VALUES.size()));

                volumeBean.create(currentVolumeCode, type.name(), packageCode);
                productCodes = populateProductsVolume(currentVolumeCode);
                populateWithSensors(currentVolumeCode, type, productCodes);

                volumeBean.updateStatus(currentVolumeCode++, Status.DELIVERED.name());
            }
        } catch (Exception e) {
            LOGGER.warning("While creating volumes: " + e.getMessage());
        }
    }

    public List<Integer> populateProductsVolume(long volumeCode) {
        int minimumNumberOfProducts = 1;
        Random random = ThreadLocalRandom.current();
        List<Integer> products = new ArrayList<>();

        try {
            for (int i = 0; i < minimumNumberOfProducts + random.nextInt(3); i++) {
                int productCode = random.nextInt(100, 112);
                productsVolumeBean.create(productCode, random.nextInt(1, 10), volumeCode);
                products.add(productCode);
            }
        } catch (Exception e) {
            LOGGER.warning("While creating product volume: " + e.getMessage());
        }

        return products;
    }

    public void populateClients() {

        List<String[]> clients = List.of(
                new String[]{"clt1", "João Manuel Silva", "c1@ipleiria.pt"},
                new String[]{"clt2", "Maria Oliveira", "c2@ipleiria.pt"},
                new String[]{"clt3", "António Luís Pereira", "c3@ipleiria.pt"},
                new String[]{"clt4", "José Fernandes", "c4@ipleiria.pt"},
                new String[]{"clt5", "Ana Beatriz Costa", "c5@ipleiria.pt"},
                new String[]{"pamartins", "Pedro Afonso Martins", "pedro.martins@example.com"},
                new String[]{"cssousa", "Catarina Sofia Sousa", "catarina.sousa@example.com"},
                new String[]{"lgoncalves", "Luís Gonçalves", "luis.goncalves@example.com"},
                new String[]{"rmendes", "Rita Maria Mendes", "rita.mendes@example.com"},
                new String[]{"mjrocha", "Manuel Joaquim Rocha", "manuel.rocha@example.com"},
                new String[]{"salves", "Sofia Alves", "sofia.alves@example.com"},
                new String[]{"bmsantos", "Bruno Miguel Santos", "bruno.santos@example.com"},
                new String[]{"iccarvalho", "Inês Catarina Carvalho", "ines.carvalho@example.com"}
        );

        List<String[]> admins = List.of(
                new String[]{"adm1", "João Manuel Almeida", "a1@ipleiria.pt"},
                new String[]{"adm2", "Maria Costa", "a2@ipleiria.pt"},
                new String[]{"apereira", "António Luís Pereira", "antonio.pereira@example.com"},
                new String[]{"rsmendes", "Rita Sofia Mendes", "rita.mendes@example.com"},
                new String[]{"mrocha", "Manuel Rocha", "manuel.rocha@example.com"}
        );

        try {
            for (String[] admin : admins) {
                adminBean.create(admin[0], admin[1], admin[2], "123");
            }
            for (String[] client : clients) {
                clientBean.create(client[0], client[1], client[2], "123");
                clientUsernames.add(client[0]);
            }
        } catch (Exception e) {
            LOGGER.warning("While creating clients: " + e.getMessage());
        }
    }
}
