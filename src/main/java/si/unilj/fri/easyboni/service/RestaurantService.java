package si.unilj.fri.easyboni.service;

import si.unilj.fri.easyboni.DuplicateEntityException;
import si.unilj.fri.easyboni.dao.RatingDao;
import si.unilj.fri.easyboni.dao.RestaurantDao;
import si.unilj.fri.easyboni.dto.AggregatedRating;
import si.unilj.fri.easyboni.dto.RestaurantTO;
import si.unilj.fri.easyboni.entities.Rating;
import si.unilj.fri.easyboni.entities.RatingPK;
import si.unilj.fri.easyboni.entities.User;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.NoResultException;
import javax.persistence.RollbackException;
import java.util.List;
import java.util.Map;

@Named
public class RestaurantService {

    @Inject
    private RestaurantDao restaurantsDao;

    @Inject
    private RatingDao ratingsDao;

    public void addRating(int restaurantId, User user, int value) {
        Rating rating = new Rating();
        RatingPK primaryKey = new RatingPK();
        primaryKey.setRestaurantId(restaurantId);
        primaryKey.setUserId(user.getId());
        rating.setPrimaryKey(primaryKey);
        rating.setValue(value);

        ratingsDao.createRating(rating);
    }

    public AggregatedRating findRatingForOne(int restaurantId) {
        try {
            return ratingsDao.findRatingForOne(restaurantId);
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<AggregatedRating> findAllRatings() {
        return ratingsDao.findAllRatings();
    }

    public Map<Long, RestaurantTO> findAllRestaurants() {
        Map<Long, RestaurantTO> restaurantsMap = restaurantsDao.findAllRestaurants();
        List<AggregatedRating> ratings = findAllRatings();

        for (AggregatedRating rating : ratings) {
            Long key = (long) rating.getRestaurantId();
            RestaurantTO item = restaurantsMap.get(key);
            if  (item != null) {
                item.setRating(rating.getRating());
            }
        }

        return restaurantsMap;
    }
}
