package springbootlearning.ecommerce.exceptions;

public class ItemDoesNotExistInCart extends IllegalArgumentException {
    public ItemDoesNotExistInCart(String message) {
        super(message);
    }
}
