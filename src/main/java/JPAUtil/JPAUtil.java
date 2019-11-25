package JPAUtil;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Utility Class for manage JPA connections
 * @author
 */
public class JPAUtil {

	private static final EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("practica-user");
	

	public static EntityManager getEntityManager() {
		return emFactory.createEntityManager();
	}

}
