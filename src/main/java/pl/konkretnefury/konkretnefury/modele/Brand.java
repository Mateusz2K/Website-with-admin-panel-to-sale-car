package pl.konkretnefury.konkretnefury.modele;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "brand")
public class Brand {

    @Id
    @GeneratedValue
    private UUID id;
    @NotNull
    private String nazwaMarki;

    private String iconUrl;

    @OneToMany(mappedBy = "brand")
    private List<BrandsModel> lista_modeli = new ArrayList<>();

    public Brand() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNazwaMarki() {
        return nazwaMarki;
    }

    public void setNazwaMarki(String nazwaMarki) {
        this.nazwaMarki = nazwaMarki;
    }

    public List<BrandsModel> getLista_modeli() {
        return lista_modeli;
    }

    public void setLista_modeli(List<BrandsModel> lista_modeli) {
        this.lista_modeli = lista_modeli;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }
}
