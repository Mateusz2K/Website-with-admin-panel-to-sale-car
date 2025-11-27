package pl.konkretnefury.konkretnefury.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.Predicate;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import pl.konkretnefury.konkretnefury.dto.OfferFilterDTO;
import pl.konkretnefury.konkretnefury.modele.CarOffer;
import pl.konkretnefury.konkretnefury.modele.CarOfferImage;
import pl.konkretnefury.konkretnefury.modele.StatusOfCar;
import pl.konkretnefury.konkretnefury.repository.CarOfferImageRepo;
import pl.konkretnefury.konkretnefury.repository.CarOfferRepo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CarOfferService {

    private final CarOfferRepo carOfferRepository;
    private final CarOfferImageRepo carOfferImageRepository;
    private final FileStorageService fileStorageService;

    @Value("${file.upload-dir.cars}")
    private String carPhotoUploadDir;

    public CarOfferService(CarOfferRepo carOfferRepository, CarOfferImageRepo carOfferImageRepository, FileStorageService fileStorageService) {
        this.carOfferRepository = carOfferRepository;
        this.carOfferImageRepository = carOfferImageRepository;
        this.fileStorageService = fileStorageService;
    }

    @Transactional
    public void saveOfferWithImages(CarOffer offerFromForm, MultipartFile[] images) {
        CarOffer offerToSave;
        if (offerFromForm.getId() != null) {
            offerToSave = carOfferRepository.findById(offerFromForm.getId())
                    .orElseThrow(() -> new EntityNotFoundException("Not found"));
            updateOfferFields(offerToSave, offerFromForm);
        } else {
            offerToSave = offerFromForm;
        }

        if (images != null && images.length > 0) {
            boolean hasMainImage = offerToSave.getZdjęcia().stream().anyMatch(CarOfferImage::isMain);
            for (int i = 0; i < images.length; i++) {
                MultipartFile image = images[i];
                if (image != null && !image.isEmpty()) {
                    String relativePath = fileStorageService.storeCarPhoto(image, offerToSave.getId());
                    CarOfferImage offerImage = new CarOfferImage();
                    offerImage.setFileName(relativePath);
                    offerImage.setCarOffer(offerToSave);
                    if (i == 0 && !hasMainImage) {
                        offerImage.setMain(true);
                        hasMainImage = true;
                    }
                    offerToSave.getZdjęcia().add(offerImage);
                }
            }
        }
        carOfferRepository.save(offerToSave);
    }

    private void updateOfferFields(CarOffer existingOffer, CarOffer formOffer) {
        existingOffer.setBrand(formOffer.getBrand());
        existingOffer.setModel(formOffer.getModel());
        existingOffer.setRok(formOffer.getRok());
        existingOffer.setCena(formOffer.getCena());
        existingOffer.setPrzebieg(formOffer.getPrzebieg());
        existingOffer.setLiczba_drzwi(formOffer.getLiczba_drzwi());
        existingOffer.setMoc(formOffer.getMoc());
        existingOffer.setRodzajPaliwa(formOffer.getRodzajPaliwa());
        existingOffer.setRodzajNadwozia(formOffer.getRodzajNadwozia());
        existingOffer.setPojemonscSilnika(formOffer.getPojemonscSilnika());
        existingOffer.setOpis(formOffer.getOpis());
        existingOffer.setFeatured(formOffer.isFeatured());
        // ZMIANA: Dodano brakującą aktualizację opcji wyposażenia
        existingOffer.setOptions(formOffer.getOptions());
    }

    @Transactional
    public void deleteOffer(UUID id) {
        carOfferRepository.findById(id).ifPresent(offer -> {
            Path offerDirectory = Paths.get(carPhotoUploadDir).resolve(offer.getId().toString());
            try {
                if (Files.exists(offerDirectory)) {
                    FileUtils.deleteDirectory(offerDirectory.toFile());
                }
            } catch (IOException e) {
                System.err.println("Nie udało się usunąć folderu: " + id);
            }
        });
        carOfferRepository.deleteById(id);
    }
    
    public List<CarOffer> findWithFilters(OfferFilterDTO filters) {
        return carOfferRepository.findAll((Specification<CarOffer>) (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (filters.getBrandId() != null) predicates.add(cb.equal(root.get("brand").get("id"), filters.getBrandId()));
            if (filters.getPriceFrom() != null) predicates.add(cb.greaterThanOrEqualTo(root.get("cena"), filters.getPriceFrom()));
            if (filters.getPriceTo() != null) predicates.add(cb.lessThanOrEqualTo(root.get("cena"), filters.getPriceTo()));
            if (filters.getYearFrom() != null) predicates.add(cb.greaterThanOrEqualTo(root.get("rok"), filters.getYearFrom()));
            if (filters.getYearTo() != null) predicates.add(cb.lessThanOrEqualTo(root.get("rok"), filters.getYearTo()));
            return cb.and(predicates.toArray(new Predicate[0]));
        });
    }
    public List<CarOffer> getAllOffers() { return carOfferRepository.findAll(); }
    public Optional<CarOffer> getOfferById(UUID id) { return carOfferRepository.findById(id); }
    @Transactional
    public void changeStatusOfOffer(UUID id, String status) {
        CarOffer offer = carOfferRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Not found"));
        offer.setStatus(StatusOfCar.valueOf(status.toUpperCase()));
    }
    @Transactional
    public void toggleFeaturedOffer(UUID id) {
        CarOffer offer = carOfferRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Not found"));
        offer.setFeatured(!offer.isFeatured());
    }
    public List<CarOffer> getFeaturedOffers() { return carOfferRepository.findAllByIsFeaturedTrue(); }
    public long countActiveOffers() { return carOfferRepository.countByStatus(StatusOfCar.DOSTĘPNY); }
    public List<CarOffer> findLast5Offers() {
        Pageable pageable = PageRequest.of(0, 5);
        return carOfferRepository.findRecentOffers(pageable);
    }
}
