package pl.konkretnefury.konkretnefury.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.konkretnefury.konkretnefury.modele.GalleryImage;
import pl.konkretnefury.konkretnefury.service.GalleryService;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class PageController {

    private final GalleryService galleryService;

    public PageController(GalleryService galleryService) {
        this.galleryService = galleryService;
    }

    @GetMapping("/onas")
    public String aboutPage(Model model) {
        model.addAttribute("activePage", "onas");
        return "onas";
    }

    @GetMapping("/kontakt")
    public String contactPage(Model model) {
        model.addAttribute("activePage", "kontakt");
        return "kontakt";
    }

    @GetMapping("/galeria")
    public String galleryPage(Model model) {
        // Pobieramy zdjęcia z bazy danych
        List<String> imageUrls = galleryService.getAllImages().stream()
                .map(GalleryImage::getFileName)
                .collect(Collectors.toList());
        
        model.addAttribute("galleryImages", imageUrls);
        model.addAttribute("activePage", "galeria");
        return "galeria";
    }
}
