package spz.dae24.exceptions.mappers;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import spz.dae24.exceptions.SensorNotActiveException;
import spz.dae24.exceptions.TypeNotExistException;

import java.util.logging.Logger;

public class TypeNotExistMapper implements ExceptionMapper<TypeNotExistException> {
    private static final Logger LOGGER = Logger.getLogger(TypeNotExistMapper.class.getCanonicalName());

    @Override
    public Response toResponse(TypeNotExistException e) {
        String msg = e.getMessage();
        LOGGER.warning("WARNING: " + msg);

        return Response.status(Response.Status.BAD_REQUEST).entity(msg).build();
    }
}
