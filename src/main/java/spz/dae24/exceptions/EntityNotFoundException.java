package spz.dae24.exceptions;

public class EntityNotFoundException extends jakarta.persistence.EntityNotFoundException {
    public EntityNotFoundException(String message) {
        super(message);
    }
}
