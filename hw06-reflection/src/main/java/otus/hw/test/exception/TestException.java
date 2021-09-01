package otus.hw.test.exception;

public class TestException extends RuntimeException {

    public TestException(Throwable cause) {
        super(cause);
    }

    public TestException(String message) {
        super(message);
    }

    public TestException(String message, Throwable cause) {
        super(message, cause);
    }

    public String getMsgError() {
        return "Error invoke test methods";
    }

}
