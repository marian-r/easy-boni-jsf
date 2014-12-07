package si.unilj.fri.easyboni.entities;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Id;
import java.io.Serializable;

@Embeddable
public class RatingPK implements Serializable {
    private int restaurantId;
    private int userId;

    @Column(name = "restaurant_id")
    public int getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
    }

    @Column(name = "user_id", insertable = false, updatable = false)
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RatingPK ratingPK = (RatingPK) o;

        if (restaurantId != ratingPK.restaurantId) return false;
        if (userId != ratingPK.userId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = restaurantId;
        result = 31 * result + userId;
        return result;
    }
}
