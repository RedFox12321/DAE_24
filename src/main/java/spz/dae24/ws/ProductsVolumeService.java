package spz.dae24.ws;

import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import spz.dae24.dtos.ProductsVolumeDTO;
import spz.dae24.ejbs.ProductsVolumeBean;
import spz.dae24.security.Authenticated;

import java.util.List;


@Path("products-volume")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
@Authenticated
public class ProductsVolumeService {
    @EJB
    private ProductsVolumeBean productsVolumeBean;

    @GET
    @Path("")
    public List<ProductsVolumeDTO> getAllProductsVolume() {
        return ProductsVolumeDTO.from(productsVolumeBean.findAll());
    }

    @GET
    @Path("{id}")
    public Response getProductsVolume(@PathParam("id") long id) {
        var productsVolume = productsVolumeBean.find(id);

        return Response.ok(ProductsVolumeDTO.from(productsVolume)).build();
    }

//    @GET
//    @Path("/volume/{code}")
//    public Response getProduct(@PathParam("code") int code) {
//
//        return Response.ok(productsVolumeDTO).build();
//    }
}
