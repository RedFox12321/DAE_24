package spz.dae24.ws;

import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import spz.dae24.dtos.ProductsVolumeDTO;
import spz.dae24.dtos.VolumeDTO;
import spz.dae24.ejbs.VolumeBean;

import java.util.List;

@Path("volumes")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class VolumeService {
    @EJB
    private VolumeBean volumeBean;

    @GET
    @Path("")
    public List<VolumeDTO> getVolumes() {
        return VolumeDTO.from(volumeBean.findAll());
    }

    @GET
    @Path("{code}")
    public Response getVolume(@PathParam("code") long code) {
       var volume = volumeBean.find(code);
       var volumeDTO = VolumeDTO.from(volume);
       volumeDTO.setVolumeProducts(ProductsVolumeDTO.from(volume.getVolumeProducts()));

       return Response.ok(volumeDTO).build();
    }

    @POST
    @Path("")
    public Response createVolume() {}
}
