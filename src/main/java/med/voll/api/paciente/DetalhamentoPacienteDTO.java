package med.voll.api.paciente;

import med.voll.api.endereco.Endereco;

public record DetalhamentoPacienteDTO(

    String nome,
    String email,
    String cpf,    
    String telefone,    
    Endereco endereco

) {

    public DetalhamentoPacienteDTO(Paciente paciente){
        this(paciente.getNome(), paciente.getEmail(), paciente.getCpf(), paciente.getTelefone(), paciente.getEndereco());
    }

}
