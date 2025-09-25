package med.voll.api.paciente;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import med.voll.api.endereco.Endereco;

public record PacienteDTO(
    
    @NotBlank
    String nome,

    @NotBlank
    @Email
    String email,

    @NotBlank
    String telefone,

    @NotBlank
    @Pattern(regexp = "\\d{3\\.?\\d{3}\\.?\\d{3}\\-?\\d{2}")
    String cpf,
    Endereco endereco
) {

    public PacienteDTO(Paciente paciente) {
        this(paciente.getNome(), paciente.getEmail(), paciente.getTelefone(), paciente.getCpf(), paciente.getEndereco());
    }

}
