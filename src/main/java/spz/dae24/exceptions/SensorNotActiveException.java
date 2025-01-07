package spz.dae24.exceptions;

public class SensorNotActiveException extends RuntimeException {
    public SensorNotActiveException(String message) {
        super(message);
    }
}
