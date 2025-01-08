package spz.dae24.ejbs;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import spz.dae24.common.enums.SensorType;

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

    @PostConstruct
    public void populateDB() {
        LOGGER.info("Initiating database seeding");
        populateSensorsAndHistory();
        LOGGER.info("Database seeding complete");
    }

    // temporary creation of sensors for testing purposes
    // after volumes are added remake this function to only add history to pre-made sensors
    public void populateSensorsAndHistory() {
        int numberOfSensors = 13;
        int minimumValueRegisters = 10;
        Random random = ThreadLocalRandom.current();
        List<SensorType> types = List.of(SensorType.values());

        for (int i = 1; i < numberOfSensors + 1; i++) {
            SensorType sensorType = types.get(random.nextInt(types.size()));
            try {
                sensorBean.create(i, sensorType.name());

                String value = "";
                switch (sensorType) {
                    case TEMPERATURE -> value = String.format("%.2f", random.nextDouble(-10, 40));
                    case GPS -> value = String.format("%.6f,%.6f", random.nextDouble(), random.nextDouble());
                    case ACCELERATION -> value = String.format("%.2f", random.nextDouble(0, 10));
                    case ATMOSPHERIC_PRESSURE -> value = String.format("%.2f", random.nextDouble(950, 1050));
                }
                for (int j = 0; j < minimumValueRegisters + random.nextInt(20); j++) {
                    sensorHistoryBean.create(i, value);
                    switch (sensorType) {
                        case TEMPERATURE -> value = String.format("%.2f", Math.max(-10, Math.min(40, Double.parseDouble(value) + random.nextDouble(-1, 1))));
                        case GPS -> {
                            String[] oldGpsValues = value.split(",");
                            double[] newGpsValues = new double[2];
                            for (int k = 0; k < 2; k++) {
                                newGpsValues[k] = Math.max(-180, Math.min(180, Double.parseDouble(oldGpsValues[k]) + random.nextDouble(-0.01, 0.01)));
                                k++;
                            }
                            value = String.format("%.6f,%.6f", newGpsValues[0], newGpsValues[1]);
                        }
                        case ACCELERATION -> value = String.format("%.2f", Math.max(0, Math.min(10, Double.parseDouble(value) + random.nextDouble(-0.1, 0.1))));
                        case ATMOSPHERIC_PRESSURE -> value = String.format("%.2f", Math.max(950, Math.min(1050, Double.parseDouble(value) + random.nextDouble(-0.5, 0.5))));
                    }
                }
            } catch (Exception e) {
                LOGGER.warning("While creating sensors: " + e.getMessage());
            }
        }
    }
}
