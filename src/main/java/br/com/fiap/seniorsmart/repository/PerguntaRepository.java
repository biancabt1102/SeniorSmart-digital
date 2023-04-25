package br.com.fiap.seniorsmart.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiap.seniorsmart.models.Pergunta;

public interface PerguntaRepository extends JpaRepository<Pergunta, Long>{

    Page<Pergunta> findByPerguntaContaining(String busca, Pageable pageable);

}
