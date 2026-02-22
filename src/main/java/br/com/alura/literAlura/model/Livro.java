package br.com.alura.literAlura.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "livros")
public class Livro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;

    private String idioma;
    private Double downloads;



    @ManyToOne
    private Autor autor;

    public Livro() {}

    public Livro(DadosLivro dadosLivro) {
        this.titulo = dadosLivro.titulo();
        this.idioma = dadosLivro.idioma() != null && !dadosLivro.idioma().isEmpty()
                ? dadosLivro.idioma().get(0)
                : "Idioma desconhecido";
        this.downloads = dadosLivro.downloads();
    }



    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public Double getDownloads() {
        return downloads;
    }

    public void setDownloads(Double downloads) {
        this.downloads = downloads;
    }

    @Override
    public String toString() {
        return """
                Título: %s
                Autor: %s
                Idioma: %s
                Downloads: %s
                """.formatted(
                        titulo,
                        autor != null ? autor.getNome() : "Autor desconhecido",
                        idioma,
                        downloads);
    }

    public Long getId() {
        return id;
    }
}


