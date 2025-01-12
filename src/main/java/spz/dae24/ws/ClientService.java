package spz.dae24.ws;

import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import spz.dae24.dtos.ClientDTO;
import spz.dae24.ejbs.ClientBean;

import java.util.List;


@Path("clients")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class ClientService {
    @EJB
    private ClientBean clientBean;

    @GET
    @Path("")
    public List<ClientDTO> getAllClients() {
        return ClientDTO.from(clientBean.findAll());
    }

    @GET
    @Path("{id}")
    public Response getClient(@PathParam("id") int id) {
        var client = clientBean.find(id);
        var clientDTO = ClientDTO.from(client);

        return Response.ok(clientDTO).build();
    }

}
