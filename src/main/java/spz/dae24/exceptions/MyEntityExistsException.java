package spz.dae24.exceptions;

public class MyEntityExistsException extends jakarta.persistence.EntityExistsException {
    public MyEntityExistsException(String message) {
        super(message);
    }
}
