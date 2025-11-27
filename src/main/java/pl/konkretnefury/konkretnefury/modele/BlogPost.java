package pl.konkretnefury.konkretnefury.modele;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "blog_post")
public class BlogPost {
    @Id
    @GeneratedValue
    private Long id;

    private String tytul;

    @Lob
    private String tresc;

    private LocalDate dataPublikacji;
    private String autor;

    public BlogPost() {
    }

    public Long getId() {
        return id;
    }

public void setId(Long id) {
        this.id = id;
    }

    public String getTytul() {
        return tytul;
    }

    public void setTytul(String tytul) {
        this.tytul = tytul;
    }

    public String getTresc() {
        return tresc;
    }

    public void setTresc(String tresc) {
        this.tresc = tresc;
    }

    public LocalDate getDataPublikacji() {
        return dataPublikacji;
    }

    public void setDataPublikacji(LocalDate dataPublikacji) {
        this.dataPublikacji = dataPublikacji;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }
}
