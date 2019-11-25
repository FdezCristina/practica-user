package UserDAO;

import java.util.List;

import com.user.entidad.UserEntidad;


public interface UserDAO {

	/**
	 * Busca usuario por email y password
	 * @param email
	 * @param password
	 * @return
	 */
	
	List<UserEntidad> login(String email, String password);
	
	/**
	 * It returns a list of all users
	 * @return
	 */
	List<UserEntidad> get();
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	UserEntidad get(int id);
	
	/**
	 * It saves an user
	 * @param employee
	 * @return true if employee is correctly saved or false if there is an error
	 */
	boolean save(UserEntidad employee);
	
	/**
	 * 
	 * @param id
	 * @return 
	 */
	boolean delete(int id);
	
	boolean update(UserEntidad user);
}

