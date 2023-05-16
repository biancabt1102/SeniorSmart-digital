package br.com.fiap.seniorsmart.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiap.seniorsmart.models.Plano;

public interface PlanoRepository extends JpaRepository<Plano, Long>{

    Page<Plano> findByTipoPlanoContaining(String busca, Pageable pageable);

}
