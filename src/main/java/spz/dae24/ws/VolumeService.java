package spz.dae24.ws;

import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import spz.dae24.dtos.ProductsVolumeDTO;
import spz.dae24.dtos.SensorDTO;
import spz.dae24.dtos.VolumeDTO;
import spz.dae24.dtos.VolumeWithSensorsAndProductVolumesDTO;
import spz.dae24.ejbs.PackageBean;
import spz.dae24.ejbs.VolumeBean;
import spz.dae24.exceptions.EntityExistsException;
import spz.dae24.exceptions.EntityNotFoundException;
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
    @RolesAllowed({"Admin", "Logistic"})
    public Response getVolume(@PathParam("code") long code) throws EntityNotFoundException {
       var volume = volumeBean.findWithSensorsAndProductsVolumes(code);
       var volumeDTO = VolumeWithSensorsAndProductVolumesDTO.from(volume);

       return Response.ok(volumeDTO).build();
    }

    @POST
    @Path("")
    @RolesAllowed("Logistic")
    @Consumes({MediaType.APPLICATION_JSON})
    public Response createVolume(VolumeWithSensorsAndProductVolumesDTO volumeDTO) throws EntityNotFoundException, EntityExistsException {
        volumeBean.addVolumeToPackageOrder(volumeDTO, volumeDTO.getPackageCode());

        var volume = volumeBean.findWithSensorsAndProductsVolumes(volumeDTO.getCode());

        return Response.ok(VolumeWithSensorsAndProductVolumesDTO.from(volume)).build();
    }

    @PATCH
    @Path("{code}")
    @RolesAllowed("Logistic")
    public Response deliverVolume(@PathParam("code") long code) throws EntityNotFoundException {
        volumeBean.deliver(code);

        var volume = volumeBean.findWithSensorsAndProductsVolumes(code);
        return Response.ok(VolumeWithSensorsAndProductVolumesDTO.from(volume)).build();
    }
}
