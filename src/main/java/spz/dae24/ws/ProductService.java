package spz.dae24.ws;

import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import spz.dae24.dtos.ProductDTO;
// import spz.dae24.dtos.ProductHistoryDTO;
import spz.dae24.ejbs.ProductBean;
import spz.dae24.security.Authenticated;
import jakarta.annotation.security.RolesAllowed;

import java.util.List;


@Path("products")
@Produces({MediaType.APPLICATION_JSON})
public class ProductService {
    @EJB
    private ProductBean productBean;

    @GET
    @Path("")
    public List<ProductDTO> getAllProducts() {
        return ProductDTO.from(productBean.findAll());
    }

    @GET
    @Path("{code}")
    public Response getProduct(@PathParam("code") int code) {
        var product = productBean.find(code);
        var productDTO = ProductDTO.from(product);

        return Response.ok(productDTO).build();
    }
}
