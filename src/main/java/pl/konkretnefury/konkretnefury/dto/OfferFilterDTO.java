package pl.konkretnefury.konkretnefury.dto;

import pl.konkretnefury.konkretnefury.modele.RodzajPaliwa;
import pl.konkretnefury.konkretnefury.modele.SkrzyniaBiegow;
import pl.konkretnefury.konkretnefury.modele.TypPojazdu;

import java.math.BigDecimal;
import java.util.UUID;

public class OfferFilterDTO {
    private UUID brandId;
    private BigDecimal priceFrom;
    private BigDecimal priceTo;
    private Integer yearFrom;
    private String typNadwozia;
    private SkrzyniaBiegow skrzyniaBiegow;
    private RodzajPaliwa rodzajPaliwa;
    private TypPojazdu typPojazdu;
    private String sort;

    // Getters and Setters
    public UUID getBrandId() { return brandId; }
    public void setBrandId(UUID brandId) { this.brandId = brandId; }
    public BigDecimal getPriceFrom() { return priceFrom; }
    public void setPriceFrom(BigDecimal priceFrom) { this.priceFrom = priceFrom; }
    public BigDecimal getPriceTo() { return priceTo; }
    public void setPriceTo(BigDecimal priceTo) { this.priceTo = priceTo; }
    public Integer getYearFrom() { return yearFrom; }
    public void setYearFrom(Integer yearFrom) { this.yearFrom = yearFrom; }

    public String getTypNadwozia() { return typNadwozia; }
    public void setTypNadwozia(String typNadwozia) { this.typNadwozia = typNadwozia; }
    public SkrzyniaBiegow getSkrzyniaBiegow() { return skrzyniaBiegow; }
    public void setSkrzyniaBiegow(SkrzyniaBiegow skrzyniaBiegow) { this.skrzyniaBiegow = skrzyniaBiegow; }

    public RodzajPaliwa getRodzajPaliwa() {
        return rodzajPaliwa;
    }

    public void setRodzajPaliwa(RodzajPaliwa rodzajPaliwa) {
        this.rodzajPaliwa = rodzajPaliwa;
    }
    public TypPojazdu getTypPojazdu() { return typPojazdu; }
    public void setTypPojazdu(TypPojazdu typPojazdu) { this.typPojazdu = typPojazdu; }

    public String getSort() { return sort; }
    public void setSort(String sort) { this.sort = sort; }
}
