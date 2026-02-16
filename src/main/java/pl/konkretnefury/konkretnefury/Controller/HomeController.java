package pl.konkretnefury.konkretnefury.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.konkretnefury.konkretnefury.modele.GalleryImage;
import pl.konkretnefury.konkretnefury.service.BrandService;
import pl.konkretnefury.konkretnefury.service.CarOfferService;
import pl.konkretnefury.konkretnefury.service.GalleryService;
import pl.konkretnefury.konkretnefury.service.StatisticsService;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class HomeController {

    private final CarOfferService carOfferService;
    private final StatisticsService statisticsService;
    private final GalleryService galleryService; // NOWA ZALEŻNOŚĆ
    private final BrandService brandService;     // NOWA ZALEŻNOŚĆ

    public HomeController(CarOfferService carOfferService, StatisticsService statisticsService, GalleryService galleryService, BrandService brandService) {
        this.carOfferService = carOfferService;
        this.statisticsService = statisticsService;
        this.galleryService = galleryService;
        this.brandService = brandService;
    }

    @GetMapping("/")
    public String home(Model model) {
        statisticsService.recordVisit("HOME", null);
        
        model.addAttribute("featuredOffers", carOfferService.getFeaturedOffers());
        
        // ZMIANA: Przekazanie zdjęć galerii z bazy
        List<String> galleryImages = galleryService.getAllImages().stream()
                .map(GalleryImage::getFileName)
                .collect(Collectors.toList());
        model.addAttribute("galleryImages", galleryImages);

        // ZMIANA: Przekazanie listy marek
        model.addAttribute("brands", brandService.getAllBrands());

        model.addAttribute("activePage", "home");
        return "home";
    }
}
