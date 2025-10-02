package med.voll.api.domain.consulta;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import med.voll.api.domain.medico.MedicoRepository;
import med.voll.api.domain.paciente.Paciente;
import med.voll.api.domain.paciente.PacienteRepository;
import med.voll.api.domain.ValidacaoException;
import med.voll.api.domain.medico.Medico;

@Service
public class AgendaDeConsultas {
    
    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    public void agendar(DadosAgendamentoConsulta dados){

        if (!pacienteRepository.existsById(dados.idPaciente())) {
            throw new ValidacaoException("Id do paciente informado não existe");
        }

        // As regras de negócio estabelecem que a especificação do médico é opcional
        // Se não for escolhido, o sistema vai buscar um médico disponível
        if (dados.idMedico() != null && !medicoRepository.existsById(dados.idMedico())) {
            throw new ValidacaoException("Id do medico informado não existe");
        }


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

        if (dados.especialidade() != null) {
            throw new ValidacaoException("Especialidade é obrigatória quando o médico não for escolhido");
        }
        
        

        return medicoRepository.escolherMedicoAleatorioLivreNaData(dados.especialidade(), dados.data());
    }

}
