package pl.konkretnefury.konkretnefury.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.konkretnefury.konkretnefury.modele.CarOffer;
import pl.konkretnefury.konkretnefury.modele.CarOfferImage;
import pl.konkretnefury.konkretnefury.repository.CarOfferImageRepo;
import pl.konkretnefury.konkretnefury.repository.CarOfferRepo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
public class CarOfferImageService {

    private final CarOfferImageRepo imageRepository;
    private final CarOfferRepo carOfferRepo;

    Logger logger = LoggerFactory.getLogger(CarOfferImageService.class);

    @Value("${file.upload-dir.cars}")
    private String carPhotoUploadDir;

    public CarOfferImageService(CarOfferImageRepo imageRepository, CarOfferRepo carOfferRepo) {
        this.imageRepository = imageRepository;
        this.carOfferRepo = carOfferRepo;
    }

    @Transactional
    public void setAsMainImage(UUID imageId) {
        CarOfferImage newMainImage = imageRepository.findById(imageId)
                .orElseThrow(() -> new RuntimeException("Image not found"));

        List<CarOfferImage> allImagesForOffer = imageRepository.findByCarOffer(newMainImage.getCarOffer());
        allImagesForOffer.forEach(image -> image.setMain(false));
        newMainImage.setMain(true);
        imageRepository.saveAll(allImagesForOffer);
    }

    @Transactional
    public void deleteImage(UUID imageId) {
        imageRepository.findById(imageId).ifPresent(this::deleteImageFileAndRecord);
        logger.info("Usunięto zdjęcie o ID: {}", imageId);
    }

    @Transactional
    public void deleteNonMainImages(UUID offerId) {
        CarOffer offer = carOfferRepo.findById(offerId)
                .orElseThrow(() -> new RuntimeException("Offer not found"));
        
        List<CarOfferImage> images = offer.getZdjęcia();
        
        List<CarOfferImage> imagesToDelete = images.stream()
                .filter(img -> !img.isMain())
                .toList();

        for (CarOfferImage image : imagesToDelete) {
            deleteImageFileAndRecord(image);
        }
        
        offer.getZdjęcia().removeAll(imagesToDelete);
        carOfferRepo.save(offer);
        logger.info("Usunięto zdjęcia nie główne dla oferty o ID: {}", offerId);
    }

    private void deleteImageFileAndRecord(CarOfferImage image) {
        try {
            Path filePath = Paths.get(carPhotoUploadDir, image.getFileName());
            
            // DIAGNOSTYKA: Wypisz pełną ścieżkę absolutną
            System.out.println("--- PRÓBA USUNIĘCIA PLIKU ---");
            System.out.println("Ścieżka z konfiguracji: " + carPhotoUploadDir);
            System.out.println("Nazwa pliku z bazy: " + image.getFileName());
            System.out.println("Pełna ścieżka (absolutna): " + filePath.toAbsolutePath());
            System.out.println("Czy plik istnieje? " + Files.exists(filePath));

            boolean deleted = Files.deleteIfExists(filePath);
            logger.info("Usunięto zdjęcie o nazwie: {}", image.getFileName());
            System.out.println("Czy usunięto? " + deleted);
            System.out.println("-----------------------------");
            
            imageRepository.delete(image);
        } catch (IOException e) {
            logger.warn("Nie udało się usunąć pliku: {}", image.getFileName());
            e.printStackTrace();
        }
    }
}
