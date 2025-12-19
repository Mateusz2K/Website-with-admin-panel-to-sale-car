package pl.konkretnefury.konkretnefury.modele;

public enum Naped {
    NA_PRZEDNIE_KOLA("Na przednie koła"),
    NA_TYLNE_KOLA("Na tylne koła"),
    AWD("4x4 (dołączany automatycznie)"),
    STAŁY_4X4("4x4 (stały)");

    private final String displayName;

    Naped(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
