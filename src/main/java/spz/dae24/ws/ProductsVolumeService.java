package spz.dae24.ws;

import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import spz.dae24.dtos.ProductsVolumeDTO;
import spz.dae24.ejbs.ProductsVolumeBean;

import java.util.List;


@Path("products-volume")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})

public class ProductsVolumeService {
    @EJB
    private ProductsVolumeBean productsVolumeBean;

    @GET
    @Path("")
    public List<ProductsVolumeDTO> getAllProductsVolume() {
        return ProductsVolumeDTO.from(productsVolumeBean.findAll());
    }

//    @GET
//    @Path("/volume/{code}")
//    public Response getProduct(@PathParam("code") int code) {
//
//        return Response.ok(productsVolumeDTO).build();
//    }
}
