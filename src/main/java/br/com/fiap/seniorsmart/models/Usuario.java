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
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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
@Table(name = "T_SS_USUARIO")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cd_usuario")
    private Long id;

    @NotBlank(message = "O nome é obrigatório")
    @Size(min = 3, max = 50)
    @Column(name = "nm_usuario")
    private String nome;

    @NotBlank(message="O email é obrigatório")
    @Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")
    @Column(name = "ds_email")
    private String email;

    @NotBlank(message = "A senha é obrigatória")
    @Size(min = 8)
    @Column(name = "ds_senha")
    private String senha;

    @NotBlank(message = "A confirmação de senha é obrigatória")
    @Size(min = 8)
    @Transient
    private String confirmarSenha;

    @NotNull
    @Column(name = "dt_nascimento")
    private LocalDate data;
    
    @NotBlank(message = "O telefone é obrigatório")
    @Pattern(regexp = "^\\(?\\d{2}\\)?[\\s-]?\\d{4,5}-\\d{4}$")
    @Column(name = "nr_telefone")
    private String telefone;

    @ManyToOne
    @JoinColumn(name = "cd_plano")
    private Plano plano;
}
