package med.voll.api.domain.usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

// Esta classe é detectada automaticamente pelo Spring Security e será chamada toda vez que o usuário tentar se logar no sistema
// O Spring security detecta isso devido à implementação da interface UserDetailsService

@Service
public class AutenticacaoService implements UserDetailsService {
        
    @Autowired
    private UsuarioRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        return repository.findByLogin(username);

    }
    
}
