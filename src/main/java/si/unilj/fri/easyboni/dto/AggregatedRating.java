package si.unilj.fri.easyboni.dto;

public class AggregatedRating {
    private int restaurantId;
    private int rating;

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

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
