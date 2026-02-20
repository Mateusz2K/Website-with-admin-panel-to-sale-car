package pl.konkretnefury.konkretnefury.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.konkretnefury.konkretnefury.modele.CarOfferImage;

import java.util.List;
import java.util.UUID;

@Repository
public interface CarOfferImageRepo extends JpaRepository<CarOfferImage, UUID> {
    // ZMIANA: Metoda do pobierania posortowanych zdjęć
    List<CarOfferImage> findByCarOfferIdOrderByIsMainDescDisplayOrderAsc(UUID offerId);
}
