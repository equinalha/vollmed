package med.voll.api.infra.exception;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.persistence.EntityNotFoundException;
import med.voll.api.domain.ValidacaoException;

@RestControllerAdvice
public class TratadorDeErros {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> tratarErro404(){
        return ResponseEntity.notFound().build();
    }

    // Trata erro 400 de Bean Validation
    // As mensagens são geradas em inglês pelo Spring-Validation, porém se for enviado o cabeçalho "Accept-Language: pt-br"
    // será traduzido automaticamente
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> tratarErro400(MethodArgumentNotValidException exception){
        List<FieldError> erros = exception.getFieldErrors();
        return ResponseEntity.badRequest().body(erros.stream().map(DadosErroValidacao::new).toList());
    }

    // Faz com que o Spring retorne corretamente as mensagens de erro dos validadores para o request
    @ExceptionHandler(ValidacaoException.class)
    public ResponseEntity<Object> tratarErroValidacao(ValidacaoException exception){
        return ResponseEntity.badRequest().body(exception.getMessage());
    }

    private record DadosErroValidacao(
        String campo,
        String mensagem
    ) {
        public DadosErroValidacao(FieldError erro){
            this(erro.getField(), erro.getDefaultMessage());
        }
    }

}
