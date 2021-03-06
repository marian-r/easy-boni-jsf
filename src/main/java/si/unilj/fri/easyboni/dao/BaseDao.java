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
        EntityManagerFactory emf;
        String databaseUrl = System.getenv("DATABASE_URL");

        if (databaseUrl != null) {
            StringTokenizer st = new StringTokenizer(databaseUrl, ":@/");
            String dbVendor = st.nextToken(); //if DATABASE_URL is set
            String userName = st.nextToken();
            String password = st.nextToken();
            String host = st.nextToken();
            String port = st.nextToken();
            String databaseName = st.nextToken();
            String jdbcUrl = String.format("jdbc:postgresql://%s:%s/%s?ssl=true&sslfactory=org.postgresql.ssl.NonValidatingFactory", host, port, databaseName);
            Map<String, String> properties = new HashMap<String, String>();
            properties.put("javax.persistence.jdbc.url", jdbcUrl);
            properties.put("javax.persistence.jdbc.user", userName);
            properties.put("javax.persistence.jdbc.password", password);
            properties.put("javax.persistence.jdbc.driver", "org.postgresql.Driver");
            properties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
            emf = Persistence.createEntityManagerFactory("EasyBoniPU", properties);
        } else {
            String jdbcUrl = String.format("jdbc:mysql://localhost:3306/easy_boni");
            Map<String, String> properties = new HashMap<String, String>();
            properties.put("javax.persistence.jdbc.url", jdbcUrl);
            properties.put("javax.persistence.jdbc.user", "root");
            properties.put("javax.persistence.jdbc.password", "");
            properties.put("javax.persistence.jdbc.driver", "com.mysql.jdbc.Driver");
            emf = Persistence.createEntityManagerFactory("EasyBoniPU", properties);
        }

        em = emf.createEntityManager();
    }
}
