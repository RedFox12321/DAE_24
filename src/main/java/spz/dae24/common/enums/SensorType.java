package spz.dae24.common.enums;


public enum SensorType {
    TEMPERATURE("Temperature"),
    ATMOSPHERIC_PRESSURE("Atmospheric pressure"),
    ACCELERATION("Acceleration"),
    GPS("Geographical Location");

    private final String description;

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

            SensorType[] values = SensorType.values();
            int i = 1;
            for (SensorType sensorTypeEnum : values) {
                msg.append(sensorTypeEnum.name());
                if (i < values.length)
                    msg.append(", ");
            }

            throw new IllegalArgumentException(msg.toString());
        }
    }
}
