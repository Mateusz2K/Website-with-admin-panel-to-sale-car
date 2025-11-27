package pl.konkretnefury.konkretnefury.Controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/admin/upload")
@PreAuthorize("hasRole('ADMIN')")
public class ImageUploadController {
    @Value("${upload.dir}")
    private String uploadUrl;

    @PostMapping("/image")
    public ResponseEntity<?> uploadImage(@RequestParam("file") MultipartFile file){
        if (file.isEmpty() || !file.getContentType().startsWith("image/")) {
            return ResponseEntity.badRequest().body(Map.of("error", "Nieprawidłowy plik. Proszę przesłać obraz."));
        }
        try {
            // 2. Stworzenie unikalnej nazwy pliku, aby uniknąć konfliktów
            String originalFilename = file.getOriginalFilename();
            String fileExtension = "";
            if (originalFilename != null && originalFilename.contains(".")) {
                    fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
            }
            String uniqueFileName = UUID.randomUUID().toString() + fileExtension;

            // 3. Zapisanie pliku na serwerze
            Path uploadPath = Paths.get(uploadUrl);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath); // Utwórz folder, jeśli nie istnieje
            }
            Path filePath = uploadPath.resolve(uniqueFileName);
            Files.copy(file.getInputStream(), filePath);

            // 4. Zwrócenie JSON z lokalizacją pliku
            // Ścieżka musi być zgodna z tą, którą skonfigurujesz w WebMvcConfigurer
            String fileUrl = "/static/photo/" + uniqueFileName;

            // Edytory WYSIWYG oczekują JSONa z kluczem "location"
            return ResponseEntity.ok(Map.of("location", fileUrl));

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of("error", "Błąd podczas przesyłania pliku."));
        }

    }
    @PostMapping("/image/blog")
    public ResponseEntity<?> uploadImageBlog(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty() || !file.getContentType().startsWith("image/")) {
            return ResponseEntity.badRequest().body(Map.of("error", "Nieprawidłowy plik. Proszę przesłać obraz."));
        }
        try {
            // 2. Stworzenie unikalnej nazwy pliku, aby uniknąć konfliktów
            String originalFilename = file.getOriginalFilename();
            String fileExtension = "";
            if (originalFilename != null && originalFilename.contains(".")) {
                fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
            }
            String uniqueFileName = UUID.randomUUID().toString() + fileExtension;

            // 3. Zapisanie pliku na serwerze
            Path uploadPath = Paths.get(uploadUrl);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath); // Utwórz folder, jeśli nie istnieje
            }
            Path filePath = uploadPath.resolve(uniqueFileName);
            Files.copy(file.getInputStream(), filePath);


            // 4. Zwrócenie JSON z lokalizacją pliku
            // Ścieżka musi być zgodna z tą, którą skonfigurujesz w WebMvcConfigurer
            String fileUrl = "static/blog/" + uniqueFileName;

            // Edytory WYSIWYG oczekują JSONa z kluczem "location
            return ResponseEntity.ok(Map.of("location", fileUrl));

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(Map.of("error", "Błąd podczas przesyłania pliku."));

        }
    }
}
