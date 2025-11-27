package pl.konkretnefury.konkretnefury.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.konkretnefury.konkretnefury.modele.BrandsModel;

import java.util.UUID;
@Repository
public interface BrandModelRepo extends JpaRepository<BrandsModel, UUID> {
}
