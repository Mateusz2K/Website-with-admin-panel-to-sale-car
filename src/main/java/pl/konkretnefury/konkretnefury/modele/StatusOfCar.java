package pl.konkretnefury.konkretnefury.modele;

public enum StatusOfCar {
    DOSTĘPNY("Dostępny"),
    ZAREZERWOWANY("Zarezerwowany"),
    SPRZEDANY("Sprzedany");

    private final String displayName;

    StatusOfCar(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
