package pl.konkretnefury.konkretnefury.format;

import org.springframework.format.Formatter;
import java.text.ParseException;
import java.util.Locale;

// ZMIANA: Formater działa teraz na Integer
public class PowerFormatter implements Formatter<Integer> {
    @Override
    public Integer parse(String text, Locale locale) throws ParseException {
        String cleanText = text.replaceAll("[^\\d]", "");
        return Integer.parseInt(cleanText);
    }

    @Override
    public String print(Integer object, Locale locale) {
        return object + " KM";
    }
}
