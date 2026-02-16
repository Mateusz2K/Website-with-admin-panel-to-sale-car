package pl.konkretnefury.konkretnefury.modele;

public enum TypPojazdu {
    OSOBOWY("Osobowe"),
    DOSTAWCZY("Dostawcze");

    private final String displayName;

    TypPojazdu(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
