package spz.dae24.exceptions.mappers;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import spz.dae24.exceptions.SensorNotActiveException;
import spz.dae24.exceptions.SensorTypeNotExistException;

import java.util.logging.Logger;

public class SensorTypeNotExistMapper implements ExceptionMapper<SensorTypeNotExistException> {
    private static final Logger LOGGER = Logger.getLogger(SensorTypeNotExistMapper.class.getCanonicalName());

    @Override
    public Response toResponse(SensorTypeNotExistException e) {
        String msg = e.getMessage();
        LOGGER.warning("WARNING: " + msg);

        return Response.status(Response.Status.BAD_REQUEST).entity(msg).build();
    }
}
