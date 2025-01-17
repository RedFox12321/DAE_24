package spz.dae24.exceptions;

public class EntityExistsException extends jakarta.persistence.EntityExistsException {
    public EntityExistsException(String message) {
        super(message);
    }
}
