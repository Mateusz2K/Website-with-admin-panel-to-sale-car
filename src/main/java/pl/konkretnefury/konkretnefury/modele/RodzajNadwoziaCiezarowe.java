package pl.konkretnefury.konkretnefury.modele;

public enum RodzajNadwoziaCiezarowe {
    FORGON("Forgon (Blaszak)"),
    SKRZYNIOWY("Skrzyniowy"),
    WYWROTKA("Wywrotka"),
    RAMA("Rama (do zabudowy)"),
    KONTENER("Kontener"),
    CHLODNIA("chłodnia"),
    INNE("Inne");
    private final String displayName;
    RodzajNadwoziaCiezarowe(String displayName) {
        this.displayName = displayName;
        }
    public String getDisplayName() {
        return displayName;
    }
}
