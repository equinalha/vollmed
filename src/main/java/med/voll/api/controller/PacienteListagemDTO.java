package med.voll.api.controller;

import med.voll.api.domain.paciente.Paciente;

public record PacienteListagemDTO(
    String nome,
    String cpf,
    String email

) {

    public PacienteListagemDTO(Paciente paciente){
        this(paciente.getNome(), paciente.getCpf(), paciente.getEmail());
    }

}
