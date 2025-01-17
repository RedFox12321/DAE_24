package spz.dae24.exceptions.mappers;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import spz.dae24.exceptions.SensorNotActiveException;

import java.util.logging.Logger;

public class SensorNotActiveExceptionMapper implements ExceptionMapper<SensorNotActiveException> {
    private static final Logger LOGGER = Logger.getLogger(SensorNotActiveExceptionMapper.class.getCanonicalName());

    @Override
    public Response toResponse(SensorNotActiveException e) {
        String msg = e.getMessage();
        LOGGER.warning("WARNING: " + msg);

        return Response.status(Response.Status.BAD_REQUEST).entity(msg).build();
    }
}
