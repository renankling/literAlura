package br.com.alura.literAlura.principal;

import br.com.alura.literAlura.model.Autor;
import br.com.alura.literAlura.model.DadosLivro;
import br.com.alura.literAlura.model.DadosResposta;
import br.com.alura.literAlura.model.Livro;
import br.com.alura.literAlura.service.ConsumoAPI;
import br.com.alura.literAlura.service.ConverteDados;
import br.com.alura.literAlura.service.LivroService;

import java.util.List;
import java.util.Scanner;

public class Principal {
    private Scanner leitura = new Scanner(System.in);
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private ConverteDados conversor =  new ConverteDados();
    private LivroService repositorio;
    public Principal(LivroService repositorio) {
        this.repositorio  = repositorio;
    }




    public void exibeMenu(){
        var opcao = -1;
        while (opcao != 0) {
            var menu = """
                    1 - Buscar livro pelo titulo
                    2 - Listar livros registrados
                    3 - Listar autores registrados
                    4 - Listar autores vivos em um determinado ano
                    5 - Listar livros em um determinado idioma
                    0 - Sair
                    """;

            System.out.println(menu);
            opcao = leitura.nextInt();
            leitura.nextLine();

            switch (opcao) {
                case 1:
                    buscarLivro();
                    break;
                case 2:
                    livrosRegistro();
                    break;
                case 3:
                    autoresRegistro();
                    break;
                case 4:
                    autoresPorAno();
                    break;
                case 5:
                    livrosIdioma();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida");
            }

        }

    }




    private void buscarLivro() {
        DadosLivro dados = getDadosLivro();

        if (dados == null) return;

        repositorio.salvarLivro(dados);

    }

    private DadosLivro getDadosLivro() {
        System.out.println("Digite o nome do livro: ");
        String nome = leitura.nextLine();

        var json = consumoAPI.obterDados(
                "https://gutendex.com/books/?search=" + nome.replace(" ", "+")
        );

        DadosResposta resposta = conversor.obterDados(json, DadosResposta.class);

        if (resposta.results() == null || resposta.results().isEmpty()) {
            System.out.println("Nenhum livro encontrado.");
            return null;
        }
        System.out.println(json);
        return resposta.results().get(0);
    }

    private void livrosRegistro() {
        List<Livro> livros = repositorio.findAll();
        livros.stream()
                .forEach(System.out::println);

    }

    private void autoresRegistro() {
        List<Autor> autores = repositorio.listarAutores();
        autores.stream()
                .forEach(System.out::println);
    }

    private void autoresPorAno() {
        System.out.println("Digite o ano que deseja buscar: ");
        try{
            int ano = Integer.parseInt(leitura.nextLine());
            List<Autor> autoresVivos = repositorio.listarAutoresPorAno(ano);
            autoresVivos.stream()
                    .forEach(System.out::println);
            if(autoresVivos.isEmpty()){
                System.out.println("Nenhum autor vivo nesse ano");
            }
        }catch(NumberFormatException  e){
            System.out.println("Ano inválido. Digite apenas números.");
        }

    }

    private void livrosIdioma() {
        System.out.print("""
            Digite o idioma que deseja buscar: 
            es - espanhol
            en - inglês
            fr - francês
            pt - português
            """);
        String idioma = leitura.nextLine().trim();

        List<Livro> livros = repositorio.listarLivrosPorIdioma(idioma);

        if (livros.isEmpty()) {
            System.out.println("Nenhum livro encontrado para o idioma informado.");
            return;
        }

        livros.forEach(System.out::println);
    }
}
