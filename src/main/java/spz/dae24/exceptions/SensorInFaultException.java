package spz.dae24.exceptions;

public class SensorInFaultException extends RuntimeException {
    public SensorInFaultException(String message) {
        super(message);
    }
}
