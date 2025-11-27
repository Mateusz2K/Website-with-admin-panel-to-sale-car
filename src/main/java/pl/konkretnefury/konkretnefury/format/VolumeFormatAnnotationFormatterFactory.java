package pl.konkretnefury.konkretnefury.format;

import org.springframework.format.AnnotationFormatterFactory;
import org.springframework.format.Parser;
import org.springframework.format.Printer;
import pl.konkretnefury.konkretnefury.format.annotation.VolumeFormat;

import java.util.Set;

public class VolumeFormatAnnotationFormatterFactory implements AnnotationFormatterFactory<VolumeFormat> {
    @Override
    public Set<Class<?>> getFieldTypes() {
        return Set.of(Integer.class);
    }

    @Override
    public Printer<?> getPrinter(VolumeFormat annotation, Class<?> fieldType) {
        return new VolumeFormatter();
    }

    @Override
    public Parser<?> getParser(VolumeFormat annotation, Class<?> fieldType) {
        return new VolumeFormatter();
    }
}
