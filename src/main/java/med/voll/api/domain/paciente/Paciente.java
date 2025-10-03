package med.voll.api.domain.paciente;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.domain.endereco.DadosEndereco;
import med.voll.api.domain.endereco.Endereco;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id") // Indica que o campo utilizado para comparação será o id
public class Paciente {

    public Paciente(PacienteDTO paciente) {
        this.nome = paciente.nome();
        this.email = paciente.email();
        this.cpf = paciente.cpf();
        this.telefone = paciente.telefone();
        this.endereco = paciente.endereco();
    }

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;
    private String email;
    private String cpf;
    private String telefone;

    @Embedded
    private DadosEndereco endereco;
    private Boolean ativo;

    public void atualizar(PacienteAtualizaDTO dados) {
        if(dados.nome() != null)
            this.nome = dados.nome();
        if(dados.email() != null)
            this.email = dados.email();
        if(dados.telefone() != null)
            this.telefone = dados.telefone();
        if(dados.endereco() != null)
            this.endereco.atualizar(dados.endereco());
        if(dados.ativo() != null)
            this.ativo = dados.ativo();
    }

    public void excluir() {
        this.ativo = false;
    }


    
}
