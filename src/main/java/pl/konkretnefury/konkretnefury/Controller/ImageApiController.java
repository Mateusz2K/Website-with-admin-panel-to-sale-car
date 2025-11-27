package pl.konkretnefury.konkretnefury.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.konkretnefury.konkretnefury.service.CarOfferImageService;

import java.util.UUID;

@RestController
@RequestMapping("/api/images")
public class ImageApiController {

    private final CarOfferImageService imageService;

    public ImageApiController(CarOfferImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping("/set-main/{imageId}")
    public ResponseEntity<?> setMainImage(@PathVariable UUID imageId) {
        imageService.setAsMainImage(imageId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{imageId}")
    public ResponseEntity<?> deleteImage(@PathVariable UUID imageId) {
        imageService.deleteImage(imageId);
        return ResponseEntity.ok().build();
    }
}
