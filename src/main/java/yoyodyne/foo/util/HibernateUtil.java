package yoyodyne.foo.util;

import org.apache.log4j.Logger;
import java.util.concurrent.atomic.AtomicReference;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class HibernateUtil {
    private static final Logger logger = Logger.getLogger(HibernateUtil.class);

    public static String connectionDriverClass = "com.mysql.cj.jdbc.Driver";

    public static String connectionUrl = "jdbc:mysql://db/bar?autoReconnect=true&useSSL=false";
    public static final String connectionUserName = "fakeName";
    public static final String connectionPassword = "fakePassword";

    public static final AtomicReference<EntityManagerFactory> entityManagerFactory =
            new AtomicReference<>(BogusEntityManagerFactory.INSTANCE);

    private static final ThreadLocal<EntityManager> entityManagerThreadLocal =
            ThreadLocal.withInitial(() -> {
                EntityManager em = entityManagerFactory.get().createEntityManager();
                em.getTransaction().begin();
                return em;
            });

    public static void quit() {
        EntityManager em = entityManagerThreadLocal.get();
        em.clear();
        if (em.getTransaction().isActive()) {
            try {
                em.getTransaction().rollback();
            } catch (Exception e) {
                logger.error("Caught exception trying to roll-back active transaction in quit().", e);
            }
        }

        if (em.isOpen()) {
            try {
                em.close();
            } catch (Exception e) {
                logger.error("Caught exception trying to close the EntityManager in quit().", e);
            }
        }

        entityManagerThreadLocal.remove();
    }

    public static void commit() {
        try {
            EntityManager em = entityManagerThreadLocal.get();
            if (em.isOpen()) {

                em.flush();
                if (em.getTransaction().isActive()) {
                    em.getTransaction().commit();
                }
            }
        } finally {
            quit();
        }
    }
}

