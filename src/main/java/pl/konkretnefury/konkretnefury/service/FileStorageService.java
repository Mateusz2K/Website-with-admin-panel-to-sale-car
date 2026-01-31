package pl.konkretnefury.konkretnefury.service;

import net.coobird.thumbnailator.Thumbnails;
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

    @Value("${file.upload-dir.icons}")
    private String iconUploadDir;

    @Value("${file.upload-dir.cars}")
    private String carPhotoUploadDir;
    
    @Value("${file.upload-dir.gallery}")
    private String galleryUploadDir;

    public String storeIcon(MultipartFile file) {
        String fileName = storeAndOptimize(file, iconUploadDir, null, 200, "jpg");
        return "/uploads/icons/" + fileName;
    }
    
    public String storeDefaultImageOffer(MultipartFile file){
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

            // Zwracamy tylko nazwę pliku (lub podkatalog/nazwę), a URL budujemy w metodzie wywołującej
            return (subDir != null) ? subDir + "/" + newFileName : newFileName;

        } catch (IOException e) {
            throw new RuntimeException("Could not store and optimize the file. Error: " + e.getMessage());
        }
    }
    
    public void deleteFile(String filePath) {
        try {
            if (filePath.startsWith("/")) {
                filePath = filePath.substring(1);
            }
            Files.deleteIfExists(Paths.get(filePath));
        } catch (IOException e) {
            System.err.println("Nie udało się usunąć pliku: " + filePath);
        }
    }
}
