package med.voll.api.domain.consulta;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import med.voll.api.domain.medico.MedicoRepository;
import med.voll.api.domain.paciente.Paciente;
import med.voll.api.domain.paciente.PacienteRepository;
import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.consulta.validacoes.ValidadorAgendamento;
import med.voll.api.domain.medico.Medico;

@Service
public class AgendaDeConsultas {
    
    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    // Pela interface, o Spring é forçado a injetar todos os validadores que a implementam
    // Assim a classe Service depende de uma abstração de Validadores e não das classes concretas que
    // fazem as validações
    @Autowired
    private List<ValidadorAgendamento> validadores;

    public void agendar(DadosAgendamentoConsulta dados){

        if (!pacienteRepository.existsById(dados.idPaciente())) {
            throw new ValidacaoException("Id do paciente informado não existe");
        }

        // As regras de negócio estabelecem que a especificação do médico é opcional
        // Se não for escolhido, o sistema vai buscar um médico disponível
        if (dados.idMedico() != null && !medicoRepository.existsById(dados.idMedico())) {
            throw new ValidacaoException("Id do medico informado não existe");
        }

        // Percorre todos os validadores fazendo a validação de cada um
        // Se apagar algum validador, automaticamente ele sai da lista
        // Da mesma forma se mais algum for criado, automaticamente estará funcionando também
        validadores.forEach(v -> v.validar(dados));

        Medico medico = escolherMedico(dados);
        Paciente paciente = pacienteRepository.findById(dados.idPaciente()).get();

        Consulta consulta = new Consulta(null, medico, paciente, dados.data());
        consultaRepository.save(consulta);

    }

    private Medico escolherMedico(DadosAgendamentoConsulta dados) {

        if (dados.idMedico() != null) {
            // A diferença entre findById e getReferenceById é que o primeiro retorna todo o objeto (referencia + atributos)
            // enquanto que o segundo retorna apenas uma referência para que seja atribuído a outro objeto, economizando tráfego com o BD
            return medicoRepository.getReferenceById(dados.idMedico());
        }

        if (dados.especialidade() == null) {
            throw new ValidacaoException("Especialidade é obrigatória quando o médico não for escolhido");
        }
        
        

        return medicoRepository.escolherMedicoAleatorioLivreNaData(dados.especialidade(), dados.data());
    }

    public Consulta cancelar(DadosCancelamentoConsulta dados) {
        Consulta consulta = consultaRepository.findById(dados.id()).get();

        if (LocalDateTime.now().plusHours(24).isAfter(consulta.getData())) {
            throw new ValidacaoException("O cancelamento deverá ser feito com pelo menos 24 horas de antencedência");
        }

        if (dados.motivo() == null){
            throw new ValidacaoException("É obrigatório informar o motivo");
        }

        consultaRepository.delete(consulta);

        return consulta;
    }

}
