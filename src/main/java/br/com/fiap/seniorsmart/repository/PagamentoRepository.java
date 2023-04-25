package br.com.fiap.seniorsmart.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fiap.seniorsmart.models.Pagamento;

public interface PagamentoRepository extends JpaRepository<Pagamento, Long>{
}
