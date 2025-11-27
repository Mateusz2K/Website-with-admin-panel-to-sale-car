package pl.konkretnefury.konkretnefury.modele;

public enum RodzajNadwozia {
    SEDAN("Sedan"),
    SUV("SUV"),
    MINIVAN("Minivan"),
    KOMPAKT("Kompakt"),
    HATCHBACK("Hatchback"),
    KABRIOLET("Kabriolet"),
    COUPE("Coupe"),
    AUTA_MIEJSKIE("Auta Miejskie"),
    AUTA_MALE("Auta Małe");
    private final String displayName;

    RodzajNadwozia(String displayName) {
        this.displayName = displayName;
    }
    public String getDisplayName() {
        return displayName;
    }








}
