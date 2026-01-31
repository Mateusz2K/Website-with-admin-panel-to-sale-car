package pl.konkretnefury.konkretnefury.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.konkretnefury.konkretnefury.dto.ModelDTO;
import pl.konkretnefury.konkretnefury.modele.Brand;
import pl.konkretnefury.konkretnefury.service.BrandService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/models")
public class ModelApiController {

    private final BrandService brandService;

    public ModelApiController(BrandService brandService) {
        this.brandService = brandService;
    }

    /**
     * metoda do pobierania modeli z marki
     * @param brandId
     * @return Lista modeli z marki
     */
    @GetMapping("/by-brand/{brandId}")
    public List<ModelDTO> getModelsByBrand(@PathVariable UUID brandId) {
        Optional<Brand> brandOptional = brandService.getBrandById(brandId);
        if (brandOptional.isPresent()) {
            Brand brand = brandOptional.get();
            // Mapujemy listę encji BrandsModel na listę ModelDTO
            return brand.getLista_modeli().stream()
                    .map(model -> new ModelDTO(model.getId(), model.getModel()))
                    .collect(Collectors.toList());
        }
        // Jeśli marka nie istnieje, zwracamy pustą listę
        return Collections.emptyList();
    }
}
