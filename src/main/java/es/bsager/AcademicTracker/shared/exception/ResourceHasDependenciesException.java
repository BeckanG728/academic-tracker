package es.bsager.AcademicTracker.shared.exception;

public class ResourceHasDependenciesException extends RuntimeException {
    public ResourceHasDependenciesException(String message) {
        super(message);
    }
}
