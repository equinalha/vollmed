package med.voll.api.domain.consulta.validacoes;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consulta.Consulta;
import med.voll.api.domain.consulta.ConsultaRepository;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;

@Component
public class ValidadorMedicoConsulta implements ValidadorAgendamento{

    @Autowired
    MedicoRepository medicoRepository;

    @Autowired
    ConsultaRepository consultaRepository;

    @Override
    public void validar(DadosAgendamentoConsulta dados) {

        Medico medico = medicoRepository.findById(dados.idMedico()).get();
        LocalDateTime horaInicioConsulta = dados.data();
        LocalDateTime horaFimConsulta = horaInicioConsulta.plusHours(1);

        List<Consulta> consultas = consultaRepository.findByMedicoAndDataBetween(medico, horaInicioConsulta, horaFimConsulta);

        if(consultas.size() > 0){
            throw new ValidacaoException("O médico escolhido já tem uma consulta agendada neste horário");
        }

    }
    
}
