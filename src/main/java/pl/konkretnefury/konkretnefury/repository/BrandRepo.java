package pl.konkretnefury.konkretnefury.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.konkretnefury.konkretnefury.modele.Brand;

import java.util.List;
import java.util.UUID;

@Repository
public interface BrandRepo extends JpaRepository<Brand, UUID> {

}

