package pl.konkretnefury.konkretnefury.Controller;

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

import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/offers")
public class OfferController {

    private final CarOfferService carOfferService;
    private final BrandService brandService;

    public OfferController(CarOfferService carOfferService, BrandService brandService) {
        this.carOfferService = carOfferService;
        this.brandService = brandService;
    }

    @GetMapping
    public String listAllOffers(@ModelAttribute("filters") OfferFilterDTO filters, Model model) {
        model.addAttribute("offers", carOfferService.findWithFilters(filters));
        model.addAttribute("brands", brandService.getAllBrands());
        return "offers/offer-list";
    }

    @GetMapping("/{id}")
    public String offerDetails(@PathVariable UUID id, Model model) {
        Optional<CarOffer> offerOptional = carOfferService.getOfferById(id);
        if (offerOptional.isEmpty()) {
            return "redirect:/offers";
        }
        model.addAttribute("offer", offerOptional.get());
        return "offers/offer-details";
    }
}
