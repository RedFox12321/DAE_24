package spz.dae24.common.enums;

public enum Status {
    ACTIVE,
    CANCELLED,
    DELIVERED;

    private static final Status[] values = Status.values();

    public static Status parse(String status) throws IllegalArgumentException {
        try {
            return Status.valueOf(status.toUpperCase());
        } catch(IllegalArgumentException exception) {
            StringBuilder msg = new StringBuilder("Status " + status + " not found. Possible values: ");

            int i = 1;
            for (Status statusEnum : values) {
                msg.append(statusEnum.name());
                if (i < values.length)
                    msg.append(", ");
            }

            throw new IllegalArgumentException(msg.toString());
        }
    }
}
