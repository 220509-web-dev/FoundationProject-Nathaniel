package foundation.exception;

public class DataSourceException extends RuntimeException{

    public DataSourceException() {
    }

    public DataSourceException(String message, Throwable cause) {
        super(message, cause);
    }
}
