package br.com.alura.literAlura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDate;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosAutor(@JsonAlias("birth_year") Integer nascimento,
                         @JsonAlias("death_year") Integer morte,
                         @JsonAlias("name")  String nome) {
}
