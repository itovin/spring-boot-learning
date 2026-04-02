package springbootlearning.ecommerce.exceptions;

public class LoginFailedException extends IllegalArgumentException{
    public LoginFailedException(String msg){
        super(msg);
    }
}
