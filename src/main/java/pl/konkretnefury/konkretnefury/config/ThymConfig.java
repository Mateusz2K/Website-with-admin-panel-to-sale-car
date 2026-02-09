package pl.konkretnefury.konkretnefury.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import pl.konkretnefury.konkretnefury.converter.StringToCarOptionConverter;
import pl.konkretnefury.konkretnefury.format.*;
import pl.konkretnefury.konkretnefury.format.annotation.*;

@Configuration
public class ThymConfig implements WebMvcConfigurer {

    @Autowired
    private StringToCarOptionConverter stringToCarOptionConverter;

    @Value("${file.upload-dir.cars}")
    private String carPhotoUploadDir;

    @Value("${file.upload-dir.icons}")
    private String iconUploadDir;
    
    @Value("${file.upload-dir.gallery}")
    private String galleryUploadDir;

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addFormatterForFieldAnnotation(new PriceFormatAnnotationFormatterFactory());
        registry.addFormatterForFieldAnnotation(new MileageFormatAnnotationFormatterFactory());
        registry.addFormatterForFieldAnnotation(new PowerFormatAnnotationFormatterFactory());
        registry.addFormatterForFieldAnnotation(new VolumeFormatAnnotationFormatterFactory());
        registry.addFormatterForFieldAnnotation(new YearFormatAnnotationFormatterFactory());
        
        registry.addConverter(stringToCarOptionConverter);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Mapowanie dla zdjęć ofert (ścieżki typu /car_photo/...)
        registry.addResourceHandler("/car_photo/**").addResourceLocations("file:" + carPhotoUploadDir + "/");
        
        // Mapowanie dla domyślnego zdjęcia (ścieżki typu /uploads/car_photos/...)
        registry.addResourceHandler("/uploads/car_photos/**").addResourceLocations("file:" + carPhotoUploadDir + "/");
        
        registry.addResourceHandler("/uploads/icons/**").addResourceLocations("file:" + iconUploadDir + "/");
        registry.addResourceHandler("/uploads/gallery/**").addResourceLocations("file:" + galleryUploadDir + "/");
    }

    @Bean
    public PriceFormatter priceFormatter() { return new PriceFormatter(); }
    @Bean
    public MileageFormatter mileageFormatter() { return new MileageFormatter(); }
    @Bean
    public PowerFormatter powerFormatter() { return new PowerFormatter(); }
    @Bean
    public VolumeFormatter volumeFormatter() { return new VolumeFormatter(); }
    @Bean
    public YearFormater yearFormatter() { return new YearFormater(); }
}
