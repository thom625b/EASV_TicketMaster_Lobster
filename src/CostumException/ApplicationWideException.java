package CostumException;

public class ApplicationWideException extends Exception{

    public ApplicationWideException(String message, Throwable cause){
        super(message, cause);
    }


    public ApplicationWideException(String message) {
        super(message);
    }
}
