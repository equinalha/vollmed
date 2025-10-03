package med.voll.api.domain.consulta.validacoes;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consulta.Consulta;
import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import med.voll.api.domain.paciente.Paciente;
import med.voll.api.domain.paciente.PacienteRepository;

@Component
public class ValidadorConsultaMesmoDia implements ValidadorAgendamento {

    @Autowired
    ConsultaRepository consultaRepository;

    @Autowired
    PacienteRepository pacienteRepository;

    @Override
    public void validar(DadosAgendamentoConsulta dados) {

        Paciente paciente = pacienteRepository.findById(dados.idPaciente()).get();
        
        LocalDate dia = dados.data().toLocalDate();

        // 2. Definir o intervalo de tempo para o dia inteiro
        LocalDateTime inicioDoDia = dia.atStartOfDay(); // Ex: 2025-10-03T00:00:00
        LocalDateTime fimDoDia = dia.atTime(LocalTime.MAX); // Ex: 2025-10-03T23:59:59.999999999

        // 3. Chamar o método do repositório
        List<Consulta> consultas =  consultaRepository.findByPacienteAndDataHoraBetween(paciente, inicioDoDia, fimDoDia);

        if (consultas.size() > 0){
            throw new ValidacaoException("Já existe consulta agendada para este paciente no mesmo dia");
        }

    }
    
}
