package pl.konkretnefury.konkretnefury.modele;

public enum RodzajNadwozia {
    SEDAN("Sedan"),
    SUV("SUV"),
    MINIVAN("Minivan"),
    TERENOWE("Terenowe"),
    HATCHBACK("Hatchback"),
    KABRIOLET("Kabriolet"),
    COUPE("Coupe"),
    KOMBI("Kombi");
//    AUTA_MIEJSKIE("Auta Miejskie"),
//    AUTA_MALE("Auta Małe");
    private final String displayName;
    //TODO: dodać nadwozie do filtrów
    RodzajNadwozia(String displayName) {
        this.displayName = displayName;
    }
    public String getDisplayName() {
        return displayName;
    }








}
