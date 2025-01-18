package spz.dae24.exceptions.mappers;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;

import java.util.logging.Logger;

public class BasicExceptionMapper implements ExceptionMapper<Exception> {
    private static final Logger LOGGER = Logger.getLogger(BasicExceptionMapper.class.getCanonicalName());

    @Override
    public Response toResponse(Exception e) {
        String msg = e.getMessage();
        LOGGER.warning("ERROR: " + msg);

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Something went wrong. Try again later").build();
    }
}
