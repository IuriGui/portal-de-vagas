package br.csi.oportunidades.infra;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<String> tratarErro404(){
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("{\"error\": \"Recurso não encontrado\", \"status\": 404}");
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<String> tratarErro403(AccessDeniedException e) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<String> tratarErro401() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("{\"error\": \"Email ou senha incorretos\", \"status\": 401}");
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<String> tratarUsuarioNaoEncontrado() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("{\"error\": \"Usuário não encontrado\", \"status\": 401}");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> tratarErroValidacao() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("{\"error\": \"Dados de entrada inválidos\", \"status\": 400}");
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> tratarErroArgumento() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("{\"error\": \"Argumento inválido\", \"status\": 400}");
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> tratarErroRuntime(RuntimeException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("{\"error\": \"Erro interno do servidor: " + e.getMessage() + "\", \"status\": 500}");
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> tratarErroGenerico(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("{\"error\": \"Erro interno do servidor\", \"status\": 500}");
    }
}
