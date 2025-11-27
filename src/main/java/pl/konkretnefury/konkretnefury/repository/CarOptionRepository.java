package pl.konkretnefury.konkretnefury.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.konkretnefury.konkretnefury.modele.CarOption;

import java.util.UUID;

@Repository
public interface CarOptionRepository extends JpaRepository<CarOption, UUID> {
}
