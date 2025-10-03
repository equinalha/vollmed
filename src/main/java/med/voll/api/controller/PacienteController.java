package med.voll.api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.paciente.PacienteDetalhamentoDTO;
import med.voll.api.domain.paciente.Paciente;
import med.voll.api.domain.paciente.PacienteAtualizaDTO;
import med.voll.api.domain.paciente.PacienteDTO;
import med.voll.api.domain.paciente.PacienteListagemDTO;
import med.voll.api.domain.paciente.PacienteRepository;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("pacientes")
@SecurityRequirement(name = "bearer-key")
public class PacienteController {

    @Autowired
    PacienteRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity<PacienteDTO> cadastrar(@RequestBody @Valid PacienteDTO dados, UriComponentsBuilder uriBuilder) {
        
        Paciente paciente = new Paciente(dados);
        repository.save(paciente);
        URI uri = uriBuilder.path("/pacientes/{id}").buildAndExpand(paciente.getId()).toUri();     
        return ResponseEntity.created(uri).body(new PacienteDTO(paciente));
    }

    @GetMapping
    public ResponseEntity<Page<PacienteListagemDTO>> listar(@PageableDefault(size = 10, page = 0, sort = {"nome"}) Pageable page) {
        Page<PacienteListagemDTO> pacientes = repository.findAll(page).map(PacienteListagemDTO::new);
        return ResponseEntity.ok(pacientes);
    }

    @PutMapping
    @Transactional
    public ResponseEntity<PacienteDetalhamentoDTO> atualizar(@RequestBody @Valid PacienteAtualizaDTO dados) {
        Paciente paciente = repository.getReferenceById(dados.id());
        paciente.atualizar(dados);

        return ResponseEntity.ok(new PacienteDetalhamentoDTO(paciente));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Paciente> excluir(@PathVariable Long id){
        Paciente paciente = repository.getReferenceById(id);
        paciente.excluir();
        return ResponseEntity.noContent().build();
    }
}