package pl.konkretnefury.konkretnefury.modele;

public enum Country {
    POLSKA("Polska"),    NIEMCY("Niemcy"),
    FRANCJA("Francja"),
    DANIA("Dania");
    public String getDisplayName() {
        return this.displayName;

    }
    private String displayName;
    Country(String displayName) {
        this.displayName = displayName;
    }
}
