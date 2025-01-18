package spz.dae24.exceptions;


public class MyEntityNotFoundException extends jakarta.persistence.EntityNotFoundException {
    public MyEntityNotFoundException(String message) {
        super(message);
    }
}
