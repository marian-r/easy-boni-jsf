package si.unilj.fri.easyboni.controller;

import si.unilj.fri.easyboni.dto.RestaurantTO;
import si.unilj.fri.easyboni.service.RestaurantService;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.Map;

@Named
@ManagedBean(name = "restaurantBean", eager = true)
@SessionScoped
public class RestaurantBean {

    @Inject
    private RestaurantService restaurantService;

    @Inject
    private UserBean userBean;

    @Named
    private Map<Long, RestaurantTO> restaurants;

    //private Map<Long, Float> ratings;

    public Map<Long, RestaurantTO> getRestaurants() {
        if (restaurants == null) {
            restaurants = restaurantService.findAllRestaurants();
        }

        return restaurants;
    }

    public String addRating() {
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        int restaurantId = Integer.parseInt(params.get("restaurantID"));
        int value = Integer.parseInt(params.get("value"));

        if (userBean.getUser().getEmail() == null) {
            return "";
        }

        restaurantService.addRating(restaurantId, userBean.getUser(), value);
        restaurants = restaurantService.findAllRestaurants();

        return "/index";
    }
}