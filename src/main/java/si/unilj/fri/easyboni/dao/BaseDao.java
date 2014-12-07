package si.unilj.fri.easyboni.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public abstract class BaseDao {
    protected EntityManager em;

    public BaseDao() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("EasyBoniPU");
        em = emf.createEntityManager();
    }
}
