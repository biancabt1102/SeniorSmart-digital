package br.com.fiap.seniorsmart.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiap.seniorsmart.models.Resposta;

public interface RespostaRepository extends JpaRepository<Resposta, Long>{

    Page<Resposta> findByRespostaContaining(String busca, Pageable pageable);

}
