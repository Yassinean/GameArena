
package Utils;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JPAUtil {
    private static JPAUtil instance;
    private EntityManagerFactory emf;

    private JPAUtil(){
        emf = Persistence.createEntityManagerFactory("GameArena");
    }

    public static JPAUtil getInstance(){
        if (instance==null){
            synchronized (JPAUtil.class){
                if (instance==null){
                    instance = new JPAUtil();
                }
            }
        }
        return instance;
    }
    public EntityManager getEntityManager(){
        return emf.createEntityManager();
    }
    public void close() {
        if (emf != null) {
            emf.close();
        }
    }
}
