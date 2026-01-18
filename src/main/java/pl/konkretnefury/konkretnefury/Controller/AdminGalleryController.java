package pl.konkretnefury.konkretnefury.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.konkretnefury.konkretnefury.service.GalleryService;

import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/admin/gallery")
public class AdminGalleryController {

    private final GalleryService galleryService;

    public AdminGalleryController(GalleryService galleryService) {
        this.galleryService = galleryService;
    }

    @GetMapping
    public String galleryList(Model model) {
        model.addAttribute("images", galleryService.getAllImages());
        return "admin/gallery/gallery-list";
    }

    @PostMapping("/upload")
    public String uploadImages(@RequestParam("images") MultipartFile[] images) {
        galleryService.addImages(images);
        return "redirect:/admin/gallery";
    }

    @GetMapping("/delete/{id}")
    public String deleteImage(@PathVariable UUID id) {
        galleryService.deleteImage(id);
        return "redirect:/admin/gallery";
    }
    
    // Endpoint API do zmiany kolejności (dla JavaScriptu)
    @PostMapping("/reorder")
    @ResponseBody
    public ResponseEntity<?> reorderImages(@RequestBody List<UUID> orderedIds) {
        galleryService.updateOrder(orderedIds);
        return ResponseEntity.ok().build();
    }
}
