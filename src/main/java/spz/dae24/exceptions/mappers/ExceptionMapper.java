package spz.dae24.exceptions.mappers;

import jakarta.annotation.Priority;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;

import java.util.logging.Logger;

public class ExceptionMapper implements jakarta.ws.rs.ext.ExceptionMapper<Exception> {
    private static final Logger LOGGER = Logger.getLogger(ExceptionMapper.class.getCanonicalName());

    @Override
    public Response toResponse(Exception e) {
        String msg = e.getMessage();
        LOGGER.warning("ERROR: " + msg);

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Something went wrong. Try again later").build();
    }
}
