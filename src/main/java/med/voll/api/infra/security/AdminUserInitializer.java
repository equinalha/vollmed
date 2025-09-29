package med.voll.api.infra.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder; // 1. Importe o PasswordEncoder
import org.springframework.stereotype.Component;

import med.voll.api.domain.usuario.Usuario;
import med.voll.api.domain.usuario.UsuarioRepository;

@Component
public class AdminUserInitializer implements CommandLineRunner {

    @Autowired
    private UsuarioRepository repository;

    // 2. Injete o PasswordEncoder que você configurou
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Verifica se o usuário admin já existe para não criar duplicado
        if (repository.findByLogin("admin") == null) {
            System.out.println("Nenhum usuário 'admin' encontrado. Criando usuário administrador...");
            
            // 3. Codifique a senha antes de criar o objeto Usuario
            var senhaCodificada = passwordEncoder.encode("12345678");
            
            Usuario inicial = new Usuario("admin", senhaCodificada);
            repository.save(inicial);

            System.out.println("Usuário administrador criado com sucesso!");
        } else {
            System.out.println("Usuário 'admin' já existe. Nenhuma ação necessária.");
        }
    }
}