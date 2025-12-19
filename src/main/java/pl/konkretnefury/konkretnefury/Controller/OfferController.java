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
import pl.konkretnefury.konkretnefury.service.SystemSettingService;

import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/offers")
public class OfferController {

    private final CarOfferService carOfferService;
    private final BrandService brandService;
    private final SystemSettingService settingService; // NOWA ZALEŻNOŚĆ

    public OfferController(CarOfferService carOfferService, BrandService brandService, SystemSettingService settingService) {
        this.carOfferService = carOfferService;
        this.brandService = brandService;
        this.settingService = settingService;
    }

    @GetMapping
    public String listAllOffers(@ModelAttribute("filters") OfferFilterDTO filters, Model model, Pageable pageable) {
        model.addAttribute("offersPage", carOfferService.findWithFilters(filters, pageable));
        model.addAttribute("brands", brandService.getAllBrands());
        // ZMIANA: Przekazanie domyślnej grafiki do widoku
        model.addAttribute("defaultImageUrl", settingService.getDefaultOfferImageUrl());
        return "offers/offer-list";
    }

    @GetMapping("/{id}")
    public String offerDetails(@PathVariable UUID id, Model model) {
        Optional<CarOffer> offerOptional = carOfferService.getOfferById(id);
        if (offerOptional.isEmpty()) {
            return "redirect:/offers";
        }
        model.addAttribute("offer", offerOptional.get());
        // ZMIANA: Przekazanie domyślnej grafiki do widoku
        model.addAttribute("defaultImageUrl", settingService.getDefaultOfferImageUrl());
        return "offers/offer-details";
    }
}
