package spz.dae24.ws;


import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import spz.dae24.dtos.SensorHistoryDTO;
import spz.dae24.ejbs.SensorHistoryBean;
import spz.dae24.security.Authenticated;

import java.util.List;

@Path("sensor-history")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class SensorHistoryService {
    @EJB
    private SensorHistoryBean sensorHistoryBean;

    @GET
    @Path("")
    @Authenticated
    @RolesAllowed("Admin")
    public List<SensorHistoryDTO> getAllSensors() {
        return SensorHistoryDTO.from(sensorHistoryBean.findAll());
    }

    @GET
    @Path("{id}")
    @Authenticated
    @RolesAllowed({"Admin", "Client"})
    public Response getSensorHistory(@PathParam("id") long id) {
        var sensorHistory = sensorHistoryBean.find(id);

        return Response.ok(SensorHistoryDTO.from(sensorHistory)).build();
    }

    @POST
    @Path("")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response createSensorHistory(SensorHistoryDTO sensorHistoryDTO) {
        long sensorHistoryId = sensorHistoryBean.create(
                sensorHistoryDTO.getSensorId(),
                sensorHistoryDTO.getValue()
        );

        var sensorHistory = sensorHistoryBean.find(sensorHistoryId);

        return Response.status(Response.Status.CREATED)
                .entity(SensorHistoryDTO.from(sensorHistory)).build();
    }
}
