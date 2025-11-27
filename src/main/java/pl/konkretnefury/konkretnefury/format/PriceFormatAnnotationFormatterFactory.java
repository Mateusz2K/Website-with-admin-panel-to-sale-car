package pl.konkretnefury.konkretnefury.format;

import org.springframework.format.AnnotationFormatterFactory;
import org.springframework.format.Parser;
import org.springframework.format.Printer;
import pl.konkretnefury.konkretnefury.format.annotation.PriceFormat;

import java.math.BigDecimal;
import java.util.Set;

public class PriceFormatAnnotationFormatterFactory implements AnnotationFormatterFactory<PriceFormat> {
    @Override
    public Set<Class<?>> getFieldTypes() {
        return Set.of(BigDecimal.class);
    }

    @Override
    public Printer<?> getPrinter(PriceFormat annotation, Class<?> fieldType) {
        return new PriceFormatter();
    }

    @Override
    public Parser<?> getParser(PriceFormat annotation, Class<?> fieldType) {
        return new PriceFormatter();
    }
}
