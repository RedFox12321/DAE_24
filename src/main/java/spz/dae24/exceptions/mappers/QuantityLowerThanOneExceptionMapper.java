package spz.dae24.exceptions.mappers;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import spz.dae24.exceptions.QuantityLowerThanOneException;
import spz.dae24.exceptions.TypeNotExistException;

import java.util.logging.Logger;

public class QuantityLowerThanOneExceptionMapper implements ExceptionMapper<QuantityLowerThanOneException> {
    private static final Logger LOGGER = Logger.getLogger(QuantityLowerThanOneExceptionMapper.class.getCanonicalName());

    @Override
    public Response toResponse(QuantityLowerThanOneException e) {
        String msg = e.getMessage();
        LOGGER.warning("WARNING: " + msg);

        return Response.status(Response.Status.BAD_REQUEST).entity(msg).build();
    }
}
