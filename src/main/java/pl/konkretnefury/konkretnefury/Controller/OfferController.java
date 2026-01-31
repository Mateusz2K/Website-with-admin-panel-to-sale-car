package pl.konkretnefury.konkretnefury.Controller;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.konkretnefury.konkretnefury.dto.OfferFilterDTO;
import pl.konkretnefury.konkretnefury.modele.CarOffer;
import pl.konkretnefury.konkretnefury.service.BrandService;
import pl.konkretnefury.konkretnefury.service.CarOfferService;
import pl.konkretnefury.konkretnefury.service.StatisticsService;
import pl.konkretnefury.konkretnefury.service.SystemSettingService;

import java.util.Optional;
import java.util.UUID;

/**
 * Klasa Controller do przesyłania dla klientów ofert.
 */
@Controller
@RequestMapping("/offers")
public class OfferController {

    private final CarOfferService carOfferService;
    private final BrandService brandService;
    private final SystemSettingService settingService;
    private final StatisticsService statisticsService; // NOWA ZALEŻNOŚĆ

    public OfferController(CarOfferService carOfferService, BrandService brandService, SystemSettingService settingService, StatisticsService statisticsService) {
        this.carOfferService = carOfferService;
        this.brandService = brandService;
        this.settingService = settingService;
        this.statisticsService = statisticsService;
    }

    /**
     * Metoda do wyświetlenia wszystkich ofert wg filtrów
     * @param filters do filtrowania ofert
     * @param model do przesyłania obiektów
     * @param pageable do stron
     * @return kierowanie do strony z ofertami
     */
    @GetMapping
    public String listAllOffers(@ModelAttribute("filters") OfferFilterDTO filters, Model model, Pageable pageable) {
        long activeOffers = carOfferService.countActiveOffers();
        model.addAttribute("activeOffers", activeOffers);
        model.addAttribute("featuredOffers", carOfferService.getFeaturedOffers());
        model.addAttribute("offersPage", carOfferService.findWithFilters(filters, pageable));
        model.addAttribute("brands", brandService.getAllBrands());
        model.addAttribute("defaultImageUrl", settingService.getDefaultOfferImageUrl());
        model.addAttribute("activePage", "offers");
        return "offers/offer-list";
    }

    /**
     * Metoda do wyświetlania szczegółów oferty
     * @param id
     * @param model
     * @return strona przeglądowa oferty
     */
    @GetMapping("/{id}")
    public String offerDetails(@PathVariable UUID id, Model model) {
        Optional<CarOffer> offerOptional = carOfferService.getOfferById(id);
        if (offerOptional.isEmpty()) {
            return "redirect:/offers";
        }
        
        // ZMIANA: Rejestracja wizyty na ofercie
        statisticsService.recordVisit("OFFER", id.toString());
        
        model.addAttribute("offer", offerOptional.get());
        model.addAttribute("defaultImageUrl", settingService.getDefaultOfferImageUrl());
        model.addAttribute("activePage", "offers");
        return "offers/offer-details";
    }
}
