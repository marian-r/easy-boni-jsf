package si.unilj.fri.easyboni.rest;

import si.unilj.fri.easyboni.dto.AggregatedRating;
import si.unilj.fri.easyboni.controller.UserBean;
import si.unilj.fri.easyboni.dao.RestaurantsDao;
import si.unilj.fri.easyboni.entities.Rating;
import si.unilj.fri.easyboni.entities.RatingPK;

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
    private RestaurantsDao restaurantsDao;

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

        Rating rating = new Rating();
        RatingPK primaryKey = new RatingPK();
        primaryKey.setRestaurantId(restaurantId);
        primaryKey.setUserId(userBean.getUser().getId());
        rating.setPrimaryKey(primaryKey);
        rating.setValue(value);

        restaurantsDao.createRating(rating);

        return restaurantsDao.findRatingForOne(restaurantId);
    }

    @GET
    @Path("/ratings")
    @Produces(MediaType.APPLICATION_JSON)
    public List<AggregatedRating> getRatingsInJSON() {
        return restaurantsDao.findAllRatings();
    }
}
