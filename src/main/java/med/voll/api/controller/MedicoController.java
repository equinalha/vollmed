package med.voll.api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import med.voll.api.medico.MedicoDTO;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/medicos")
public class MedicoController {
    
    @PostMapping
    public void cadastrar(@RequestBody MedicoDTO dados) {
        System.out.println(dados);
    }
}
