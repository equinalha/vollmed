package med.voll.api.domain.consulta;

import org.springframework.stereotype.Repository;

import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.paciente.Paciente;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface ConsultaRepository extends JpaRepository<Consulta, Long>{
    List<Consulta> findByPacienteAndDataBetween(Paciente paciente, LocalDateTime startOfDay, LocalDateTime endOfDay);

    List<Consulta> findByMedicoAndDataBetween(
            Medico medico, LocalDateTime horaInicioConsulta, LocalDateTime horaFimConsulta);
}
