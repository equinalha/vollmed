package med.voll.api.domain.consulta;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

public record DadosAgendamentoConsulta(
    Long idMedico,
    @NotNull
    Long idPaciente,
    @NotNull
    @Future
    // Para que o spring reconheça a data passada via request, o formato deve ser: AAAA-MM-DDTHH:mm(:SS)
    // Também é possível personalizar o formato da seguinte forma:
    // @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    LocalDateTime data
) {

}
