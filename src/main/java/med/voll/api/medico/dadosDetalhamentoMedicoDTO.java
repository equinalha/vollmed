package med.voll.api.medico;

import med.voll.api.endereco.Endereco;

public record dadosDetalhamentoMedicoDTO(
    Long id,
    String nome,
    String email,
    String crm,
    String telefone,
    Especialidade especialidade,
    Endereco endereco
){

    public dadosDetalhamentoMedicoDTO(Medico medico){
        this(medico.getId(), medico.getNome(), medico.getEmail(), medico.getCrm(), medico.getTelefone(), medico.getEspecialidade(), medico.getEndereco());
    }

}
