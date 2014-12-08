package si.unilj.fri.easyboni.dto;

public class AggregatedRating {
    private int restaurantId;
    private float rating;

    public AggregatedRating(Integer restaurantId, Integer rating) {
        this.restaurantId = restaurantId;
        this.rating = rating;
    }

    public int getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }
}
