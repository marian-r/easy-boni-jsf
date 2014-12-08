package si.unilj.fri.easyboni.dao;

import org.apache.commons.io.IOUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import si.unilj.fri.easyboni.dto.AggregatedRating;
import si.unilj.fri.easyboni.dto.RestaurantTO;
import si.unilj.fri.easyboni.entities.Rating;

import javax.inject.Named;
import javax.persistence.Query;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Named
public class RestaurantsDao extends BaseDao {

    public void createRating(Rating rating) {
        em.getTransaction().begin();
        em.persist(rating);
        em.getTransaction().commit();
    }

    public AggregatedRating findRatingForOne(int restaurantId) {
        Query query = em.createNativeQuery("SELECT r.restaurant_id AS restaurantId, AVG(r.value) AS rating " +
                "FROM ratings r " +
                "WHERE r.restaurant_id = :id " +
                "GROUP BY r.restaurant_id ", "AggregatedRatings");
        query.setParameter("id", restaurantId);
        return (AggregatedRating) query.getSingleResult();
    }

    public List<AggregatedRating> findAllRatings() {
        return (List<AggregatedRating>) em.createNativeQuery("SELECT r.restaurant_id AS restaurantId, AVG(r.value) AS rating " +
                "FROM ratings r " +
                "GROUP BY r.restaurant_id", "AggregatedRatings")
                .getResultList();
    }

    public Map<Long, RestaurantTO> findAllRestaurants() {
        Map<Long, RestaurantTO> map = new HashMap<Long, RestaurantTO>();
        List<AggregatedRating> ratings = findAllRatings();

        JSONParser parser = new JSONParser();
        JSONArray a = null;
        try {
            String json = IOUtils.toString(new URL("http://easy-boni.herokuapp.com/restaurants.json"));
            a = (JSONArray) parser.parse(json);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        for (Object o : a) {
            JSONObject restaurant = (JSONObject) o;

            long id = (Long) restaurant.get("id");
            String name = (String) restaurant.get("name");
            String address = (String) restaurant.get("address");
            String price = (String) restaurant.get("price");

            RestaurantTO restaurantTO = new RestaurantTO();
            restaurantTO.setId(id);
            restaurantTO.setName(name);
            restaurantTO.setAddress(address);
            restaurantTO.setPrice(price);

            map.put(id, restaurantTO);
        }

        for (AggregatedRating rating : ratings) {
            Long key = (long) rating.getRestaurantId();
            RestaurantTO item = map.get(key);
            if  (item != null) {
                item.setRating(rating.getRating());
            }
        }

        return map;
    }
}
