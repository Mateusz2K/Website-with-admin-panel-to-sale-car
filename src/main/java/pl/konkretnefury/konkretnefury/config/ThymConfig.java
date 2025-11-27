package pl.konkretnefury.konkretnefury.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import pl.konkretnefury.konkretnefury.converter.StringToCarOptionConverter;
import pl.konkretnefury.konkretnefury.format.MileageFormatAnnotationFormatterFactory;
import pl.konkretnefury.konkretnefury.format.PowerFormatAnnotationFormatterFactory;
import pl.konkretnefury.konkretnefury.format.PriceFormatAnnotationFormatterFactory;
import pl.konkretnefury.konkretnefury.format.VolumeFormatAnnotationFormatterFactory;

@Configuration
public class ThymConfig implements WebMvcConfigurer {

    @Autowired
    private StringToCarOptionConverter stringToCarOptionConverter;

    @Value("${file.upload-dir.cars}")
    private String carPhotoUploadDir;

    @Value("${file.upload-dir.icons}")
    private String iconUploadDir;

    @Override
    public void addFormatters(FormatterRegistry registry) {
        // ZMIANA: Dodano fabrykę dla formatowania ceny
        registry.addFormatterForFieldAnnotation(new PriceFormatAnnotationFormatterFactory());
        registry.addFormatterForFieldAnnotation(new MileageFormatAnnotationFormatterFactory());
        registry.addFormatterForFieldAnnotation(new PowerFormatAnnotationFormatterFactory());
        registry.addFormatterForFieldAnnotation(new VolumeFormatAnnotationFormatterFactory());
        
        registry.addConverter(stringToCarOptionConverter);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/car_photo/**").addResourceLocations("file:" + carPhotoUploadDir + "/");
        registry.addResourceHandler("/uploads/icons/**").addResourceLocations("file:" + iconUploadDir + "/");
    }
}
