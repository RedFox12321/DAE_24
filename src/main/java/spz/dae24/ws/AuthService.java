package spz.dae24.ws;

import jakarta.ejb.EJB;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.SecurityContext;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Context;
import spz.dae24.dtos.AuthDTO;
import spz.dae24.dtos.UserDTO;
import spz.dae24.ejbs.UserBean;
import spz.dae24.entities.User;
import spz.dae24.security.Authenticated;
import spz.dae24.security.TokenIssuer;

@Path("auth")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class AuthService {
    @Inject
    private TokenIssuer issuer;

    @EJB
    private UserBean userBean;

    @Context
    private SecurityContext securityContext;

    @POST
    @Path("login")
    public Response authenticate(@Valid AuthDTO auth) {
        if (userBean.canLogin(auth.getUsername(), auth.getPassword())) {
            String token = issuer.issue(auth.getUsername());
            return Response.ok(token).build();
        }
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }

    @GET
    @Path("me")
    @Authenticated
    public Response userInfo() {
        var principal = securityContext.getUserPrincipal();
        User user = userBean.findOrFail(principal.getName());
        UserDTO userDTO = UserDTO.from(user);
        userDTO.setType(userBean.findUserType(principal.getName()));
        return Response.ok(userDTO).build();
    }
}
