package springbootlearning.ecommerce.exceptions;

public class EmailAddressAlreadyRegisteredException extends IllegalArgumentException{

    public EmailAddressAlreadyRegisteredException(String msg){
        super(msg);
    }
}
