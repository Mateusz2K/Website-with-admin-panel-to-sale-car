package pl.konkretnefury.konkretnefury.modele;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "brands_model")
public class BrandsModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String model;

    @ManyToOne
    @JoinColumn(name = "brand_id")
    private Brand brand;

    public BrandsModel() {
    }

    public UUID getId() {
        return id;
    }


    public void setId(UUID id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }
}
