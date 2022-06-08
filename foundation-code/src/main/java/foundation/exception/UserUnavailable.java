package foundation.exception;

public class UserUnavailable extends RuntimeException{

    public UserUnavailable() {
    }

    public UserUnavailable(String message) {
        super(message);
    }

    public UserUnavailable(String message, Throwable cause) {
        super(message, cause);
    }

    public UserUnavailable(Throwable cause) {
        super(cause);
    }

    public UserUnavailable(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
