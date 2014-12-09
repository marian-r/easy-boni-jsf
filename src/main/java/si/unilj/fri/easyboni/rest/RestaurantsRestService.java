package si.unilj.fri.easyboni.rest;

import si.unilj.fri.easyboni.controller.UserBean;
import si.unilj.fri.easyboni.dto.AggregatedRating;
import si.unilj.fri.easyboni.DuplicateEntityException;
import si.unilj.fri.easyboni.service.RestaurantService;

import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Named
@Path("/restaurant")
public class RestaurantsRestService {

    @Inject
    private RestaurantService restaurantService;

    @Inject
    private UserBean userBean;

    @GET
    @Path("/add-rating")
    @Produces(MediaType.APPLICATION_JSON)
    public AggregatedRating addRatingAndReturnInJSON(
            @QueryParam("restaurantID") int restaurantId,
            @QueryParam("value") int value) {

        if (userBean.getUser().getEmail() == null) {
            throw new WebApplicationException(Response.Status.FORBIDDEN);
        }

        try {
            restaurantService.addRating(restaurantId, userBean.getUser(), value);
        } catch (DuplicateEntityException e) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }

        return restaurantService.findRatingForOne(restaurantId);
    }

    @GET
    @Path("/ratings")
    @Produces(MediaType.APPLICATION_JSON)
    public List<AggregatedRating> getRatingsInJSON() {
        return restaurantService.findAllRatings();
    }
}
