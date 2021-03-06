package si.unilj.fri.easyboni.dao;

import si.unilj.fri.easyboni.dto.AggregatedRating;
import si.unilj.fri.easyboni.entities.Rating;
import si.unilj.fri.easyboni.DuplicateEntityException;

import javax.inject.Named;
import javax.persistence.Query;
import javax.persistence.RollbackException;
import java.util.List;

@Named
public class RatingDao extends BaseDao {

    public void createRating(Rating rating) throws DuplicateEntityException {
        try {
            em.getTransaction().begin();
            em.persist(rating);
            em.getTransaction().commit();
        } catch (RollbackException e) {
            throw new DuplicateEntityException(e);
        } catch (RuntimeException e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        }
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
}
