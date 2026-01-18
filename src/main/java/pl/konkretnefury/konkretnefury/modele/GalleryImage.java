package pl.konkretnefury.konkretnefury.modele;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import java.util.UUID;

@Entity
public class GalleryImage {
    @Id
    @GeneratedValue
    private UUID id;

    private String fileName;
    private int displayOrder;

    public GalleryImage() {}

    public GalleryImage(String fileName, int displayOrder) {
        this.fileName = fileName;
        this.displayOrder = displayOrder;
    }

    // Getters and Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public String getFileName() { return fileName; }
    public void setFileName(String fileName) { this.fileName = fileName; }
    public int getDisplayOrder() { return displayOrder; }
    public void setDisplayOrder(int displayOrder) { this.displayOrder = displayOrder; }
}
