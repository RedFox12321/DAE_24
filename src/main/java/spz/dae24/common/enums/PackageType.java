package spz.dae24.common.enums;

public enum PackageType {
    BOX("Box"),
    FREEZER("Freezer");

    private final String name;

    PackageType(String name) {this.name = name;}

    public String getName() {return name;}
}
