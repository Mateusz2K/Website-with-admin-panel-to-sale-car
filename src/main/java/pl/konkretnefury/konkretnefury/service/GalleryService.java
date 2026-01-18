package pl.konkretnefury.konkretnefury.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import pl.konkretnefury.konkretnefury.modele.GalleryImage;
import pl.konkretnefury.konkretnefury.repository.GalleryImageRepo;

import java.util.List;
import java.util.UUID;

@Service
public class GalleryService {

    private final GalleryImageRepo galleryRepo;
    private final FileStorageService fileStorageService;

    public GalleryService(GalleryImageRepo galleryRepo, FileStorageService fileStorageService) {
        this.galleryRepo = galleryRepo;
        this.fileStorageService = fileStorageService;
    }

    public List<GalleryImage> getAllImages() {
        return galleryRepo.findAllByOrderByDisplayOrderAsc();
    }

    @Transactional
    public void addImages(MultipartFile[] files) {
        if (files == null) return;
        
        int maxOrder = galleryRepo.findAll().stream()
                .mapToInt(GalleryImage::getDisplayOrder)
                .max()
                .orElse(0);

        for (MultipartFile file : files) {
            if (!file.isEmpty()) {
                String path = fileStorageService.storeGalleryImage(file);
                maxOrder++;
                GalleryImage image = new GalleryImage(path, maxOrder);
                galleryRepo.save(image);
            }
        }
    }

    @Transactional
    public void deleteImage(UUID id) {
        galleryRepo.findById(id).ifPresent(image -> {
            fileStorageService.deleteFile(image.getFileName());
            galleryRepo.delete(image);
        });
    }
    
    @Transactional
    public void updateOrder(List<UUID> orderedIds) {
        for (int i = 0; i < orderedIds.size(); i++) {
            int finalI = i; // ZMIANA: Zmienna pomocnicza dla lambdy
            UUID id = orderedIds.get(i);
            galleryRepo.findById(id).ifPresent(img -> {
                img.setDisplayOrder(finalI + 1);
                galleryRepo.save(img);
            });
        }
    }
}
