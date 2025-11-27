package pl.konkretnefury.konkretnefury.modele;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
public class CarOption {
    @Id
    @GeneratedValue
    private UUID id;

    private String name;

    @Enumerated(EnumType.STRING)
    private TypeOfCarOptions category;

    // Getters and Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public TypeOfCarOptions getCategory() { return category; }
    public void setCategory(TypeOfCarOptions category) { this.category = category; }
}
