package spz.dae24.exceptions.mappers;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.util.logging.Logger;

@Provider
public class IllegalArgumentExceptionMapper implements ExceptionMapper<IllegalArgumentException> {
    private static final Logger LOGGER = Logger.getLogger(IllegalArgumentExceptionMapper.class.getCanonicalName());

    @Override
    public Response toResponse(IllegalArgumentException e) {
        String msg = e.getMessage();
        LOGGER.warning("WARNING: " + msg);

        return Response.status(Response.Status.NOT_FOUND).entity(msg).build();
    }
}
