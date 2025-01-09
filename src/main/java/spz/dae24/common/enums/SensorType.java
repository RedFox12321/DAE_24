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
}
