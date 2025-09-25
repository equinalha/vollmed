package med.voll.api.domain.paciente;

public record PacienteListagemDTO(
    String nome,
    String cpf,
    String email

) {

    public PacienteListagemDTO(Paciente paciente){
        this(paciente.getNome(), paciente.getCpf(), paciente.getEmail());
    }

}
