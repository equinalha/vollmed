package med.voll.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import med.voll.api.domain.usuario.AutenticacaoDTO;
import med.voll.api.domain.usuario.Usuario;
import med.voll.api.infra.security.DadosTokenJwtDTO;
import med.voll.api.infra.security.TokenService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/login")
public class AutenticacaoController {
    
    // O AuthenticationManager chama, por debaixo dos panos, loadUserByUsername de AutenticacaoService
    // Para que o Spring consiga injetá-lo via @Autowired, é necessário configurar na classe SecurityConfigurations
    @Autowired
    private AuthenticationManager manager;
    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity<DadosTokenJwtDTO> login(@RequestBody @Valid AutenticacaoDTO dados) {
        
        var authenticationToken = new UsernamePasswordAuthenticationToken(dados.login(), dados.senha());
        Authentication authentication = manager.authenticate(authenticationToken);

        var tokenJWT = tokenService.gerarToken((Usuario) authentication.getPrincipal());
        
        return ResponseEntity.ok(new DadosTokenJwtDTO(tokenJWT));
    }
    

}
