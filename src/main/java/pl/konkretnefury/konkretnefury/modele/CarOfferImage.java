package pl.konkretnefury.konkretnefury.modele;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
public class CarOfferImage {
    @Id
    @GeneratedValue
    private UUID id;
    private String fileName;

    // NOWE POLE: Oznacza zdjęcie jako główne
    private boolean isMain = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car_offer_id")
    private CarOffer carOffer;

    // Getters and Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public String getFileName() { return fileName; }
    public void setFileName(String fileName) { this.fileName = fileName; }
    public boolean isMain() { return isMain; }
    public void setMain(boolean main) { isMain = main; }
    public CarOffer getCarOffer() { return carOffer; }
    public void setCarOffer(CarOffer carOffer) { this.carOffer = carOffer; }
}
