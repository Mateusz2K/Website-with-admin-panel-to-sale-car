package pl.konkretnefury.konkretnefury.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.konkretnefury.konkretnefury.modele.PageVisit;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PageVisitRepo extends JpaRepository<PageVisit, Long> {
    
    long countByPageType(String pageType);
    
    long countByPageTypeAndResourceId(String pageType, String resourceId);

    // Pobierz liczbę wizyt z ostatnich 7 dni (dla wykresu)
    @Query("SELECT v.visitDate, COUNT(v) FROM PageVisit v WHERE v.visitDate >= :startDate GROUP BY v.visitDate")
    List<Object[]> countVisitsByDate(LocalDateTime startDate);
    
    // Pobierz najpopularniejsze oferty
    @Query("SELECT v.resourceId, COUNT(v) as cnt FROM PageVisit v WHERE v.pageType = 'OFFER' GROUP BY v.resourceId ORDER BY cnt DESC LIMIT 5")
    List<Object[]> findMostPopularOffers();
}
