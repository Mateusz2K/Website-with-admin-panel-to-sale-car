package pl.konkretnefury.konkretnefury.modele;

public enum TypeOfCarOptions {
    BEZPIECZENSTWO("Bezpieczeństwo"),
    KOMFORT("Komfort"),
    MULTIMEDIA("Multimedia"),
    WYGLAD_ZEWNETRZNY("Wygląd zewnętrzny"),
    INNE("Inne");

    private final String displayName;

    TypeOfCarOptions(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
