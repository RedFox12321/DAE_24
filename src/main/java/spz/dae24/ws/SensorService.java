package spz.dae24.ws;

import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import spz.dae24.dtos.SensorDTO;
import spz.dae24.dtos.SensorHistoryDTO;
import spz.dae24.ejbs.SensorBean;
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
    public List<SensorDTO> getAllSensors() {
        return SensorDTO.from(sensorBean.findAll());
    }

    @GET
    @Path("{id}")
    public Response getSensor(@PathParam("id") long id) {
        var sensor = sensorBean.findWithHistory(id);
        var sensorDTO = SensorDTO.from(sensor);
        sensorDTO.setHistory(SensorHistoryDTO.from(sensor.getHistory()));

        return Response.ok(sensorDTO).build();
    }

    @PATCH
    @Path("{id}")
    public Response disableSensor(@PathParam("id") long id) {
        sensorBean.disable(id);
        var sensor = sensorBean.find(id);

        return Response.ok(SensorDTO.from(sensor)).build();
    }
}
