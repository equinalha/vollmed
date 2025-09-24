package med.voll.api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.medico.Medico;
import med.voll.api.medico.MedicoDTO;
import med.voll.api.medico.MedicoListagemDTO;
import med.voll.api.medico.MedicoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/medicos")
public class MedicoController {
    
    @Autowired
    private MedicoRepository repository;

    @PostMapping
    @Transactional // Por ser um método que faz gravação de dados
    public void cadastrar(@RequestBody @Valid MedicoDTO dados) {
        repository.save(new Medico(dados));
    }

    // Forma sem paginação
    /*
    @GetMapping
    public List<MedicoListagemDTO> listar() {

        // Repository retorna um List de medicos, portanto foi criado um DTO para exibir somente os campos necessários: (Nome, email, crm, especialidade)
        // No DTO, foi criado um construtor que recebe um objeto Medico e popula os dados do DTO
        return repository.findAll().stream().map(MedicoListagemDTO::new).toList();
    } */

    // Forma com paginação
    // utilização: GET http://localhost:8080/medicos?size=1&page=2&sort=nome,desc
    @GetMapping
    public Page<MedicoListagemDTO> listar(@PageableDefault(size = 10, page = 0, sort = {"nome"}) Pageable page) {

        // Repository retorna um List de medicos, portanto foi criado um DTO para exibir somente os campos necessários: (Nome, email, crm, especialidade)
        // No DTO, foi criado um construtor que recebe um objeto Medico e popula os dados do DTO
        return repository.findAll(page).map(MedicoListagemDTO::new);
    }

    @PutMapping("/{id}")
    @Transactional
    public void atualizar(@RequestBody @Valid MedicoAtualizaDTO dados) {
        Medico medico = repository.getReferenceById(dados.id());
        medico.atualizar(dados);
    }
}
