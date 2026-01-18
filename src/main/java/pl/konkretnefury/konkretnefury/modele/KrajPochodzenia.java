package pl.konkretnefury.konkretnefury.modele;

public enum KrajPochodzenia {
    POLSKA("Polska", "pl"),
    NIEMCY("Niemcy", "de"),
    FRANCJA("Francja", "fr"),
    BELGIA("Belgia", "be"),
    HOLANDIA("Holandia", "nl"),
    WLOCHY("Włochy", "it"),
    SZWAJCARIA("Szwajcaria", "ch"),
    AUSTRIA("Austria", "at"),
    SZWECJA("Szwecja", "se"),
    DANIA("Dania", "dk"),
    USA("USA", "us"),
    KANADA("Kanada", "ca"),
    JAPONIA("Japonia", "jp"),
    INNY("Inny", "un");

    private final String displayName;
    private final String flagCode;

    KrajPochodzenia(String displayName, String flagCode) {
        this.displayName = displayName;
        this.flagCode = flagCode;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getFlagCode() {
        return flagCode;
    }
}
