package spz.dae24.exceptions.mappers;

import jakarta.annotation.Priority;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import spz.dae24.exceptions.MyEntityNotFoundException;

import java.util.logging.Logger;

@Priority(0)
@Provider
public class MyEntityNotFoundExceptionMapper implements ExceptionMapper<MyEntityNotFoundException> {
    private static final Logger LOGGER = Logger.getLogger(MyEntityNotFoundExceptionMapper.class.getCanonicalName());

    @Override
    public Response toResponse(MyEntityNotFoundException e) {
        String msg = e.getMessage();
        LOGGER.warning("WARNING: " + msg);

        return Response.status(Response.Status.NOT_FOUND).entity(msg).build();
    }
}
