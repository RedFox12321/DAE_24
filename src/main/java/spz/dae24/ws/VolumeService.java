package spz.dae24.ws;

import jakarta.ejb.EJB;
import jakarta.persistence.EntityNotFoundException;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import spz.dae24.common.enums.PackageType;
import spz.dae24.common.enums.Status;
import spz.dae24.dtos.ProductsVolumeDTO;
import spz.dae24.dtos.VolumeDTO;
import spz.dae24.ejbs.PackageBean;
import spz.dae24.ejbs.VolumeBean;

import java.util.List;

@Path("volumes")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class VolumeService {
    @EJB
    private VolumeBean volumeBean;

    @EJB
    private PackageBean packageBean;

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
    @Path("/")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response createVolume(VolumeDTO volumeDTO) {
        volumeBean.create(
                PackageType.valueOf(volumeDTO.getPackageType().toUpperCase()),
                volumeDTO.getPackageCode(),
                volumeDTO.getSensors()
        );

        var volume = volumeBean.find(volumeDTO.getCode());

        return Response.ok(VolumeDTO.from(volume)).build();
    }

    @PATCH
    @Path("{code}")
    public Response updateStatusVolume(@PathParam("code") long code, VolumeDTO volumeDTO) throws EntityNotFoundException {
        volumeBean.updateStatus(code, volumeDTO.getStatus());
        var volume = volumeBean.find(code);
        return Response.ok(VolumeDTO.from(volume)).build();
    }
}
