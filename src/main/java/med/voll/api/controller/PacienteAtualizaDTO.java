package med.voll.api.controller;

import jakarta.validation.constraints.NotNull;
import med.voll.api.domain.endereco.DadosEndereco;

public record PacienteAtualizaDTO(
    
    @NotNull
    Long id,
    String nome,
    String email,
    String telefone,
    DadosEndereco endereco,
    Boolean ativo

) {

}
