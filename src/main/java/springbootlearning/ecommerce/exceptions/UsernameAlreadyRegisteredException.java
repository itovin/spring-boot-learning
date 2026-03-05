package springbootlearning.ecommerce.exceptions;

public class UsernameAlreadyRegisteredException extends Exception{
    public UsernameAlreadyRegisteredException(String msg){
        super(msg);
    }
}
