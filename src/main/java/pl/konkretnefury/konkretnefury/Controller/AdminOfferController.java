package pl.konkretnefury.konkretnefury.Controller;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.konkretnefury.konkretnefury.dto.OfferFilterDTO;
import pl.konkretnefury.konkretnefury.modele.CarOffer;
import pl.konkretnefury.konkretnefury.service.BrandService;
import pl.konkretnefury.konkretnefury.service.CarOfferService;
import pl.konkretnefury.konkretnefury.service.CarOptionService;
import pl.konkretnefury.konkretnefury.service.SystemSettingService;

import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/admin/offers")
public class AdminOfferController {

    private final CarOfferService carOfferService;
    private final BrandService brandService;
    private final CarOptionService carOptionService;
    private final SystemSettingService settingService;

    public AdminOfferController(CarOfferService carOfferService, BrandService brandService, CarOptionService carOptionService, SystemSettingService settingService) {
        this.carOfferService = carOfferService;
        this.brandService = brandService;
        this.carOptionService = carOptionService;
        this.settingService = settingService;
    }

    @GetMapping
    public String listOffers(@ModelAttribute("filters") OfferFilterDTO filters, Model model, org.springframework.data.domain.Pageable pageable) {
        model.addAttribute("offersPage", carOfferService.findWithFilters(filters, pageable));
        model.addAttribute("brands", brandService.getAllBrands());
        return "admin/offers/offer-list-admin";
    }

    @GetMapping("/new")
    public String newOfferForm(Model model) {
        CarOffer newOffer = new CarOffer();
        
        // Ustawienie domyślnej gwarancji
        String defaultWarranty = settingService.getDefaultWarrantyDescription();
        if (defaultWarranty != null && !defaultWarranty.isEmpty()) {
            newOffer.setGwarancjaOpis(defaultWarranty);
        }

        // ZMIANA: Ustawienie domyślnego opisu
        String defaultDescription = settingService.getDefaultOfferDescription();
        if (defaultDescription != null && !defaultDescription.isEmpty()) {
            newOffer.setOpis(defaultDescription);
        }
        
        model.addAttribute("offer", newOffer);
        model.addAttribute("brands", brandService.getAllBrands());
        model.addAttribute("allOptions", carOptionService.findAll());
        return "admin/offers/offer-form";
    }

    @GetMapping("/edit/{id}")
    public String editOfferForm(@PathVariable UUID id, Model model) {
        Optional<CarOffer> offerOptional = carOfferService.getOfferById(id);
        if (offerOptional.isEmpty()) {
            return "redirect:/admin/offers";
        }
        model.addAttribute("offer", offerOptional.get());
        model.addAttribute("brands", brandService.getAllBrands());
        model.addAttribute("allOptions", carOptionService.findAll());
        return "admin/offers/offer-form";
    }

    @PostMapping
    public String saveOffer(@Valid @ModelAttribute("offer") CarOffer offer,
                            BindingResult result,
                            @RequestParam("images") MultipartFile[] images,
                            Model model) {
        if (result.hasErrors()) {
            model.addAttribute("brands", brandService.getAllBrands());
            model.addAttribute("allOptions", carOptionService.findAll());
            return "admin/offers/offer-form";
        }
        carOfferService.saveOfferWithImages(offer, images);
        return "redirect:/admin/offers";
    }

    @GetMapping("/delete/{id}")
    public String deleteOffer(@PathVariable UUID id) {
        carOfferService.deleteOffer(id);
        return "redirect:/admin/offers";
    }

    @GetMapping("/toggle-featured/{id}")
    public String toggleFeatured(@PathVariable UUID id) {
        carOfferService.toggleFeaturedOffer(id);
        return "redirect:/admin/offers";
    }

    @PostMapping("/status/{id}")
    public String changeStatus(@PathVariable UUID id, @RequestParam String status) {
        carOfferService.changeStatusOfOffer(id, status);
        return "redirect:/admin/offers";
    }
}
