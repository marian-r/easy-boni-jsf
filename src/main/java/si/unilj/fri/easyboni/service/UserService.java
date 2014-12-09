package si.unilj.fri.easyboni.service;

import si.unilj.fri.easyboni.DuplicateEntityException;
import si.unilj.fri.easyboni.dao.UserDao;
import si.unilj.fri.easyboni.entities.User;

import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.NoResultException;

@Named
public class UserService {

    @Inject
    private UserDao userDao;

    public void create(User user) throws DuplicateEntityException {
        userDao.create(user);
    }

    public User findByEmail(String email) {
        try {
            return userDao.findByEmail(email);
        } catch (NoResultException e) {
            return null;
        }
    }
}
