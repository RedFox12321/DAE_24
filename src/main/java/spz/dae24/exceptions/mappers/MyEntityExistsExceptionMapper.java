package spz.dae24.exceptions.mappers;

import jakarta.annotation.Priority;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import spz.dae24.exceptions.MyEntityExistsException;

import java.util.logging.Logger;


@Priority(0)
@Provider
public class MyEntityExistsExceptionMapper implements ExceptionMapper<MyEntityExistsException> {
    private static final Logger LOGGER = Logger.getLogger(MyEntityExistsExceptionMapper.class.getCanonicalName());

    @Override
    public Response toResponse(MyEntityExistsException e) {
        String msg = e.getMessage();
        LOGGER.warning("WARNING: " + msg);

        return Response.status(Response.Status.CONFLICT).entity(msg).build();
    }
}
