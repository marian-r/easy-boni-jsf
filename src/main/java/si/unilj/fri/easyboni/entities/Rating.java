package si.unilj.fri.easyboni.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "ratings")
public class Rating implements Serializable {
    private RatingPK primaryKey;
    private int value;
    private User user;

    @EmbeddedId
    public RatingPK getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(RatingPK pk) {
        primaryKey = pk;
    }

    @Basic
    @Column(name = "value")
    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Rating rating = (Rating) o;

        if (!primaryKey.equals(rating.primaryKey)) return false;
        if (value != rating.value) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = primaryKey.hashCode();
        result = 31 * result + value;
        return result;
    }
}
