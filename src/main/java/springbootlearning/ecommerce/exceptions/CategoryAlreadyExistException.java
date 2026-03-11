package springbootlearning.ecommerce.exceptions;

public class CategoryAlreadyExistException extends Exception {
    public CategoryAlreadyExistException(String message) {
        super(message);
    }
}
