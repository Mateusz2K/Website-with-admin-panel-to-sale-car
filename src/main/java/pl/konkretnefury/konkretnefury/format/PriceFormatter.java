package pl.konkretnefury.konkretnefury.format;

import org.springframework.format.Formatter;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

public class PriceFormatter implements Formatter<BigDecimal> {
    @Override
    public BigDecimal parse(String text, Locale locale) throws ParseException {
        // Usuwa spacje i zamienia przecinek na kropkę
        String normalized = text.replace(" ", "").replace(",", ".");
        return new BigDecimal(normalized);
    }

    @Override
    public String print(BigDecimal object, Locale locale) {
        NumberFormat nf = NumberFormat.getCurrencyInstance(locale);
        return nf.format(object);
    }
}
