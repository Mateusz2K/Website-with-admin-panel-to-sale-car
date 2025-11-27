package pl.konkretnefury.konkretnefury.modele;

public enum RodzajPaliwa {
    BENZYNA("Benzyna"),
    BENZYNA_CNG("Benzyna+CNG"),
    BENZYNA_LPG("Benzyna+LPG"),
    DIESEL("Diesel"),
    ELEKTRYCZNY("Elektryczny");
    private final String displayName;

    RodzajPaliwa(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
