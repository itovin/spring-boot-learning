package springbootlearning.ecommerce.exceptions;

public class EmailAddressAlreadyRegisteredException extends Exception{

    public EmailAddressAlreadyRegisteredException(String msg){
        super(msg);
    }
}
