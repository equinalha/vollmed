package med.voll.api.domain.consulta.validacoes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import med.voll.api.domain.paciente.Paciente;
import med.voll.api.domain.paciente.PacienteRepository;

@Component
public class ValidadorPacienteInativo implements ValidadorAgendamento {

    @Autowired
    PacienteRepository pacienteRepository;

    @Override
    public void validar(DadosAgendamentoConsulta dados) {
        Paciente paciente = pacienteRepository.findById(dados.idPaciente()).get();
        if (!paciente.getAtivo()) {
            throw new ValidacaoException("Paciente inativo");
        }
    }
    
}
