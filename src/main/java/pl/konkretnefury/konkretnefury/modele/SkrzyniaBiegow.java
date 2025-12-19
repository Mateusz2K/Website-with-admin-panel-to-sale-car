package pl.konkretnefury.konkretnefury.modele;

public enum SkrzyniaBiegow {
    MANUALNA("Manualna"),
    AUTOMATYCZNA("Automatyczna");

    private final String displayName;

    SkrzyniaBiegow(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
