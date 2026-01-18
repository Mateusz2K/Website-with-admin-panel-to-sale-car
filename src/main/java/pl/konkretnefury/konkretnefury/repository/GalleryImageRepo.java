package pl.konkretnefury.konkretnefury.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.konkretnefury.konkretnefury.modele.GalleryImage;

import java.util.List;
import java.util.UUID;

@Repository
public interface GalleryImageRepo extends JpaRepository<GalleryImage, UUID> {
    // Pobieranie wszystkich zdjęć posortowanych po kolejności
    List<GalleryImage> findAllByOrderByDisplayOrderAsc();
}
