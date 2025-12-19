package pl.konkretnefury.konkretnefury.dto;

import java.math.BigDecimal;
import java.util.UUID;

public class OfferFilterDTO {
    private UUID brandId;
    private BigDecimal priceFrom;
    private BigDecimal priceTo;
    private Integer yearFrom;
    private Integer yearTo;
    private String typNadwozia;

    // Getters and Setters
    public UUID getBrandId() { return brandId; }
    public void setBrandId(UUID brandId) { this.brandId = brandId; }
    public BigDecimal getPriceFrom() { return priceFrom; }
    public void setPriceFrom(BigDecimal priceFrom) { this.priceFrom = priceFrom; }
    public BigDecimal getPriceTo() { return priceTo; }
    public void setPriceTo(BigDecimal priceTo) { this.priceTo = priceTo; }
    public Integer getYearFrom() { return yearFrom; }
    public void setYearFrom(Integer yearFrom) { this.yearFrom = yearFrom; }
    public Integer getYearTo() { return yearTo; }
    public void setYearTo(Integer yearTo) { this.yearTo = yearTo; }

    public String getTypNadwozia() {
        return typNadwozia;
    }

    public void setTypNadwozia(String typNadwozia) {
        this.typNadwozia = typNadwozia;
    }
}
