package spz.dae24.ws;

import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.EJB;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import spz.dae24.common.enums.Status;
import spz.dae24.dtos.PackageDTO;
import spz.dae24.dtos.PackageWithAllDTO;
import spz.dae24.dtos.PackageWithVolumesDTO;
import spz.dae24.ejbs.ClientBean;
import spz.dae24.ejbs.PackageBean;
import spz.dae24.exceptions.MyEntityExistsException;
import spz.dae24.exceptions.MyEntityNotFoundException;
import spz.dae24.security.Authenticated;

import java.util.List;

@Path("packages")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
@Authenticated
public class PackageService {
    @EJB
    private ClientBean clientBean;
    @EJB
    private PackageBean packageBean;

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
    public Response getPackagesByClient(@PathParam("username") String username) throws MyEntityNotFoundException {
        var client = clientBean.findWithPackages(username);

        return Response.ok(PackageDTO.from(client.getPackages())).build();
    }

    @GET
    @Path("my/{username}")
    @RolesAllowed("Client")
    public Response getMyPackages(@PathParam("username") String username) throws MyEntityNotFoundException {
        var principal = securityContext.getUserPrincipal();

        if(!principal.getName().equals(username)) {
            return Response.status(Response.Status.FORBIDDEN).build();
        }

        var client = clientBean.findWithPackages(username);

        return Response.ok(PackageDTO.from(client.getPackages())).build();
    }

    @GET
    @Path("{code}")
    @RolesAllowed({"Client", "Admin", "Logistic"})
    public Response getPackage(@PathParam("code") long code) throws MyEntityNotFoundException {
        var _package = packageBean.findWithVolumes(code);

        if ((securityContext.isUserInRole("Client") &&
                !securityContext.getUserPrincipal().getName().equals(_package.getClient().getUsername()))
                || (securityContext.isUserInRole("Logistic") &&
                !_package.getStatus().equals(Status.ACTIVE))
        )
            return Response.status(Response.Status.FORBIDDEN).build();


        return Response.ok(PackageWithVolumesDTO.from(_package)).build();
    }

    @PATCH
    @Path("{code}")
    @RolesAllowed({"Logistic", "Client"})
    public Response cancelPackage(@PathParam("code") long code) throws MyEntityNotFoundException, MyEntityExistsException {
        packageBean.cancelPackage(code);

        var pck = packageBean.findWithVolumes(code);
        return Response.ok(PackageWithVolumesDTO.from(pck)).build();
    }

    @GET
    @Path("status/{statusType}")
    @RolesAllowed({"Admin","Logistic"})
    public List<PackageDTO> getPackagesByStatus(@PathParam("statusType") String statusType) throws IllegalArgumentException {

        return PackageDTO.from(packageBean.findByStatus(statusType));
    }

    @POST
    @Path("")
    @RolesAllowed("Logistic")
    public Response createPackage(PackageWithAllDTO packageDTO) throws IllegalArgumentException, MyEntityNotFoundException, MyEntityExistsException {
        packageBean.makePackageOrder(packageDTO);

        var pck = packageBean.findWithVolumes(packageDTO.getCode());
        return Response.ok(PackageDTO.from(pck)).build();
    }
}
