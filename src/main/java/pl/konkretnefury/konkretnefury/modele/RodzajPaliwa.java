package pl.konkretnefury.konkretnefury.modele;

public enum RodzajPaliwa {
    BENZYNA("Benzyna"),
    BENZYNA_CNG("Benzyna+CNG"),
    BENZYNA_LPG("Benzyna+LPG"),
    DIESEL("Diesel"),
    HYBRYDA("Hybryda"),
    ELEKTRYCZNY("Elektryczny");
    private final String displayName;
    //TODO: HYBRYDA

    RodzajPaliwa(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
