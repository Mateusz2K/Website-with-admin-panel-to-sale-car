package pl.konkretnefury.konkretnefury.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.konkretnefury.konkretnefury.dto.OfferFilterDTO;
import pl.konkretnefury.konkretnefury.modele.CarOffer;
import pl.konkretnefury.konkretnefury.service.BrandService;
import pl.konkretnefury.konkretnefury.service.CarOfferImageService;
import pl.konkretnefury.konkretnefury.service.CarOfferService;
import pl.konkretnefury.konkretnefury.service.CarOptionService;
import pl.konkretnefury.konkretnefury.service.SystemSettingService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/admin/offers")
public class AdminOfferController {

    private final CarOfferService carOfferService;
    private final BrandService brandService;
    private final CarOfferImageService carOfferImageService;
    private final CarOptionService carOptionService;
    private final SystemSettingService settingService;

    public AdminOfferController(CarOfferService carOfferService, BrandService brandService, CarOfferImageService carOfferImageService, CarOptionService carOptionService, SystemSettingService settingService) {
        this.carOfferService = carOfferService;
        this.brandService = brandService;
        this.carOfferImageService = carOfferImageService;
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
        
        String defaultWarranty = settingService.getDefaultWarrantyDescription();
        if (defaultWarranty != null && !defaultWarranty.isEmpty()) {
            newOffer.setGwarancjaOpis(defaultWarranty);
        }

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

    @PostMapping("/save")
    public String saveOffer(@ModelAttribute CarOffer offer, @RequestParam(value = "images", required = false) MultipartFile[] images) {
        carOfferService.saveOfferWithImages(offer, images);
        return "redirect:/admin/offers";
    }

    @GetMapping("/delete/{id}")
    public String deleteOffer(@PathVariable UUID id) {
        carOfferService.deleteOffer(id);
        return "redirect:/admin/offers";
    }

    @GetMapping("/set-main/{offerId}/{imageId}")
    public String setMainImage(@PathVariable UUID offerId, @PathVariable UUID imageId) {
        carOfferImageService.setAsMainImage(imageId);
        return "redirect:/admin/offers/edit/" + offerId;
    }

    @GetMapping("/delete-image/{offerId}/{imageId}")
    public String deleteImage(@PathVariable UUID offerId, @PathVariable UUID imageId) {
        carOfferImageService.deleteImage(imageId);
        return "redirect:/admin/offers/edit/" + offerId;
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
    
    // ZMIANA: Endpoint do zmiany kolejności zdjęć
    @PostMapping("/reorder-images")
    @ResponseBody
    public ResponseEntity<?> reorderImages(@RequestBody List<UUID> orderedIds) {
        carOfferService.updateImageOrder(orderedIds);
        return ResponseEntity.ok().build();
    }
}
