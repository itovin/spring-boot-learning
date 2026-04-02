package springbootlearning.ecommerce.exceptions;

public class CategoryNotFoundException extends IllegalArgumentException {
    public CategoryNotFoundException(String message) {
        super(message);
    }
}
