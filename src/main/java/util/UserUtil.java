package util;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.user.entidad.UserEntidad;

import UserDAO.UserDAO;
import UserDAO.UserDAOImpl;

public class UserUtil {
	

	public static void cargarDatosInicales(HttpServletRequest request) {
		
		UserDAO userDAO = new UserDAOImpl();
		List<UserEntidad> results = new ArrayList<UserEntidad>();
		results = userDAO.get();
		request.setAttribute("theList", results);
		
	}

}
