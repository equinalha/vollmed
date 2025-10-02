package med.voll.api.domain.consulta.validacoes;

import java.time.Duration;
import java.time.LocalDate;

import org.springframework.stereotype.Component;

import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consulta.DadosAgendamentoConsulta;

@Component
public class ValidadorHorarioAntecedencia implements ValidadorAgendamento{
    
    public void validar(DadosAgendamentoConsulta dados){
        var dataConsulta = dados.data();
        var agora = LocalDate.now();

        var diferencaEmMinutos = Duration.between(dataConsulta, agora).toMinutes();

        if(diferencaEmMinutos < 30 ){
            throw new ValidacaoException("Consulta deve ser agendada com antecedencia minima de 30 minutos");
        }
    }

}
