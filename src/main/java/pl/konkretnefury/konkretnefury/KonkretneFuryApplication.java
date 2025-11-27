package pl.konkretnefury.konkretnefury;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.spring6.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.templatemode.TemplateMode;

@SpringBootApplication
public class KonkretneFuryApplication {

    public static void main(String[] args) {
        SpringApplication.run(KonkretneFuryApplication.class, args);
    }


}
