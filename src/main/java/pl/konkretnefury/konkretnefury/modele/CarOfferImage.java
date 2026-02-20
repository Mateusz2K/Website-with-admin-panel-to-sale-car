package pl.konkretnefury.konkretnefury.modele;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "car_offer_image")
public class CarOfferImage {
    @Id
    @GeneratedValue
    private UUID id;

    private String fileName;
    private boolean isMain;
    
    // ZMIANA: Dodano pole do sortowania
    private int displayOrder;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car_offer_id")
    private CarOffer carOffer;

    public CarOfferImage() {}

    // Getters and Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public String getFileName() { return fileName; }
    public void setFileName(String fileName) { this.fileName = fileName; }
    public boolean isMain() { return isMain; }
    public void setMain(boolean main) { isMain = main; }
    public CarOffer getCarOffer() { return carOffer; }
    public void setCarOffer(CarOffer carOffer) { this.carOffer = carOffer; }
    public int getDisplayOrder() { return displayOrder; }
    public void setDisplayOrder(int displayOrder) { this.displayOrder = displayOrder; }
}
