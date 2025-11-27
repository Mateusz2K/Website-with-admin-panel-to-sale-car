package pl.konkretnefury.konkretnefury.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.konkretnefury.konkretnefury.modele.CarOffer;
import pl.konkretnefury.konkretnefury.modele.StatusOfCar;

import java.util.List;
import java.util.UUID;

@Repository
public interface CarOfferRepo extends JpaRepository<CarOffer, UUID>, JpaSpecificationExecutor<CarOffer> {
    List<CarOffer> findAllByIsFeaturedTrue();
    long countByStatus(StatusOfCar status);
    List<CarOffer> findAllByStatus(StatusOfCar status);


    // ZMIANA: Dodano "NULLS LAST", aby oferty bez daty były na końcu
    @Query("SELECT co FROM CarOffer co ORDER BY co.creationDate DESC NULLS LAST")
    List<CarOffer> findRecentOffers(Pageable pageable);
}
