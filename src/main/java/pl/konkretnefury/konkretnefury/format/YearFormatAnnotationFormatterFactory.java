package pl.konkretnefury.konkretnefury.format;

import org.springframework.format.AnnotationFormatterFactory;
import org.springframework.format.Parser;
import org.springframework.format.Printer;
import pl.konkretnefury.konkretnefury.format.annotation.YearFormat;


import java.util.Set;

public class YearFormatAnnotationFormatterFactory implements AnnotationFormatterFactory<YearFormat> {
    @Override
    public Set<Class<?>> getFieldTypes() {
        return Set.of(Integer.class);
    }

    @Override
    public Printer<?> getPrinter(YearFormat annotation, Class<?> fieldType) {
        return new YearFormater();
    }

    @Override
    public Parser<?> getParser(YearFormat annotation, Class<?> fieldType) {
        return new YearFormater();
    }
}
