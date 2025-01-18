package spz.dae24.common.enums;

public enum PackageType {
    BOX("Box"),
    FREEZER("Freezer");

    private final String name;
    private static final PackageType[] values = values();

    PackageType(String name) {this.name = name;}

    public String getName() {return name;}

    public static PackageType parse(String packageType) throws IllegalArgumentException {
        try {
            return PackageType.valueOf(packageType.toUpperCase());
        } catch(IllegalArgumentException exception) {
            StringBuilder msg = new StringBuilder("Package type " + packageType + " not found. Possible values: ");

            int i = 1;
            for (PackageType packageTypeEnum : values) {
                msg.append(packageTypeEnum.name());
                if (i < values.length)
                    msg.append(", ");
            }

            throw new IllegalArgumentException(msg.toString());
        }
    }
}
