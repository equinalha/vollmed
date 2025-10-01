package med.voll.api.domain.consulta;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface ConsultaRepository extends JpaRepository<Consulta, Long>{
    
}
