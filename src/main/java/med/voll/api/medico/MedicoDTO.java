package med.voll.api.medico;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import med.voll.api.endereco.DadosEndereco;

public record MedicoDTO(

@NotBlank // Somente para String. Já faz o @NotNull
String nome, 

@NotBlank
@Email
String email,

@NotBlank
String telefone,

@NotBlank
@Pattern(regexp = "\\d{4,6}")
String crm, 

@NotNull
Especialidade especialidade, 

@NotNull
@Valid // Diz para o spring que esta validação vem do outro objeto (record), neste caso, endereço
DadosEndereco endereco) {
    
}
