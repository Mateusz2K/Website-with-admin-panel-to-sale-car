package pl.konkretnefury.konkretnefury.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.konkretnefury.konkretnefury.modele.Brand;
import pl.konkretnefury.konkretnefury.modele.BrandsModel;
import pl.konkretnefury.konkretnefury.service.BrandService;

import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/admin/brands")
public class AdminBrandController {

    private final BrandService brandService;

    public AdminBrandController(BrandService brandService) {
        this.brandService = brandService;
    }

    @GetMapping
    public String listBrands(Model model) {
        model.addAttribute("brands", brandService.getAllBrands());
        return "admin/brands/brand-list";
    }

    // NOWOŚĆ: Strona ze szczegółami marki i listą jej modeli
    @GetMapping("/{id}")
    public String brandDetails(@PathVariable UUID id, Model model) {
        Optional<Brand> brandOptional = brandService.getBrandById(id);
        if (brandOptional.isEmpty()) {
            return "redirect:/admin/brands";
        }
        model.addAttribute("brand", brandOptional.get());
        return "admin/brands/brand-details";
    }

    @GetMapping("/new")
    public String newBrandForm(Model model) {
        model.addAttribute("brand", new Brand());
        return "admin/brands/brand-form";
    }

    @GetMapping("/edit/{id}")
    public String editBrandForm(@PathVariable UUID id, Model model) {
        brandService.getBrandById(id).ifPresent(brand -> model.addAttribute("brand", brand));
        return "admin/brands/brand-form";
    }

    @PostMapping
    public String saveBrand(@ModelAttribute Brand brand, @RequestParam("iconFile") MultipartFile iconFile) {
        brandService.saveBrandWithIcon(brand, iconFile);
        return "redirect:/admin/brands";
    }

    @GetMapping("/delete/{id}")
    public String deleteBrand(@PathVariable UUID id) {
        brandService.deleteBrand(id);
        return "redirect:/admin/brands";
    }

    // --- Zarządzanie Modelami ---

    @GetMapping("/{brandId}/models/new")
    public String newModelForm(@PathVariable UUID brandId, Model model) {
        brandService.getBrandById(brandId).ifPresent(brand -> {
            BrandsModel newModel = new BrandsModel();
            newModel.setBrand(brand);
            model.addAttribute("model", newModel);
            model.addAttribute("brand", brand);
        });
        return "admin/brands/model-form";
    }

    // NOWOŚĆ: Formularz edycji modelu
    @GetMapping("/models/edit/{modelId}")
    public String editModelForm(@PathVariable UUID modelId, Model model) {
        Optional<BrandsModel> modelOptional = brandService.getModelById(modelId);
        if (modelOptional.isEmpty()) {
            return "redirect:/admin/brands";
        }
        BrandsModel existingModel = modelOptional.get();
        model.addAttribute("model", existingModel);
        model.addAttribute("brand", existingModel.getBrand()); // Przekazujemy markę do widoku
        return "admin/brands/model-form";
    }

    @PostMapping("/models")
    public String saveModel(@ModelAttribute BrandsModel model) {
        brandService.saveModel(model);
        // Przekierowujemy na stronę szczegółów marki, do której należy model
        return "redirect:/admin/brands/" + model.getBrand().getId();
    }

    // NOWOŚĆ: Usuwanie modelu
    @GetMapping("/models/delete/{modelId}")
    public String deleteModel(@PathVariable UUID modelId) {
        // Pobieramy model, aby dowiedzieć się, do jakiej marki należy, przed usunięciem
        Optional<BrandsModel> modelOptional = brandService.getModelById(modelId);
        if (modelOptional.isPresent()) {
            UUID brandId = modelOptional.get().getBrand().getId();
            brandService.deleteModel(modelId);
            return "redirect:/admin/brands/" + brandId;
        }
        return "redirect:/admin/brands";
    }
}
