package UserDAO;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.user.entidad.UserEntidad;

import JPAUtil.JPAUtil;


public class UserDAOImpl implements UserDAO{


	
EntityManager manager;
	
	
	@Override
	public List<UserEntidad> get() {
		try {
			manager = JPAUtil.getEntityManager();
			TypedQuery<UserEntidad> namedQuery = manager.createNamedQuery("UserEntidad.findAll", UserEntidad.class);
			List<UserEntidad> results = namedQuery.getResultList();
			manager.close();
			return results;
		}catch(Exception e) {
			e.printStackTrace();
		}

		return new ArrayList<UserEntidad>();
	}

	@Override
	public UserEntidad get(int id) {
		UserEntidad user = null;
		try {
			manager = JPAUtil.getEntityManager();
			user = manager.find(UserEntidad.class, id);
			manager.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return user;
	}

	@Override
	public boolean save(UserEntidad user) {
		boolean flag = false;
		try {
			manager = JPAUtil.getEntityManager();
			manager.getTransaction().begin();   
			manager.persist(user); 
			manager.getTransaction().commit(); 
			manager.close();
			flag = true;
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return flag;
	}

	@Override
	public boolean delete(int id) {
		boolean flag = false;
		try {
			manager = JPAUtil.getEntityManager();
			manager.getTransaction().begin();   
			UserEntidad employee = manager.find(UserEntidad.class, id);
			if (employee != null) {
				manager.remove(employee);
				manager.getTransaction().commit(); 
				flag = true;
			}
			manager.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public boolean update(UserEntidad user) {
		boolean flag = false;
		try {
			manager = JPAUtil.getEntityManager();
			manager.getTransaction().begin();   
			manager.merge(user); 
			manager.getTransaction().commit(); 
			manager.close();
			flag = true;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public List<UserEntidad> login(String email, String password) {
		try {
			manager = JPAUtil.getEntityManager();
			TypedQuery<UserEntidad> namedQuery = manager.createNamedQuery("UserEntidad.login", UserEntidad.class);
			List<UserEntidad> results = namedQuery.getResultList();
			manager.close();
			return results;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return new ArrayList<UserEntidad>();
	}
	
	
}
