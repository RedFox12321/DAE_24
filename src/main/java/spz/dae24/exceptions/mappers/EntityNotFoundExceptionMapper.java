package spz.dae24.exceptions.mappers;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import spz.dae24.exceptions.EntityExistsException;
import spz.dae24.exceptions.EntityNotFoundException;

import java.util.logging.Logger;

@Provider
public class EntityNotFoundExceptionMapper implements ExceptionMapper<EntityNotFoundException> {
    private static final Logger LOGGER = Logger.getLogger(EntityNotFoundExceptionMapper.class.getCanonicalName());

    @Override
    public Response toResponse(EntityNotFoundException e) {
        String msg = e.getMessage();
        LOGGER.warning("WARNING: " + msg);

        return Response.status(Response.Status.NOT_FOUND).entity(msg).build();
    }
}
