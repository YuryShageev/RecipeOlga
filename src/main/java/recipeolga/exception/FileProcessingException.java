package recipeolga.exception;

import org.apache.commons.lang3.exception.ExceptionUtils;

public class FileProcessingException extends RuntimeException {

    public FileProcessingException() {
        super("The problem during reading the file");
    }

    public FileProcessingException(String message) {
        super(message);
    }

    public FileProcessingException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileProcessingException(Throwable cause) {
        super(cause);
    }

    protected FileProcessingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}