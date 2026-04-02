package springbootlearning.ecommerce.exceptions;

public class UsernameAlreadyRegisteredException extends IllegalArgumentException{
    public UsernameAlreadyRegisteredException(String msg){
        super(msg);
    }
}
