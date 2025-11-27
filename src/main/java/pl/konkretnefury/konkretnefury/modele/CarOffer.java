package pl.konkretnefury.konkretnefury.modele;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import pl.konkretnefury.konkretnefury.format.annotation.MileageFormat;
import pl.konkretnefury.konkretnefury.format.annotation.PowerFormat;
import pl.konkretnefury.konkretnefury.format.annotation.PriceFormat;
import pl.konkretnefury.konkretnefury.format.annotation.VolumeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "car_offer")
public class CarOffer {
    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne @JoinColumn(name = "brand_id") private Brand brand;
    @ManyToOne @JoinColumn(name = "model_id") private BrandsModel model;
    private int rok;
    
    // ZMIANA: Dodanie adnotacji formującej dla ceny
    @PriceFormat
    private BigDecimal cena;
    
    @MileageFormat
    private Integer przebieg;
    @PowerFormat
    private Integer moc;
    @VolumeFormat
    private Integer pojemonscSilnika;

    private String liczba_drzwi;
    @Enumerated(EnumType.STRING) private RodzajPaliwa rodzajPaliwa;
    @Enumerated(EnumType.STRING) private RodzajNadwozia rodzajNadwozia;
    @Lob private String opis;
    @Enumerated(EnumType.STRING) private StatusOfCar status;
    private boolean isFeatured = false;
    @CreationTimestamp private LocalDateTime creationDate;

    @OneToMany(mappedBy = "carOffer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CarOfferImage> zdjęcia = new ArrayList<>();

    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(name = "offer_options", joinColumns = @JoinColumn(name = "offer_id"), inverseJoinColumns = @JoinColumn(name = "option_id"))
    private Set<CarOption> options = new HashSet<>();

    public Optional<CarOfferImage> getMainImage() {
        return zdjęcia.stream().filter(CarOfferImage::isMain).findFirst();
    }
    
    // Getters and Setters...
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public Brand getBrand() { return brand; }
    public void setBrand(Brand brand) { this.brand = brand; }
    public BrandsModel getModel() { return model; }
    public void setModel(BrandsModel model) { this.model = model; }
    public int getRok() { return rok; }
    public void setRok(int rok) { this.rok = rok; }
    public BigDecimal getCena() { return cena; }
    public void setCena(BigDecimal cena) { this.cena = cena; }
    public String getLiczba_drzwi() { return liczba_drzwi; }
    public void setLiczba_drzwi(String liczba_drzwi) { this.liczba_drzwi = liczba_drzwi; }
    public RodzajPaliwa getRodzajPaliwa() { return rodzajPaliwa; }
    public void setRodzajPaliwa(RodzajPaliwa rodzajPaliwa) { this.rodzajPaliwa = rodzajPaliwa; }
    public RodzajNadwozia getRodzajNadwozia() { return rodzajNadwozia; }
    public void setRodzajNadwozia(RodzajNadwozia rodzajNadwozia) { this.rodzajNadwozia = rodzajNadwozia; }
    public String getOpis() { return opis; }
    public void setOpis(String opis) { this.opis = opis; }
    public StatusOfCar getStatus() { return status; }
    public void setStatus(StatusOfCar status) { this.status = status; }
    public boolean isFeatured() { return isFeatured; }
    public void setFeatured(boolean featured) { isFeatured = featured; }
    public LocalDateTime getCreationDate() { return creationDate; }
    public void setCreationDate(LocalDateTime creationDate) { this.creationDate = creationDate; }
    public List<CarOfferImage> getZdjęcia() { return zdjęcia; }
    public void setZdjęcia(List<CarOfferImage> zdjęcia) { this.zdjęcia = zdjęcia; }
    public Set<CarOption> getOptions() { return options; }
    public void setOptions(Set<CarOption> options) { this.options = options; }
    public Integer getPrzebieg() { return przebieg; }
    public void setPrzebieg(Integer przebieg) { this.przebieg = przebieg; }
    public Integer getMoc() { return moc; }
    public void setMoc(Integer moc) { this.moc = moc; }
    public Integer getPojemonscSilnika() { return pojemonscSilnika; }
    public void setPojemonscSilnika(Integer pojemonscSilnika) { this.pojemonscSilnika = pojemonscSilnika; }
}
