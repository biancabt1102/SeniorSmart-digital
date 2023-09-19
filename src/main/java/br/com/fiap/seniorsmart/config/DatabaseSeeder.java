package br.com.fiap.seniorsmart.config;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import br.com.fiap.seniorsmart.models.Pagamento;
import br.com.fiap.seniorsmart.models.Pergunta;
import br.com.fiap.seniorsmart.models.Plano;
import br.com.fiap.seniorsmart.models.Resposta;
import br.com.fiap.seniorsmart.models.Usuario;
import br.com.fiap.seniorsmart.repository.PagamentoRepository;
import br.com.fiap.seniorsmart.repository.PerguntaRepository;
import br.com.fiap.seniorsmart.repository.PlanoRepository;
import br.com.fiap.seniorsmart.repository.RespostaRepository;
import br.com.fiap.seniorsmart.repository.UsuarioRepository;

@Configuration
@Profile("dev")
public class DatabaseSeeder implements CommandLineRunner {

    @Autowired
    PagamentoRepository pagamentoRepository;

    @Autowired
    PerguntaRepository perguntaRepository;

    @Autowired
    PlanoRepository planoRepository;

    @Autowired
    RespostaRepository respostaRepository;

    @Autowired
    UsuarioRepository usuarioRepository;


    @Override
    public void run(String... args) throws Exception {
        Plano testeGratis = new Plano(1L, "Teste grátis", null, null);
        Plano planoMensal = new Plano(2L, "Plano mensal", new BigDecimal(35), null);
        Plano planoAnual = new Plano(3L, "Plano anual", null, new BigDecimal(378));
        planoRepository.saveAll(List.of(testeGratis, planoMensal,planoAnual));

        usuarioRepository.saveAll(List.of(
            Usuario.builder()
                .nome("Mario")
                .email("mariobrohters@gmail.com")
                .senha("@mamamia")
                .confirmarSenha("@mamamia")
                .data(LocalDate.of(1985, 9, 13))
                .telefone("(11) 9999-8888")
                .plano(testeGratis)
                .build(),
            Usuario.builder()
                .nome("Luigi")
                .email("luigibrohters@gmail.com")
                .senha("marioverde")
                .confirmarSenha("marioverde")
                .data(LocalDate.of(1985, 7, 14))
                .telefone("(11) 9999-7777")
                .plano(planoMensal)
                .build(),
            Usuario.builder()
                .nome("Peach")
                .email("princesspeach@gmail.com")
                .senha("iloveMario")
                .confirmarSenha("iloveMario")
                .data(LocalDate.of(1985, 9, 13))
                .telefone("(11) 9999-6666")
                .plano(planoAnual)
                .build(),
            Usuario.builder()
                .nome("Yoshi")
                .email("yoshi@gmail.com")
                .senha("eggs1234")
                .confirmarSenha("eggs1234")
                .data(LocalDate.of(1990, 8, 5))
                .telefone("(11) 9999-5555")
                .plano(planoMensal)
                .build(),
            Usuario.builder()
                .nome("Bowser")
                .email("bowser@gmail.com")
                .senha("PeachPeachPeachPeach")
                .confirmarSenha("PeachPeachPeachPeach")
                .data(LocalDate.of(1985, 5, 31))
                .telefone("(11) 8888-8888")
                .plano(testeGratis)
                .build()
        ));

        pagamentoRepository.saveAll(List.of(
            Pagamento.builder()
                .nomeNoCartao("ITS ME MARIO")
                .numeroDoCartao("9999888877776666")
                .validadeDoCartao(LocalDate.of(2023, 5, 31))
                .codigoDoCartao("789")
                .plano(testeGratis)
                .build(),
            Pagamento.builder()
                .nomeNoCartao("LUIGI")
                .numeroDoCartao("4539876543219876")
                .validadeDoCartao(LocalDate.of(2024, 8, 1))
                .codigoDoCartao("132")
                .plano(planoMensal)
                .build(),
            Pagamento.builder()
                .nomeNoCartao("PEACH")
                .numeroDoCartao("5467546754675467")
                .validadeDoCartao(LocalDate.of(2025, 2, 1))
                .codigoDoCartao("234")
                .plano(planoAnual)
                .build(),
            Pagamento.builder()
                .nomeNoCartao("YOSHI S.")
                .numeroDoCartao("9876987698769876")
                .validadeDoCartao(LocalDate.of(2026, 11, 1))
                .codigoDoCartao("345")
                .plano(planoMensal)
                .build(),
            Pagamento.builder()
                .nomeNoCartao("BOWSER KOOPA")
                .numeroDoCartao("1111222233334444")
                .validadeDoCartao(LocalDate.of(2025, 6, 30))
                .codigoDoCartao("123")
                .plano(testeGratis)
                .build()
        ));

        perguntaRepository.saveAll(List.of(
            Pergunta.builder()
                .pergunta("Como posso proteger minha privacidade online?")
                .build(),
            Pergunta.builder()
                .pergunta("Como faço para usar meu celular para acessar a internet?")
                .build(),
            Pergunta.builder()
                .pergunta("O que é um vírus de computador?")
                .build(),
            Pergunta.builder()
                .pergunta("Como posso manter meu computador seguro contra hackers?")
                .build(),
            Pergunta.builder()
                .pergunta("Como posso fazer chamadas de vídeo para minha família que mora longe?")
                .build()
        ));

        List<Pergunta> perguntas = perguntaRepository.findAll();

        respostaRepository.saveAll(List.of(
            Resposta.builder()
                .resposta("Existem várias maneiras de proteger sua privacidade online, como usar senhas seguras, não compartilhar informações pessoais com sites desconhecidos, usar antivírus, e evitar clicar em links suspeitos.")
                .pergunta(perguntas.get(0))
                .build(),
            Resposta.builder()
                .resposta("Para acessar a internet pelo celular, você precisa ter uma conexão de dados móveis ou uma rede Wi-Fi disponível. Depois disso, basta abrir o navegador do seu celular e digitar o endereço do site que deseja acessar.")
                .pergunta(perguntas.get(1))
                .build(),
            Resposta.builder()
                .resposta("Um vírus de computador é um programa malicioso que pode infectar um computador e danificar arquivos e sistemas. Eles podem se espalhar rapidamente pela internet e causar danos significativos.")
                .pergunta(perguntas.get(2))
                .build(),
            Resposta.builder()
                .resposta("Existem várias medidas que você pode tomar para manter seu computador seguro contra hackers, como manter o software atualizado, usar senhas seguras, ter um antivírus instalado, e evitar clicar em links suspeitos ou baixar arquivos de fontes desconhecidas.")
                .pergunta(perguntas.get(3))
                .build(),
            Resposta.builder()
                .resposta("Existem várias opções para fazer chamadas de vídeo, como o Skype, Zoom, Google Meet, entre outros. Você precisará de uma conexão com a internet e uma webcam para fazer as chamadas. Você também pode pedir ajuda para um parente ou amigo que já utiliza essas ferramentas para lhe orientar no início.")
                .pergunta(perguntas.get(4))
                .build()
        ));        
    }
}
