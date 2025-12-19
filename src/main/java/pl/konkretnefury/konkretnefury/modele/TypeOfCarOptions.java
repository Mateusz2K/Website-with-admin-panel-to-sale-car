package pl.konkretnefury.konkretnefury.modele;

public enum TypeOfCarOptions {
    BEZPIECZENSTWO("Bezpieczeństwo"),
    KOMFORT_I_DODOATKI("Komfort i dodatki"),
    MULTIMEDIA("Audio i multimedia"),
    WYGLAD_ZEWNETRZNY("Wygląd zewnętrzny"),
    OSWIETLENIE("Oświetlenie"),
    NADWOZIE("Nadwozie"),
    INNE("Inne");

    private final String displayName;

    TypeOfCarOptions(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
