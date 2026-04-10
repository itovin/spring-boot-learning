package springbootlearning.ecommerce.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
            SQLIntegrityConstraintViolationException.class, CategoryNotFoundException.class,
            ItemDoesNotExistInCart.class, ProductNotFoundException.class, UsernameNotFoundException.class})
    public ResponseEntity<?> handleBadRequestException(Exception exception){
        return ResponseEntity.badRequest().body(exception.getMessage());
    }
    @ExceptionHandler(LoginFailedException.class)
    public ResponseEntity<?> handleUnauthorizedException(Exception exception){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(exception.getMessage());
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Void> handleBadCredentialsException(){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
