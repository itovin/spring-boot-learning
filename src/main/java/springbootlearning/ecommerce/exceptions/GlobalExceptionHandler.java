package springbootlearning.ecommerce.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationErrors(MethodArgumentNotValidException exception){
        Map<String, String> errorMap = new HashMap<>();
        exception.getBindingResult().getFieldErrors().forEach(error ->{
            errorMap.put(error.getField(), error.getDefaultMessage());
        });

        return ResponseEntity.badRequest().body(errorMap);
    }

    @ExceptionHandler({UsernameAlreadyRegisteredException.class, EmailAddressAlreadyRegisteredException.class,
            SQLIntegrityConstraintViolationException.class, LoginFailedException.class, CategoryNotFoundException.class, ItemDoesNotExistInCart.class})
    public ResponseEntity<?> handleException(Exception exception){
        return ResponseEntity.badRequest().body(exception.getMessage());
    }
}
