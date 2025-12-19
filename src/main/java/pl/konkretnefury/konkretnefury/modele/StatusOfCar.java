package pl.konkretnefury.konkretnefury.modele;

public enum StatusOfCar {
    DOSTĘPNY("Dostępny"),
    ZAREZERWOWANY("Zarezerwowany"),
    SPRZEDANY("Sprzedany"),
    WKROTCE("Wkrótce w ofercie");
    //TODO: dodać "Wkrótce w ofercie" i zdjęcie dedykowane do tego statusu

    private final String displayName;

    StatusOfCar(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
