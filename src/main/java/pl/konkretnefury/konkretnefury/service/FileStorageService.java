package pl.konkretnefury.konkretnefury.service;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileStorageService {

    // ZMIANA: Odczyt ścieżek ze zmiennych środowiskowych
    @Value("${FILE_UPLOAD_DIR_ICONS:/uploads/icons}")
    private String iconUploadDir;

    @Value("${FILE_UPLOAD_DIR_CARS:/uploads/car_photos}")
    private String carPhotoUploadDir;

    public String storeIcon(MultipartFile file) {
        if (file == null || file.isEmpty()) return null;
        try {
            String extension = FilenameUtils.getExtension(file.getOriginalFilename());
            String uniqueFileName = UUID.randomUUID().toString() + "." + extension;
            Path uploadPath = Paths.get(iconUploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            Path filePath = uploadPath.resolve(uniqueFileName);
            Files.copy(file.getInputStream(), filePath);
            return "/uploads/icons/" + uniqueFileName;
        } catch (IOException e) {
            throw new RuntimeException("Could not store the icon file. Error: " + e.getMessage());
        }
    }

    public String storeCarPhoto(MultipartFile file, UUID offerId) {
        if (file == null || file.isEmpty()) return null;
        try {
            String extension = FilenameUtils.getExtension(file.getOriginalFilename());
            String uniqueFileName = UUID.randomUUID().toString() + "." + extension;
            
            Path offerUploadPath = Paths.get(carPhotoUploadDir).resolve(offerId.toString());
            if (!Files.exists(offerUploadPath)) {
                Files.createDirectories(offerUploadPath);
            }

            Path filePath = offerUploadPath.resolve(uniqueFileName);
            Files.copy(file.getInputStream(), filePath);

            return offerId.toString() + "/" + uniqueFileName;
        } catch (IOException e) {
            throw new RuntimeException("Could not store the car photo. Error: " + e.getMessage());
        }
    }
}
