package si.unilj.fri.easyboni.dao;

import si.unilj.fri.easyboni.entities.User;

import javax.inject.Named;
import javax.persistence.Query;

@Named
public class UserDao extends BaseDao {

    public void create(User user) {
        em.getTransaction().begin();
        em.persist(user);
        em.getTransaction().commit();
    }

    public User findByEmail(String email) {
        Query query = em.createQuery("SELECT u FROM User u WHERE u.email = :email");
        query.setParameter("email", email);
        return (User) query.getSingleResult();
    }
}
