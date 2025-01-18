package spz.dae24.ws;

import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.EJB;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import spz.dae24.dtos.ProductsVolumeDTO;
import spz.dae24.ejbs.ProductsVolumeBean;
import spz.dae24.exceptions.MyEntityNotFoundException;
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
    @RolesAllowed("Admin")
    public List<ProductsVolumeDTO> getAllProductsVolume() {
        return ProductsVolumeDTO.from(productsVolumeBean.findAll());
    }

    @GET
    @Path("{id}")
    @RolesAllowed("Admin")
    public Response getProductsVolume(@PathParam("id") long id) throws MyEntityNotFoundException {
        var productsVolume = productsVolumeBean.find(id);

        return Response.ok(ProductsVolumeDTO.from(productsVolume)).build();
    }

    @GET
    @Path("product/{productCode}")
    @RolesAllowed("Admin")
    public List<ProductsVolumeDTO> getProductsVolumeByProduct(@PathParam("productCode") int productCode) throws MyEntityNotFoundException {
        return ProductsVolumeDTO.from(productsVolumeBean.findByProductCode(productCode));
    }

    @GET
    @Path("volume/{volumeCode}")
    @RolesAllowed("Admin")
    public List<ProductsVolumeDTO> getProductsVolumeByVolume(@PathParam("volumeCode") long volumeCode) throws MyEntityNotFoundException {
        return ProductsVolumeDTO.from(productsVolumeBean.findByVolumeCode(volumeCode));
    }
}
