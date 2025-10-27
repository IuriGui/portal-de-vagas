package br.csi.oportunidades.infra;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.*;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<String> tratarErro404(){

        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<String> tratarErro403(AccessDeniedException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<FieldError> errors = ex.getFieldErrors();
        List<DadosErroValidacao> dados = new ArrayList<>();

        for(FieldError fe : errors){
            dados.add(new DadosErroValidacao(fe.getField(), fe.getDefaultMessage()));
        }

        return ResponseEntity.badRequest().body(dados);

    }


    private record DadosErroValidacao(String campo, String mensagem){}

    @ExceptionHandler(AuthorizationDeniedException.class)
    public ProblemDetail handleAuthorizationDenied(AuthorizationDeniedException ex) {

        // Cria o ProblemDetail já com o status e a mensagem
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.FORBIDDEN,
                ex.getMessage()
        );

        // Define o título (resumo do erro)
        problemDetail.setTitle("Acesso Negado");

        // Você pode adicionar propriedades customizadas se precisar
        problemDetail.setProperty("timestamp", Instant.now());
        // Opcional: um link para a documentação do erro
        // problemDetail.setType(URI.create("/docs/errors/acesso-negado"));

        return problemDetail;
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, Object>> handleDataIntegrityViolation(DataIntegrityViolationException ex) {

        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", new Date());


        String errorMessage = ex.getMostSpecificCause().getMessage();


        if (errorMessage.contains("inscricao_candidato_id_oportunidade_id_key")) {

            body.put("erro", "Você já se inscreveu para esta vaga.");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(body);

        } else if(errorMessage.contains("usuario_email_key")) {
            body.put("erro", "Este email ja esta cadastrado");
            return ResponseEntity.status(HttpStatus.CONFLICT).body(body);
        }

        body.put("erro", "Não foi possível processar a solicitação. Verifique os dados enviados.");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }








}
