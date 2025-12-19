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

    public String storeIcon(MultipartFile file) {
        return storeAndOptimize(file, iconUploadDir, null, 200, "webp");
    }

    public String storeCarPhoto(MultipartFile file, UUID offerId) {
        String folderName = offerId.toString();
        return storeAndOptimize(file, carPhotoUploadDir, folderName, 1200, "webp");
    }

    private String storeAndOptimize(MultipartFile file, String baseDir, String subDir, int targetWidth, String outputFormat) {
        if (file == null || file.isEmpty()) return null;

        try {
            // ZMIANA: Nowa nazwa pliku będzie miała rozszerzenie .webp
            String newFileName = UUID.randomUUID().toString() + "." + outputFormat;
            
            Path uploadDir = (subDir != null) ? Paths.get(baseDir, subDir) : Paths.get(baseDir);
            Files.createDirectories(uploadDir);
            
            Path filePath = uploadDir.resolve(newFileName);

            // ZMIANA: Logika optymalizacji
            Thumbnails.of(file.getInputStream())
                    .size(targetWidth, targetWidth) // Zmiana rozmiaru do max 1200px szerokości/wysokości
                    .outputFormat(outputFormat)     // Konwersja do WebP
                    .outputQuality(0.85)            // Kompresja na poziomie 85%
                    .toFile(filePath.toFile());

            // Zwracamy ścieżkę relatywną
            return (subDir != null) ? subDir + "/" + newFileName : newFileName;

        } catch (IOException e) {
            throw new RuntimeException("Could not store and optimize the file. Error: " + e.getMessage());
        }
    }
}
