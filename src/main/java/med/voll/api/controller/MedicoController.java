package med.voll.api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoAtualizaDTO;
import med.voll.api.domain.medico.MedicoDTO;
import med.voll.api.domain.medico.MedicoListagemDTO;
import med.voll.api.domain.medico.MedicoRepository;
import med.voll.api.domain.medico.dadosDetalhamentoMedicoDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("medicos")
public class MedicoController {
    
    @Autowired
    private MedicoRepository repository;

    @PostMapping
    @Transactional // Por ser um método que faz gravação de dados
    public ResponseEntity<dadosDetalhamentoMedicoDTO> cadastrar(@RequestBody @Valid MedicoDTO dados, UriComponentsBuilder uriBuilder) {
        
        Medico medico = new Medico(dados);
        repository.save(medico);
        // Para devolver o HTTP 201 é necessário:
        // 1 - Devolver no body os dados do registro criado
        // 2 - Devolver um cabeçalho "Location" com a URL de onde se acessa o recurso criado

        var uri = uriBuilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri(); // Constrtói a URI que vai apontar para o recurso criado
        return ResponseEntity.created(uri).body(new dadosDetalhamentoMedicoDTO(medico)); // HTTP 201 - Created
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
    public ResponseEntity<Page<MedicoListagemDTO>> listar(@PageableDefault(size = 10, page = 0, sort = {"nome"}) Pageable page) {

        // Repository retorna um List de medicos, portanto foi criado um DTO para exibir somente os campos necessários: (Nome, email, crm, especialidade)
        // No DTO, foi criado um construtor que recebe um objeto Medico e popula os dados do DTO
        Page<MedicoListagemDTO> pagina = repository.findAllByAtivoTrue(page).map(MedicoListagemDTO::new);
        
        return ResponseEntity.ok(pagina);
    }

    @GetMapping("/{id}")
    public ResponseEntity<dadosDetalhamentoMedicoDTO> detalhar(@PathVariable Long id){

        // Se for fornecido um id que não existe, o método getReferenceById irá lançar uma EntityNotFoundException que,
        // se não for tratada, vai resultar em um erro HTTP 500 na aplicação.
        // Para tratar isso, é interessante utilizar um Try-Catch (solução local) ou criar uma classe (solução global) anotada com @RestControllerAdvice
        // contendo um método anotado por @ExceptionHandler(EntityNotFoundException.class)

        Medico medico = repository.getReferenceById(id);

        dadosDetalhamentoMedicoDTO dadosMedico = new dadosDetalhamentoMedicoDTO(medico);
        return ResponseEntity.ok(dadosMedico);
        
    }

    @PutMapping
    @Transactional
    public ResponseEntity<dadosDetalhamentoMedicoDTO> atualizar(@RequestBody @Valid MedicoAtualizaDTO dados) {
        Medico medico = repository.getReferenceById(dados.id());
        medico.atualizar(dados);
        
        // Não devolver entidade JPA no controller
        return ResponseEntity.ok(new dadosDetalhamentoMedicoDTO(medico));
    }

    // Exclusão real
    // @DeleteMapping("/{id}")
    // @Transactional
    // public void excluir(@PathVariable Long id){
    //     repository.deleteById(id);
    // }

    // Exclusão lógica (necessário incluir mais um campo no banco.)
    // foi criada a migration V3 para isso
    @DeleteMapping("/{id}")
    @Transactional
    // Versão sem ResponseEntity: Somente devolve 200 caso tenha sido bem sucedida, ou 404
    //
    // public void excluir(@PathVariable Long id){
    //     Medico medico = repository.getReferenceById(id);
    //     medico.excluir();
    // }
    //
    // Versão com ResponseEntity, respeitando padrão REST
    public ResponseEntity<Medico> excluir(@PathVariable Long id){
        Medico medico = repository.getReferenceById(id);
        medico.excluir();

        return ResponseEntity.noContent().build(); // Devolve um código 204 -> Sucesso, sem conteúdo
    }
}
