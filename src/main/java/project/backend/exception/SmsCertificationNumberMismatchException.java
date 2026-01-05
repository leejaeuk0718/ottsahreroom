package project.backend.exception;

public class SmsCertificationNumberMismatchException extends RuntimeException {
    public SmsCertificationNumberMismatchException(String message) {
        super(message);
    }
}
