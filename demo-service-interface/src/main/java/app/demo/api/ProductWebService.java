package app.demo.api;

import app.demo.api.product.CreateProductRequest;
import app.demo.api.product.ProductView;
import core.framework.api.http.HTTPStatus;
import core.framework.api.web.service.GET;
import core.framework.api.web.service.POST;
import core.framework.api.web.service.Path;
import core.framework.api.web.service.PathParam;
import core.framework.api.web.service.ResponseStatus;

import java.util.Optional;

/**
 * @author neo
 */
public interface ProductWebService {
    @GET
    @Path("/product/:id")
    Optional<ProductView> get(@PathParam("id") String id);

    @POST
    @Path("/product")
    @ResponseStatus(HTTPStatus.CREATED)
    void create(CreateProductRequest request);
}
