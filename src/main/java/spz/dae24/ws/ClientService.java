package spz.dae24.ws;

import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import spz.dae24.dtos.ClientDTO;
import spz.dae24.ejbs.ClientBean;
import spz.dae24.security.Authenticated;

import java.util.List;


@Path("clients")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
@Authenticated
public class ClientService {
    @EJB
    private ClientBean clientBean;

    @GET
    @Path("")
    @RolesAllowed("Admin")
    public List<ClientDTO> getAllClients() {
        return ClientDTO.from(clientBean.findAll());
    }

    @GET
    @Path("{username}")
    @RolesAllowed("Admin")
    public Response getClient(@PathParam("username") String username) {
        var client = clientBean.find(username);
        var clientDTO = ClientDTO.from(client);

        return Response.ok(clientDTO).build();
    }
}
