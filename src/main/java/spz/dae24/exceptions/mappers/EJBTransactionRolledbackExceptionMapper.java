package spz.dae24.exceptions.mappers;

import jakarta.annotation.Priority;
import jakarta.ejb.EJBTransactionRolledbackException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import spz.dae24.exceptions.MyEntityExistsException;
import spz.dae24.exceptions.MyEntityNotFoundException;

import java.util.logging.Logger;

@Priority(1)
@Provider
public class EJBTransactionRolledbackExceptionMapper implements ExceptionMapper<EJBTransactionRolledbackException> {
    private static final Logger LOGGER = Logger.getLogger(EJBTransactionRolledbackExceptionMapper.class.getCanonicalName());

    @Override
    public Response toResponse(EJBTransactionRolledbackException e) {
        Throwable cause = e.getCause();

        if (cause instanceof MyEntityNotFoundException) {
            LOGGER.warning("WARNING: " + cause.getMessage());
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(cause.getMessage())
                    .build();
        } else if (cause instanceof MyEntityExistsException) {
            LOGGER.warning("WARNING: " + cause.getMessage());
            return Response.status(Response.Status.CONFLICT)
                    .entity(cause.getMessage())
                    .build();
        } else if (cause instanceof IllegalArgumentException) {
            LOGGER.warning("WARNING: " + cause.getMessage());
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(cause.getMessage())
                    .build();
        }

        LOGGER.severe("ERROR: " + e.getMessage());

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("Something went wrong. Try again later")
                .build();
    }
}
