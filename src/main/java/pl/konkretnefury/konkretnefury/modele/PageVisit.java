package pl.konkretnefury.konkretnefury.modele;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class PageVisit {
    @Id
    @GeneratedValue
    private Long id;

    private String pageType; // np. "HOME", "OFFER", "BLOG"
    private String resourceId; // np. ID oferty lub wpisu (jako String)
    
    @CreationTimestamp
    private LocalDateTime visitDate;

    public PageVisit() {}

    public PageVisit(String pageType, String resourceId) {
        this.pageType = pageType;
        this.resourceId = resourceId;
    }

    // Getters
    public Long getId() { return id; }
    public String getPageType() { return pageType; }
    public String getResourceId() { return resourceId; }
    public LocalDateTime getVisitDate() { return visitDate; }
}
