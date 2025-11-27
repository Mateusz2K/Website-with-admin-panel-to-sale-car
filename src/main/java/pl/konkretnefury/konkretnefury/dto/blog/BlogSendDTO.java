package pl.konkretnefury.konkretnefury.dto.blog;

import java.time.LocalDate;

public class BlogSendDTO {
    private Long id;
    private String tytul;
    private String tresc;
    private LocalDate dataPublikacji;
    private String autor;

    public BlogSendDTO() {
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
