package spz.dae24.ws;

import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import spz.dae24.dtos.SensorDTO;
import spz.dae24.dtos.SensorHistoryDTO;
import spz.dae24.dtos.SensorWithHistoryDTO;
import spz.dae24.ejbs.SensorBean;
import spz.dae24.exceptions.EntityNotFoundException;
import spz.dae24.security.Authenticated;

import java.util.List;


@Path("sensors")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
@Authenticated
public class SensorService {
    @EJB
    private SensorBean sensorBean;

    @GET
    @Path("")
    @RolesAllowed("Admin")
    public List<SensorDTO> getAllSensors() {
        return SensorDTO.from(sensorBean.findAll());
    }

    @GET
    @Path("{id}")
    @RolesAllowed({"Admin", "Client"})
    public Response getSensor(@PathParam("id") long id, @Context SecurityContext securityContext) throws EntityNotFoundException {
        var sensor = sensorBean.findWithHistory(id);
        var sensorDTO = SensorWithHistoryDTO.from(sensor);
        sensorDTO.setHistory(SensorHistoryDTO.from(sensor.getHistory()));

        if (securityContext.isUserInRole("Admin")) {
            sensorDTO.setHistory(SensorHistoryDTO.from(sensor.getHistory()));
        } else if (securityContext.isUserInRole("Client")) {
            sensorDTO.setHistory(SensorHistoryDTO.fromLast(sensor.getHistory()));
        }

        return Response.ok(sensorDTO).build();
    }
}
