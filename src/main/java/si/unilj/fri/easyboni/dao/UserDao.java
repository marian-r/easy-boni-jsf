package si.unilj.fri.easyboni.dao;

import si.unilj.fri.easyboni.DuplicateEntityException;
import si.unilj.fri.easyboni.entities.User;

import javax.inject.Named;
import javax.persistence.Query;
import javax.persistence.RollbackException;

@Named
public class UserDao extends BaseDao {

    public void create(User user) throws DuplicateEntityException {
        em.getTransaction().begin();
        try {
            em.persist(user);
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

    public User findByEmail(String email) {
        Query query = em.createQuery("SELECT u FROM User u WHERE u.email = :email");
        query.setParameter("email", email);
        return (User) query.getSingleResult();
    }
}
