package med.voll.api.domain.consulta.validacoes;

import med.voll.api.domain.consulta.DadosAgendamentoConsulta;


// A ideia é chamar apenas uma interface de validação que execute todas as classes
// Para isso, todas elas devem ter um método com a mesma assinatura e que execute as diferentes validações.
public interface ValidadorAgendamento {
    void validar(DadosAgendamentoConsulta dados);
}
