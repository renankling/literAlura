package br.com.alura.literAlura.model.repository;

import br.com.alura.literAlura.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LivroRepository extends JpaRepository<Livro, Long> {
    Optional<Livro> findByTituloContainingIgnoreCase(String nomeLivro);

    @Query("""
        SELECT l
        FROM Livro l
        WHERE LOWER(l.idioma) = LOWER(:idioma)
    """)
    List<Livro> buscarLivrosPorIdioma(@Param("idioma") String idioma);
}

