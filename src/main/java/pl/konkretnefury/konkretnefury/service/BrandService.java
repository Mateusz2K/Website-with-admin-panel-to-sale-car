package pl.konkretnefury.konkretnefury.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import pl.konkretnefury.konkretnefury.modele.Brand;
import pl.konkretnefury.konkretnefury.modele.BrandsModel;
import pl.konkretnefury.konkretnefury.repository.BrandModelRepo;
import pl.konkretnefury.konkretnefury.repository.BrandRepo;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BrandService {

    private final BrandRepo brandRepository;
    private final BrandModelRepo brandsModelRepository;
    private final FileStorageService fileStorageService;

    public BrandService(BrandRepo brandRepository, BrandModelRepo brandsModelRepository, FileStorageService fileStorageService) {
        this.brandRepository = brandRepository;
        this.brandsModelRepository = brandsModelRepository;
        this.fileStorageService = fileStorageService;
    }

    public List<Brand> getAllBrands() {
        return brandRepository.findAll();
    }

    public Optional<Brand> getBrandById(UUID id) {
        return brandRepository.findById(id);
    }

    @Transactional
    public void saveBrand(Brand brand) {
        brandRepository.save(brand);
    }

    @Transactional
    public void deleteBrand(UUID id) {
        brandRepository.deleteById(id);
    }

    @Transactional
    public void saveBrandWithIcon(Brand brand, MultipartFile iconFile) {
        if (iconFile != null && !iconFile.isEmpty()) {
            String iconUrl = fileStorageService.storeIcon(iconFile);
            brand.setIconUrl(iconUrl);
        }
        brandRepository.save(brand);
    }

    // --- Metody dla Modeli ---

    public Optional<BrandsModel> getModelById(UUID id) {
        return brandsModelRepository.findById(id);
    }

    @Transactional
    public void saveModel(BrandsModel model) {
        brandsModelRepository.save(model);
    }

    @Transactional
    public void deleteModel(UUID id) {
        brandsModelRepository.deleteById(id);
    }

    @Transactional
    public BrandsModel updateModel(UUID id, BrandsModel modelDetails) {
        // 1. Wczytaj istniejący model lub rzuć wyjątek, jeśli go nie ma
        BrandsModel existingModel = brandsModelRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Nie znaleziono modelu o ID: " + id));

        // 2. Zaktualizuj pole 'model' w istniejącym obiekcie
        existingModel.setModel(modelDetails.getModel());

        // 3. Zapisz zaktualizowany obiekt
        return brandsModelRepository.save(existingModel);
    }
}
