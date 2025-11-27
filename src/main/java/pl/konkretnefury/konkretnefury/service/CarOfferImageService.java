package pl.konkretnefury.konkretnefury.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.konkretnefury.konkretnefury.modele.CarOfferImage;
import pl.konkretnefury.konkretnefury.repository.CarOfferImageRepo;

import java.util.List;
import java.util.UUID;

@Service
public class CarOfferImageService {

    private final CarOfferImageRepo imageRepository;

    public CarOfferImageService(CarOfferImageRepo imageRepository) {
        this.imageRepository = imageRepository;
    }

    @Transactional
    public void setAsMainImage(UUID imageId) {
        CarOfferImage newMainImage = imageRepository.findById(imageId)
                .orElseThrow(() -> new RuntimeException("Image not found"));

        // 1. Znajdź wszystkie zdjęcia dla tej samej oferty
        List<CarOfferImage> allImagesForOffer = imageRepository.findByCarOffer(newMainImage.getCarOffer());

        // 2. Odznacz wszystkie inne zdjęcia jako nie-główne
        for (CarOfferImage image : allImagesForOffer) {
            image.setMain(false);
        }

        // 3. Oznacz wybrane zdjęcie jako główne
        newMainImage.setMain(true);

        // Zapisz wszystkie zmiany (dzięki @Transactional, wystarczy zmiana w obiektach)
        imageRepository.saveAll(allImagesForOffer);
    }

    @Transactional
    public void deleteImage(UUID imageId) {
        // Logika usuwania pliku z dysku powinna być tutaj dodana w przyszłości
        imageRepository.deleteById(imageId);
    }
}
