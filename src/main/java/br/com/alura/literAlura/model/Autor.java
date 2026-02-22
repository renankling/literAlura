package br.com.alura.literAlura.model;


import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "autores")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer nascimento;
    private Integer morte;
    private String nome;


    @OneToMany(mappedBy = "autor")
    private List<Livro> livros = new ArrayList<>();

    public Autor() {}
    public Autor(String nome, Integer nascimento, Integer morte) {
        this.nome = nome;
        this.nascimento = nascimento;
        this.morte = morte;
    }

    public Integer getNascimento() {
        return nascimento;
    }

    public void setNascimento(Integer nascimento) {
        this.nascimento = nascimento;
    }

    public Integer getMorte() {
        return morte;
    }

    public void setMorte(Integer morte) {
        this.morte = morte;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        String livrosStr = livros.isEmpty()
                ? "Nenhum livro"
                : livros.stream()
                .map(Livro::getTitulo)
                .reduce((a, b) -> a + ", " + b)
                .orElse("");

        return """
            Nome: %s
            Nascimento: %s
            Falecimento: %s
            Livros: %s
            """.formatted(
                nome,
                nascimento != null ? nascimento : "Desconhecido",
                morte != null ? morte : "Vivo",
                livrosStr
        );
    }
}
