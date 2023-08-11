package br.com.fiap.seniorsmart.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.fiap.seniorsmart.models.Pagamento;

public interface PagamentoRepository extends JpaRepository<Pagamento, Long>{

    Page<Pagamento> findByNomeNoCartaoContaining(String busca, Pageable pageable);

    @Query("SELECT p FROM Pagamento p WHERE p.plano.id = :idPlano")
    Optional<Pagamento> findByPlanoId(@Param("idPlano") Long idPlano);
}
