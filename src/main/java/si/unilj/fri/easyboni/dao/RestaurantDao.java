package si.unilj.fri.easyboni.dao;

import org.apache.commons.io.IOUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import si.unilj.fri.easyboni.dto.RestaurantTO;

import javax.inject.Named;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

@Named
public class RestaurantDao {

    public static final String RESTAURANTS_URL = "http://easy-boni.herokuapp.com/restaurants.json";

    public Map<Long, RestaurantTO> findAllRestaurants() {
        Map<Long, RestaurantTO> map = new HashMap<Long, RestaurantTO>();

        JSONParser parser = new JSONParser();
        JSONArray a = null;
        try {
            String json = IOUtils.toString(new URL(RESTAURANTS_URL), "UTF-8");
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

        return map;
    }
}
