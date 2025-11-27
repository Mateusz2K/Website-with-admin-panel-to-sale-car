package pl.konkretnefury.konkretnefury.format;

import org.springframework.format.AnnotationFormatterFactory;
import org.springframework.format.Parser;
import org.springframework.format.Printer;
import pl.konkretnefury.konkretnefury.format.annotation.PowerFormat;

import java.util.Set;

public class PowerFormatAnnotationFormatterFactory implements AnnotationFormatterFactory<PowerFormat> {
    @Override
    public Set<Class<?>> getFieldTypes() {
        return Set.of(Integer.class);
    }

    @Override
    public Printer<?> getPrinter(PowerFormat annotation, Class<?> fieldType) {
        return new PowerFormatter();
    }

    @Override
    public Parser<?> getParser(PowerFormat annotation, Class<?> fieldType) {
        return new PowerFormatter();
    }
}
