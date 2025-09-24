package med.voll.api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.paciente.Paciente;
import med.voll.api.paciente.PacienteDTO;
import med.voll.api.paciente.PacienteRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("pacientes")
public class PacienteController {

    @Autowired
    PacienteRepository repository;

    @PostMapping
    @Transactional
    public void cadastrar(@RequestBody @Valid PacienteDTO paciente) {
        repository.save(new Paciente(paciente));
    }

    @GetMapping
    public Page<PacienteListagemDTO> listar(@PageableDefault(size = 10, page = 0, sort = {"nome"}) Pageable page) {
        return repository.findAll(page).map(PacienteListagemDTO::new);
    }
}
