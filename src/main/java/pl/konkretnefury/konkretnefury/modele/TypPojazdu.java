package pl.konkretnefury.konkretnefury.modele;

public enum TypPojazdu {
    OSOBOWY("Osobowy"),
    DOSTAWCZY("Dostawczy do 3.5t");

    private final String displayName;

    TypPojazdu(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
