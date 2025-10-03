package med.voll.api.domain.consulta.validacoes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;

@Component
public class ValidadorMedicoInativo implements ValidadorAgendamento {

    @Autowired
    MedicoRepository medicoRepository;

    @Override
    public void validar(DadosAgendamentoConsulta dados) {

        Medico medico = medicoRepository.findById(dados.idMedico()).get();

        if (!medico.getAtivo()){
            throw new ValidacaoException("Medico inativo");
        }

    }
    
}
