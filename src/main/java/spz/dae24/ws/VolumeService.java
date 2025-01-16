package spz.dae24.ws;

import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.EJB;
import jakarta.persistence.EntityNotFoundException;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import spz.dae24.common.enums.Status;
import spz.dae24.dtos.ProductsVolumeDTO;
import spz.dae24.dtos.VolumeDTO;
import spz.dae24.ejbs.PackageBean;
import spz.dae24.ejbs.VolumeBean;
import spz.dae24.security.Authenticated;

import java.util.List;

@Path("volumes")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
@Authenticated
public class VolumeService {
    @EJB
    private VolumeBean volumeBean;

    @EJB
    private PackageBean packageBean;

    @GET
    @Path("")
    @RolesAllowed("Admin")
    public List<VolumeDTO> getVolumes() {
        return VolumeDTO.from(volumeBean.findAll());
    }

    @GET
    @Path("{code}")
    @RolesAllowed("Admin")
    public Response getVolume(@PathParam("code") long code) {
       var volume = volumeBean.find(code);
       var volumeDTO = VolumeDTO.from(volume);
       volumeDTO.setVolumeProducts(ProductsVolumeDTO.from(volume.getProductsVolumes()));

       return Response.ok(volumeDTO).build();
    }

    @POST
    @Path("")
    @RolesAllowed("Admin")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response createVolume(VolumeDTO volumeDTO) {
        volumeBean.create(
                volumeDTO.getCode(),
                volumeDTO.getPackageType(),
                volumeDTO.getPackageCode()
        );

        var volume = volumeBean.find(volumeDTO.getCode());

        return Response.ok(VolumeDTO.from(volume)).build();
    }

    @PATCH
    @Path("{code}")
    public Response deliverVolume(@PathParam("code") long code) throws EntityNotFoundException {
        volumeBean.deliver(code);

        var volume = volumeBean.findWithSensorsAndProductsVolumes(code);
        return Response.ok(VolumeDTO.from(volume)).build();
    }
}
