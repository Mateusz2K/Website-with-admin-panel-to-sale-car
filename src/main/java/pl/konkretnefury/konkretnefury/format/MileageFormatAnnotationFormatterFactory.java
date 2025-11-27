package pl.konkretnefury.konkretnefury.format;

import org.springframework.format.AnnotationFormatterFactory;
import org.springframework.format.Parser;
import org.springframework.format.Printer;
import pl.konkretnefury.konkretnefury.format.annotation.MileageFormat;

import java.util.Set;

public class MileageFormatAnnotationFormatterFactory implements AnnotationFormatterFactory<MileageFormat> {
    @Override
    public Set<Class<?>> getFieldTypes() {
        return Set.of(Integer.class);
    }

    @Override
    public Printer<?> getPrinter(MileageFormat annotation, Class<?> fieldType) {
        return new MileageFormatter();
    }

    @Override
    public Parser<?> getParser(MileageFormat annotation, Class<?> fieldType) {
        return new MileageFormatter();
    }
}
