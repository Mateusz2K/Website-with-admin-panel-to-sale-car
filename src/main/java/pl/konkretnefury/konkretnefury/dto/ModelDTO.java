package pl.konkretnefury.konkretnefury.dto;

import java.util.UUID;

// Prosty DTO do przesyłania danych o modelu do frontendu
public class ModelDTO {
    private UUID id;
    private String name;

    public ModelDTO(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    // Gettery są niezbędne do serializacji JSON
    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
