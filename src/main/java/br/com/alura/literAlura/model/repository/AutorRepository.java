package br.com.alura.literAlura.model.repository;

import br.com.alura.literAlura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AutorRepository extends JpaRepository<Autor, Long> {

    Optional<Autor> findByNomeIgnoreCase(String nome);

    @Query("""
        SELECT DISTINCT a
        FROM Autor a
        LEFT JOIN FETCH a.livros
    """)
    List<Autor> buscarAutoresComLivros();


    @Query("SELECT a FROM Autor a LEFT JOIN FETCH a.livros WHERE a.nascimento <= :ano AND (a.morte IS NULL OR a.morte >= :ano)")
    List<Autor> listarAutoresPorAno(Integer ano);
}

