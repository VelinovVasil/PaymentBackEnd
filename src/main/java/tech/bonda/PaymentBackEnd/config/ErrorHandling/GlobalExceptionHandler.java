package tech.bonda.PaymentBackEnd.config.ErrorHandling;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({LoginFailedException.class, DuplicateEGNException.class})
    public ResponseEntity<Object> handleLoginFailedException(LoginFailedException ex) {
        ObjectNode jsonNode = new ObjectMapper().createObjectNode();
        jsonNode.put("error", ex.getMessage());
        return new ResponseEntity<>(jsonNode, HttpStatus.UNAUTHORIZED);
    }
}
