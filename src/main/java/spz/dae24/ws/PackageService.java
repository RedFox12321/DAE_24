package spz.dae24.ws;

import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import spz.dae24.dtos.PackageDTO;
import spz.dae24.ejbs.ClientBean;
import spz.dae24.ejbs.PackageBean;
import spz.dae24.entities.Client;
import spz.dae24.security.Authenticated;

import java.util.List;

@Path("packages")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
// @Authenticated
public class PackageService {
    @EJB
    private PackageBean packageBean;
    @EJB
    private ClientBean clientBean;

    @Context
    private SecurityContext securityContext;

    @GET
    @Path("")
    @RolesAllowed("Admin")
    public List<PackageDTO> getPackages() {
        return PackageDTO.from(packageBean.findAll());
    }

    @GET
    @Path("client/{username}")
    @RolesAllowed("Admin")
    public Response getPackagesByClient(@PathParam("username") String username) {
        var client = clientBean.find(username);

        return Response.ok(PackageDTO.from(client.getPackages())).build();
    }

    @GET
    @Path("my/{username}")
    @RolesAllowed("Client")
    public Response getMyPackages(@PathParam("username") String username) {
        var principal = securityContext.getUserPrincipal();

        if(!principal.getName().equals(username)) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }

        var client = clientBean.find(username);

        return Response.ok(PackageDTO.from(client.getPackages())).build();
    }

    @GET
    @Path("{code}")
    @RolesAllowed({"Client", "Admin"})
    public Response getPackage(@PathParam("code") long code) {
        var _package = packageBean.findWithVolumes(code);

        return Response.ok(PackageDTO.from(_package)).build();
    }

    @GET
    @Path("status/{statusType}")
    // @RolesAllowed("Admin")
    public List<PackageDTO> getPackagesByStatus(@PathParam("statusType") String statusType) {
        return PackageDTO.from(packageBean.findByStatus(statusType));
    }

/*
    @POST
    @Path("")
    @RolesAllowed("Logistic")
    public Response createPackage(PackageDTO packageDTO) {
        if (packageDTO == null)
            return Response.status(Response.Status.BAD_REQUEST).build();

        var volumes = packageDTO.getVolumes();
        if (volumes.isEmpty())
            return Response.status(422, "Package needs at least 1 volume.").build();

        for (var volume : volumes) {
            var productsVolumes = volume.getVolumeProducts();
            if (productsVolumes.isEmpty())
                return Response.status(422, "One of the volumes does not have products.").build();
        }

        var _package = packageBean.findWithVolumes(code);
        return Response.ok(PackageDTO.from(_package)).build();
    }
*/
}
