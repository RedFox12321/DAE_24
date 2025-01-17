package spz.dae24.exceptions.mappers;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import spz.dae24.exceptions.EntityExistsException;

import java.util.logging.Logger;

@Provider
public class EntityNotFoundExceptionMapper implements ExceptionMapper<EntityExistsException> {
    private static final Logger LOGGER = Logger.getLogger(EntityNotFoundExceptionMapper.class.getCanonicalName());

    @Override
    public Response toResponse(EntityExistsException e) {
        String msg = e.getMessage();
        LOGGER.warning("WARNING: " + msg);

        return Response.status(Response.Status.NOT_FOUND).entity(msg).build();
    }
}
