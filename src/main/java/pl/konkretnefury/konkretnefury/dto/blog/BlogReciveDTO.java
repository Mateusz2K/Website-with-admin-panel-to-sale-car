package pl.konkretnefury.konkretnefury.dto.blog;

public class BlogReciveDTO {
    private Long id;
    private String tytul;
    private String tresc;
    private String autor;
    private String dataPublikacji;

    public BlogReciveDTO() {
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

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getDataPublikacji() {
        return dataPublikacji;
    }

}
