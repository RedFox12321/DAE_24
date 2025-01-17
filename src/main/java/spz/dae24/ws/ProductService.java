package spz.dae24.ws;

import jakarta.ejb.EJB;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import spz.dae24.dtos.ProductDTO;
import spz.dae24.ejbs.ProductBean;
import spz.dae24.exceptions.EntityNotFoundException;

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
    public Response getProduct(@PathParam("code") int code) throws EntityNotFoundException {
        var product = productBean.find(code);
        var productDTO = ProductDTO.from(product);

        return Response.ok(productDTO).build();
    }
}
