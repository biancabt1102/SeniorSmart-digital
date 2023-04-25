package br.com.fiap.seniorsmart.models;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "T_SS_PLANO")
public class Plano {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cd_plano")
    private Long id;
    @NotNull
    @Column(name = "ds_plano")
    private String tipoPlano;
    @Column(name = "vl_plano_mensal")
    private BigDecimal planoMensal;
    @Column(name = "vl_plano_anual")
    private BigDecimal planoAnual;
}
