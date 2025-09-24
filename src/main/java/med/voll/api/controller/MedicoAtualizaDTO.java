package med.voll.api.controller;

import jakarta.validation.constraints.NotNull;
import med.voll.api.endereco.DadosEndereco;

public record MedicoAtualizaDTO(
    
    @NotNull
    Long id, 
    String nome, 
    String telefone, 
    DadosEndereco endereco) {

}
