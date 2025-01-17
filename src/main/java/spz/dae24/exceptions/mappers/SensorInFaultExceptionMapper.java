package spz.dae24.exceptions.mappers;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import spz.dae24.exceptions.QuantityLowerThanOneException;
import spz.dae24.exceptions.SensorInFaultException;

import java.util.logging.Logger;

public class SensorInFaultExceptionMapper implements ExceptionMapper<SensorInFaultException> {
    private static final Logger LOGGER = Logger.getLogger(SensorInFaultExceptionMapper.class.getCanonicalName());

    @Override
    public Response toResponse(SensorInFaultException e) {
        String msg = e.getMessage();
        LOGGER.warning("WARNING: " + msg);

        return Response.status(Response.Status.BAD_REQUEST).entity(msg).build();
    }
}
