package spz.dae24.common.enums;


import java.util.List;
import java.util.stream.Collectors;

public enum SensorType {
    TEMPERATURE("Temperature"),
    ATMOSPHERIC_PRESSURE("Atmospheric pressure"),
    ACCELERATION("Acceleration"),
    GPS("Geographical Location");

    private final String description;
    private static final SensorType[] values = values();

    SensorType(String description) {
        this.description = description;
    }

    public String getName() {
        return description;
    }

    public static SensorType parse(String sensorType) throws IllegalArgumentException {
        try {
            return SensorType.valueOf(sensorType.toUpperCase());
        } catch(IllegalArgumentException exception) {
            StringBuilder msg = new StringBuilder("Sensor type " + sensorType + " not found. Possible values: ");

            int i = 1;
            for (SensorType sensorTypeEnum : values) {
                msg.append(sensorTypeEnum.name());
                if (i < values.length)
                    msg.append(", ");
            }

            throw new IllegalArgumentException(msg.toString());
        }
    }

    public static List<SensorType> parse(List<String> sensorTypes) throws IllegalArgumentException {
        return sensorTypes.stream().map(SensorType::parse).collect(Collectors.toList());
    }
}
