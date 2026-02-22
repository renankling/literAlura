package br.com.alura.literAlura.service;

import br.com.alura.literAlura.model.Autor;
import br.com.alura.literAlura.model.DadosLivro;
import br.com.alura.literAlura.model.Livro;
import br.com.alura.literAlura.model.repository.AutorRepository;
import br.com.alura.literAlura.model.repository.LivroRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LivroService {
    private static final Logger log = LoggerFactory.getLogger(LivroService.class);
    @Autowired
    private LivroRepository repositorio;
    @Autowired
    private AutorRepository autorRepository;

    public void salvarLivro(DadosLivro dadosLivro) {

        var dadosAutor = dadosLivro.autor().get(0);

        Autor autor = autorRepository
                .findByNomeIgnoreCase(dadosAutor.nome())
                .orElseGet(() -> autorRepository.save(
                        new Autor(
                                dadosAutor.nome(),
                                dadosAutor.nascimento(),
                                dadosAutor.morte()
                        )
                ));
        Livro livro = new Livro(dadosLivro);

        livro.setAutor(autor);
        repositorio.save(livro);
        Livro livroSalvo = repositorio.findById(livro.getId()).get();
        System.out.println(livroSalvo);
    }


    public List<Livro> findAll() {
        return repositorio.findAll();
    }

    public List<Autor> listarAutores() {
        return autorRepository.buscarAutoresComLivros();
    }

    public List<Autor> listarAutoresPorAno(int ano) {
        return  autorRepository.listarAutoresPorAno(ano);
    }

    public List<Livro> listarLivrosPorIdioma(String idioma) {
        return repositorio.buscarLivrosPorIdioma(idioma);
    }
}
