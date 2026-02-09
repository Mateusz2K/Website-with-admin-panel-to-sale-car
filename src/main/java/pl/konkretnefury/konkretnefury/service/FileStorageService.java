package pl.konkretnefury.konkretnefury.service;

import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(FileStorageService.class);

    @Value("${file.upload-dir.icons}")
    private String iconUploadDir;

    @Value("${file.upload-dir.cars}")
    private String carPhotoUploadDir;
    
    @Value("${file.upload-dir.gallery}")
    private String galleryUploadDir;

    public String storeIcon(MultipartFile file) {
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        if ("svg".equalsIgnoreCase(extension)) {
            logger.debug("Zapisywanie ikony SVG: {}", file.getOriginalFilename());
            return storeRawFile(file, iconUploadDir, "/uploads/icons/");
        }
        
        logger.debug("Optymalizacja i zapis ikony: {}", file.getOriginalFilename());
        String fileName = storeAndOptimize(file, iconUploadDir, null, 200, "jpg");
        return "/uploads/icons/" + fileName;
    }
    
    public String storeDefaultImageOffer(MultipartFile file){
        logger.debug("Zapisywanie domyślnego zdjęcia oferty");
        String fileName = storeAndOptimize(file, carPhotoUploadDir, null, 1200, "jpg");
        // ZMIANA: Poprawiono ścieżkę URL z /uploads/cars/ na /car_photo/
        return "uploads/car_photos/" + fileName;
    }

    public String storeCarPhoto(MultipartFile file, UUID offerId) {
        String folderName = offerId.toString();
        return storeAndOptimize(file, carPhotoUploadDir, folderName, 1200, "jpg");
    }
    
    // ZMIANA: Poprawione zwracanie ścieżki dla galerii
    public String storeGalleryImage(MultipartFile file) {
        String fileName = storeAndOptimize(file, galleryUploadDir, null, 1200, "jpg");
        // Zawsze zwracamy poprawny URL
        return "/uploads/gallery/" + fileName;
    }

    // Nowa metoda do zapisywania plików bez optymalizacji (np. SVG)
    private String storeRawFile(MultipartFile file, String baseDir, String urlPrefix) {
        try {
            String extension = FilenameUtils.getExtension(file.getOriginalFilename());
            String newFileName = UUID.randomUUID().toString() + "." + extension;
            
            Path uploadDir = Paths.get(baseDir);
            if (!Files.exists(uploadDir)) {
                Files.createDirectories(uploadDir);
            }
            
            Path filePath = uploadDir.resolve(newFileName);
            file.transferTo(filePath);
            
            return urlPrefix + newFileName;
        } catch (IOException e) {
            logger.error("Błąd zapisu pliku raw: {}", file.getOriginalFilename(), e);
            throw new RuntimeException("Could not store file. Error: " + e.getMessage());
        }
    }

    private String storeAndOptimize(MultipartFile file, String baseDir, String subDir, int targetWidth, String outputFormat) {
        if (file == null || file.isEmpty()) return null;

        try {
            String newFileName = UUID.randomUUID().toString() + "." + outputFormat;
            
            Path uploadDir = (subDir != null) ? Paths.get(baseDir, subDir) : Paths.get(baseDir);
            if (!Files.exists(uploadDir)) {
                Files.createDirectories(uploadDir);
            }
            
            Path filePath = uploadDir.resolve(newFileName);

            Thumbnails.of(file.getInputStream())
                    .size(targetWidth, targetWidth)
                    .outputFormat(outputFormat)
                    .outputQuality(0.85)
                    .toFile(filePath.toFile());

            return (subDir != null) ? subDir + "/" + newFileName : newFileName;

        } catch (IOException e) {
            logger.error("Błąd optymalizacji pliku: {}", file.getOriginalFilename(), e);
            throw new RuntimeException("Could not store and optimize the file. Error: " + e.getMessage());
        }
    }
    
    public void deleteFile(String filePath) {
        try {
            if (filePath.startsWith("/")) {
                filePath = filePath.substring(1);
            }
            Path path = Paths.get(filePath);
            boolean deleted = Files.deleteIfExists(path);
            if (deleted) {
                logger.info("Usunięto plik: {}", filePath);
            } else {
                logger.warn("Plik do usunięcia nie istnieje: {}", filePath);
            }
        } catch (IOException e) {
            logger.error("Nie udało się usunąć pliku: " + filePath, e);
        }
    }
}
