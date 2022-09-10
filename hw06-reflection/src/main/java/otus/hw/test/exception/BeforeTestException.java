package otus.hw.test.exception;

public class BeforeTestException extends TestException{

    public BeforeTestException(Throwable cause) {
        super(cause);
    }

    public BeforeTestException(String message) {
        super(message);
    }

    public BeforeTestException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public String getMsgError() {
        return "Error invoke before methods";
    }
}
