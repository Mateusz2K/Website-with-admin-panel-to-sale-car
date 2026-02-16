package pl.konkretnefury.konkretnefury.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.Predicate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileSystemUtils;
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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CarOfferService {

    private static final Logger logger = LoggerFactory.getLogger(CarOfferService.class);

    private final CarOfferRepo carOfferRepository;
    private final CarOfferImageRepo carOfferImageRepository;
    private final FileStorageService fileStorageService;
    private final CarOfferImageService carOfferImageService;

    @Value("${file.upload-dir.cars}")
    private String carPhotoUploadDir;

    public CarOfferService(CarOfferRepo carOfferRepository, CarOfferImageRepo carOfferImageRepository, FileStorageService fileStorageService, CarOfferImageService carOfferImageService) {
        this.carOfferRepository = carOfferRepository;
        this.carOfferImageRepository = carOfferImageRepository;
        this.fileStorageService = fileStorageService;
        this.carOfferImageService = carOfferImageService;
    }

    @Transactional
    public void saveOfferWithImages(CarOffer offerFromForm, MultipartFile[] images) {
        CarOffer offerToSave;
        boolean isNew = offerFromForm.getId() == null;
        
        if (!isNew) {
            offerToSave = carOfferRepository.findById(offerFromForm.getId())
                    .orElseThrow(() -> new EntityNotFoundException("Not found"));
            updateOfferFields(offerToSave, offerFromForm);
            logger.info("Aktualizacja oferty: {} {}", offerToSave.getBrand().getNazwaMarki(), offerToSave.getModel().getModel());
        } else {
            offerToSave = offerFromForm;
            if (offerToSave.getGwarancjaOpis() == null || offerToSave.getGwarancjaOpis().isEmpty()) {
                // Tu można by użyć serwisu ustawień
            }
            logger.info("Tworzenie nowej oferty: {} {}", offerToSave.getBrand().getNazwaMarki(), offerToSave.getModel().getModel());
        }

        CarOffer savedOffer = carOfferRepository.save(offerToSave);

        if (images != null && images.length > 0) {
            logger.info("Zapisywanie {} zdjęć dla oferty {}", images.length, savedOffer.getId());
            boolean hasMainImage = savedOffer.getZdjęcia().stream().anyMatch(CarOfferImage::isMain);
            for (int i = 0; i < images.length; i++) {
                MultipartFile image = images[i];
                if (image != null && !image.isEmpty()) {
                    String relativePath = fileStorageService.storeCarPhoto(image, savedOffer.getId());
                    CarOfferImage offerImage = new CarOfferImage();
                    offerImage.setFileName(relativePath);
                    offerImage.setCarOffer(savedOffer);
                    if (i == 0 && !hasMainImage) {
                        offerImage.setMain(true);
                        hasMainImage = true;
                    }
                    savedOffer.getZdjęcia().add(offerImage);
                }
            }
            carOfferRepository.save(savedOffer);
        }
    }

    private void updateOfferFields(CarOffer existingOffer, CarOffer formOffer) {
        existingOffer.setBrand(formOffer.getBrand());
        existingOffer.setModel(formOffer.getModel());
        existingOffer.setNaglowek(formOffer.getNaglowek());
        existingOffer.setRok(formOffer.getRok());
        existingOffer.setCena(formOffer.getCena());
        existingOffer.setPrzebieg(formOffer.getPrzebieg());
        existingOffer.setLiczba_drzwi(formOffer.getLiczba_drzwi());
        existingOffer.setMoc(formOffer.getMoc());
        existingOffer.setRodzajPaliwa(formOffer.getRodzajPaliwa());
        existingOffer.setRodzajNadwozia(formOffer.getRodzajNadwozia());
        existingOffer.setRodzajNadwoziaCiezarowe(formOffer.getRodzajNadwoziaCiezarowe());
        existingOffer.setPojemonscSilnika(formOffer.getPojemonscSilnika());
        existingOffer.setOpis(formOffer.getOpis());
        existingOffer.setFeatured(formOffer.isFeatured());
        existingOffer.setOptions(formOffer.getOptions());
        existingOffer.setKrajPochodzenia(formOffer.getKrajPochodzenia());
        existingOffer.setKolor(formOffer.getKolor());
        existingOffer.setGwarancjaOpis(formOffer.getGwarancjaOpis());
        existingOffer.setSkrzyniaBiegow(formOffer.getSkrzyniaBiegow());
        existingOffer.setTypPojazdu(formOffer.getTypPojazdu());
        existingOffer.setNaped(formOffer.getNaped());
    }

    @Transactional
    public void deleteOffer(UUID id) {
        logger.warn("Usuwanie oferty o ID: {}", id);
        carOfferRepository.findById(id).ifPresent(offer -> {
            // ZMIANA: Użycie FileSystemUtils i poprawne budowanie ścieżki
            try {
                Path uploadPath = Paths.get(carPhotoUploadDir).toAbsolutePath().normalize();
                Path offerDirectory = uploadPath.resolve(offer.getId().toString());
                
                logger.info("Próba usunięcia katalogu: {}", offerDirectory);
                
                if (Files.exists(offerDirectory)) {
                    boolean deleted = FileSystemUtils.deleteRecursively(offerDirectory);
                    if (deleted) {
                        logger.info("Pomyślnie usunięto katalog zdjęć.");
                    } else {
                        logger.error("Nie udało się usunąć katalogu (FileSystemUtils zwrócił false).");
                    }
                } else {
                    logger.warn("Katalog zdjęć nie istnieje: {}", offerDirectory);
                }
            } catch (Exception e) {
                logger.error("Błąd podczas usuwania katalogu zdjęć: " + id, e);
            }
        });
        carOfferRepository.deleteById(id);
    }
    
    public Page<CarOffer> findWithFilters(OfferFilterDTO filters, Pageable pageable) {
        // ZMIANA: Obsługa sortowania
        Sort sort = Sort.unsorted();
        if (filters.getSort() != null) {
            switch (filters.getSort()) {
                case "price_asc":
                    sort = Sort.by("cena").ascending();
                    break;
                case "price_desc":
                    sort = Sort.by("cena").descending();
                    break;
                case "name_asc":
                    sort = Sort.by("brand.nazwaMarki").ascending().and(Sort.by("model.model").ascending());
                    break;
                case "newest":
                    sort = Sort.by("creationDate").descending();
                    break;
                default:
                    sort = Sort.by("creationDate").descending(); // Domyślne sortowanie
            }
        } else {
            sort = Sort.by("creationDate").descending();
        }

        // Tworzymy nowy obiekt Pageable z uwzględnieniem sortowania
        Pageable sortedPageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);

        Specification<CarOffer> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (filters.getBrandId() != null) predicates.add(cb.equal(root.get("brand").get("id"), filters.getBrandId()));
            if (filters.getPriceFrom() != null) predicates.add(cb.greaterThanOrEqualTo(root.get("cena"), filters.getPriceFrom()));
            if (filters.getPriceTo() != null) predicates.add(cb.lessThanOrEqualTo(root.get("cena"), filters.getPriceTo()));
            if (filters.getYearFrom() != null) predicates.add(cb.greaterThanOrEqualTo(root.get("rok"), filters.getYearFrom()));
            if (filters.getSkrzyniaBiegow() != null) predicates.add(cb.equal(root.get("skrzyniaBiegow"), filters.getSkrzyniaBiegow()));
            if (filters.getRodzajPaliwa() != null) predicates.add(cb.equal(root.get("rodzajPaliwa"), filters.getRodzajPaliwa()));
            if (filters.getTypNadwozia() != null) predicates.add(cb.equal(root.get("rodzajNadwozia"), filters.getTypNadwozia()));
            if (filters.getTypPojazdu() != null) predicates.add(cb.equal(root.get("typPojazdu"), filters.getTypPojazdu()));
            return cb.and(predicates.toArray(new Predicate[0]));
        };
        return carOfferRepository.findAll(spec, sortedPageable);
    }
    public List<CarOffer> getAllOffers() { return carOfferRepository.findAll(); }
    public Optional<CarOffer> getOfferById(UUID id) { return carOfferRepository.findById(id);}
    
    @Transactional
    public void changeStatusOfOffer(UUID id, String status) {
        logger.info("Zmiana statusu oferty {} na {}", id, status);
        CarOffer offer = carOfferRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Not found"));
        StatusOfCar newStatus = StatusOfCar.valueOf(status.toUpperCase());
        offer.setStatus(newStatus);
        
        if (newStatus == StatusOfCar.SPRZEDANY) {
            logger.info("Oferta sprzedana - usuwanie zbędnych zdjęć");
            carOfferImageService.deleteNonMainImages(id);
        }
    }
    
    @Transactional
    public void toggleFeaturedOffer(UUID id) {
        CarOffer offer = carOfferRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Not found"));
        offer.setFeatured(!offer.isFeatured());
        logger.info("Zmiana statusu promowania dla oferty {}: {}", id, offer.isFeatured());
    }
    public List<CarOffer> getFeaturedOffers() { return carOfferRepository.findAllByIsFeaturedTrue(); }
    
    public long countActiveOffers() {
        long total = carOfferRepository.count();
        long sold = carOfferRepository.countByStatus(StatusOfCar.SPRZEDANY);
        long reserved = carOfferRepository.countByStatus(StatusOfCar.ZAREZERWOWANY);
        return total - sold - reserved;
    }

    public List<CarOffer> findLast5Offers() {
        Pageable pageable = PageRequest.of(0, 5);
        List<CarOffer> offers = carOfferRepository.findRecentOffers(pageable);
        for (CarOffer offer : offers) {
            if (offer.getCreationDate() == null) {
                offer.setCreationDate(LocalDateTime.now());
                carOfferRepository.save(offer);
            }
        }
        return offers;
    }
}
