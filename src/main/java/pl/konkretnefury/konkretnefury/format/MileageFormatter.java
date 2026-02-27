package pl.konkretnefury.konkretnefury.format;

import org.springframework.format.Formatter;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

// ZMIANA: Formater działa teraz na Integer, a nie na String
public class MileageFormatter implements Formatter<Integer> {
    @Override
    public Integer parse(String text, Locale locale) throws ParseException {
        // Usuwa wszystko, co nie jest cyfrą
        String cleanText = text.replaceAll("[^\\d]", "");
        return Integer.parseInt(cleanText);
    }

    @Override
    public String print(Integer object, Locale locale) {
        // Używa NumberFormat do poprawnego formatowania liczby
        return NumberFormat.getNumberInstance(locale).format(object) + " km";
    }
    public String print(Integer object){
        return print(object, Locale.getDefault());
    }
}
