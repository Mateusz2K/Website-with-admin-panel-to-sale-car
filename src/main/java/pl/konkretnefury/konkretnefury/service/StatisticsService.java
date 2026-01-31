package pl.konkretnefury.konkretnefury.service;

import org.springframework.stereotype.Service;
import pl.konkretnefury.konkretnefury.modele.CarOffer;
import pl.konkretnefury.konkretnefury.modele.PageVisit;
import pl.konkretnefury.konkretnefury.repository.CarOfferRepo;
import pl.konkretnefury.konkretnefury.repository.PageVisitRepo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class StatisticsService {

    private final PageVisitRepo pageVisitRepo;
    private final CarOfferRepo carOfferRepo;

    public StatisticsService(PageVisitRepo pageVisitRepo, CarOfferRepo carOfferRepo) {
        this.pageVisitRepo = pageVisitRepo;
        this.carOfferRepo = carOfferRepo;
    }

    public void recordVisit(String pageType, String resourceId) {
        pageVisitRepo.save(new PageVisit(pageType, resourceId));
    }

    public long getTotalVisits() {
        return pageVisitRepo.count();
    }
    
    public long getOfferVisits(UUID offerId) {
        return pageVisitRepo.countByPageTypeAndResourceId("OFFER", offerId.toString());
    }

    public List<PopularOfferDTO> getMostPopularOffers() {
        List<Object[]> results = pageVisitRepo.findMostPopularOffers();
        List<PopularOfferDTO> popularOffers = new ArrayList<>();

        for (Object[] result : results) {
            String offerIdStr = (String) result[0];
            Long count = (Long) result[1];
            
            try {
                UUID offerId = UUID.fromString(offerIdStr);
                Optional<CarOffer> offer = carOfferRepo.findById(offerId);
                offer.ifPresent(carOffer -> popularOffers.add(new PopularOfferDTO(carOffer, count)));
            } catch (IllegalArgumentException e) {
                // Ignorujemy błędne ID
            }
        }
        return popularOffers;
    }

    public ChartDataDTO getVisitsLast7Days() {
        LocalDateTime sevenDaysAgo = LocalDateTime.now().minusDays(6).withHour(0).withMinute(0);
        List<Object[]> rawData = pageVisitRepo.countVisitsByDate(sevenDaysAgo);
        
        Map<String, Long> visitsMap = new HashMap<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM");

        for (Object[] row : rawData) {
            Object dateObj = row[0];
            Long count = (Long) row[1];
            
            LocalDate date;
            if (dateObj instanceof java.sql.Date) {
                date = ((java.sql.Date) dateObj).toLocalDate();
            } else if (dateObj instanceof java.sql.Timestamp) {
                date = ((java.sql.Timestamp) dateObj).toLocalDateTime().toLocalDate();
            } else if (dateObj instanceof LocalDateTime) {
                date = ((LocalDateTime) dateObj).toLocalDate();
            } else {
                continue;
            }
            
            visitsMap.put(date.format(formatter), count);
        }

        List<String> labels = new ArrayList<>();
        List<Long> data = new ArrayList<>();
        
        LocalDate current = LocalDate.now().minusDays(6);
        for (int i = 0; i < 7; i++) {
            String dateStr = current.format(formatter);
            labels.add(dateStr);
            data.add(visitsMap.getOrDefault(dateStr, 0L));
            current = current.plusDays(1);
        }

        return new ChartDataDTO(labels, data);
    }

    // ZMIANA: Nowa metoda do pobierania statystyk wg typu strony
    public ChartDataDTO getVisitsByPageType() {
        long homeVisits = pageVisitRepo.countByPageType("HOME");
        long offerVisits = pageVisitRepo.countByPageType("OFFER");
        long blogVisits = pageVisitRepo.countByPageType("BLOG"); // Zakładając, że dodasz to w BlogController

        List<String> labels = List.of("Strona Główna", "Oferty", "Blog");
        List<Long> data = List.of(homeVisits, offerVisits, blogVisits);

        return new ChartDataDTO(labels, data);
    }

    public static class PopularOfferDTO {
        private final CarOffer offer;
        private final Long visitCount;

        public PopularOfferDTO(CarOffer offer, Long visitCount) {
            this.offer = offer;
            this.visitCount = visitCount;
        }

        public CarOffer getOffer() { return offer; }
        public Long getVisitCount() { return visitCount; }
    }
    
    public static class ChartDataDTO {
        private final List<String> labels;
        private final List<Long> data;

        public ChartDataDTO(List<String> labels, List<Long> data) {
            this.labels = labels;
            this.data = data;
        }

        public List<String> getLabels() { return labels; }
        public List<Long> getData() { return data; }
    }
}
