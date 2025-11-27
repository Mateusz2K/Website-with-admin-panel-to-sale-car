package pl.konkretnefury.konkretnefury.dto.carOffer;

import java.math.BigDecimal;
import java.util.List;

public class CarOfferReciveDTO {
    private int id;
    private String marka;
    private String model;
    private int rok;
    private BigDecimal cena;
    private String przebieg;
    private String liczba_drzwi;
    private String Status;
    private List<String> zdjecia;
    private String opis;

    public CarOfferReciveDTO() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMarka() {
        return marka;
    }

    public void setMarka(String marka) {
        this.marka = marka;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getRok() {
        return rok;
    }

    public void setRok(int rok) {
        this.rok = rok;
    }

    public BigDecimal getCena() {
        return cena;
    }

    public void setCena(BigDecimal cena) {
        this.cena = cena;
    }

    public String getPrzebieg() {
        return przebieg;
    }

    public void setPrzebieg(String przebieg) {
        this.przebieg = przebieg;
    }

    public String getLiczba_drzwi() {
        return liczba_drzwi;
    }

    public void setLiczba_drzwi(String liczba_drzwi) {
        this.liczba_drzwi = liczba_drzwi;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public List<String> getZdjecia() {
        return zdjecia;
    }

    public void setZdjecia(List<String> zdjecia) {
        this.zdjecia = zdjecia;
    }
}

