package br.com.fiap.seniorsmart.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiap.seniorsmart.models.Pagamento;

public interface PagamentoRepository extends JpaRepository<Pagamento, Long>{

    Page<Pagamento> findByNomeNoCartaoContaining(String busca, Pageable pageable);
}
