package otus.hw.test.exception;

public class AfterTestException extends TestException {
    public AfterTestException(Throwable cause) {
        super(cause);
    }

    public AfterTestException(String message) {
        super(message);
    }

    public AfterTestException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public String getMsgError() {
        return "Error invoke after methods";
    }
}
