//package pl.konkretnefury.konkretnefury.Controller;
//
//import jakarta.persistence.EntityNotFoundException;
//import jakarta.validation.Valid;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//import pl.konkretnefury.konkretnefury.modele.CarOffer;
//import pl.konkretnefury.konkretnefury.service.CarOfferService;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.NoSuchElementException;
//import java.util.UUID;
//
//@Controller
//@RequestMapping("/admin/car")
//@SessionAttributes("carOffer")
//
//public class CarOfferController {
//    private final CarOfferService carOfferService;
//    @Autowired
//    public CarOfferController(CarOfferService carOfferService) {
//        this.carOfferService = carOfferService;
//    }
//    @GetMapping
//    public String listOffers(Model model) {
//        model.addAttribute("offers", carOfferService.findLast5Offers());
//        return "admin/car/car-offer";
//    }
//
//    @GetMapping("/new")
//    @PreAuthorize("hasRole('ADMIN')")
//    public String newOfferForm(Model model) {
//        model.addAttribute("carOffer", new CarOffer());
//        return "admin/car/new/new-car-offer";
//    }
//
//    @PostMapping("/new")
//    @PreAuthorize("hasRole('ADMIN')")
//    public String saveOffer(
//            @Valid @ModelAttribute CarOffer carOffer,
//            BindingResult result,
//            @RequestParam("images") MultipartFile[] images) {
//
//        if (result.hasErrors()) {
//            return "admin/car/new/new-car-offer";
//        }
//
//        carOfferService.saveOfferWithImages(carOffer, images);
//        System.out.println("✔️ Wszedłem do saveOffer(), zapisano ofertę");
//        return "redirect:/admin/car";
//    }
//
//    @GetMapping("/edit/{id}")
//    @PreAuthorize("hasRole('ADMIN')")
//    public String editOfferForm(@PathVariable UUID id, Model model) {
//        model.addAttribute("carOffer", carOfferService.getOfferById(id));
//        return "admin/new-car-offer";
//    }
//
//    @GetMapping("/delete/{id}")
//    @PreAuthorize("hasRole('ADMIN')")
//    public ResponseEntity<?> deleteOffer(@PathVariable UUID id) {
//        try{
//            carOfferService.getOfferById(id);
//            carOfferService.deleteOffer(id);
//            return ResponseEntity.accepted().body("Oferta została usunięta.");
//        } catch (EntityNotFoundException e){
//            return ResponseEntity.notFound().build();
//        }
//        catch (IllegalArgumentException e){
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
//
//    }
//    @PostMapping("/status")
//    @PreAuthorize("hasRole('ADMIN')")
//    public ResponseEntity<?> changeStatusOfOffer(@PathVariable UUID id,@RequestBody String Status){
//        try{
//            carOfferService.changeStatusOfOffer(id,Status);
//            return ResponseEntity.ok().body("Status oferty został zmieniony.");
//
//        } catch (EntityNotFoundException e){
//            return ResponseEntity.notFound().build();
//        }
//        catch (IllegalArgumentException e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
//    }
//    @GetMapping("/featured")
//    public ResponseEntity<?> getOfferFeatured() {
//        try{
//            List<CarOffer>listFeatured = carOfferService.getFeaturedOffers().stream().toList();
//            return ResponseEntity.ok(listFeatured);
//        } catch (NoSuchElementException e) {
//            return ResponseEntity.notFound().build();
//        } catch (IllegalArgumentException e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
//    }
//    @PutMapping("/featured")
//    public  ResponseEntity<?> setFeaturedOffer(@PathVariable UUID id){
//        try{
//            carOfferService.toggleFeaturedOffer(id);
//            return ResponseEntity.accepted().body("Oferta została za aktualizowana do promowania");
//        }
//        catch (EntityNotFoundException e){
//            return ResponseEntity.notFound().build();
//        }
//        catch (IllegalArgumentException e){
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
//    }
//}
