package br.com.fiap.seniorsmart.models;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "T_SS_FORMA_DE_PAGAMENTO")
public class Pagamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cd_cartao")
    private Long id;

    @NotBlank(message = "O nome é obrigatório")
    @Size(min = 3, max = 50)
    @Column(name = "nm_cartao")
    private String nomeNoCartao;

    @NotBlank(message = "O número é obrigatória")
    @Size(min = 16, max = 20)
    @Column(name = "nr_cartao")
    private String numeroDoCartao;

    @NotNull
    @Column(name = "dt_vencimento")
    private LocalDate validadeDoCartao;

    @NotBlank(message = "O codigo é obrigatório")
    @Size(min = 3, max = 3)
    @Column(name = "nr_cvv")
    private String codigoDoCartao;

    @ManyToOne
    @JoinColumn(name = "cd_plano")
    private Plano plano;
}
