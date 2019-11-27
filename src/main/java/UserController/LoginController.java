package UserController;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.user.entidad.UserEntidad;

import UserDAO.UserDAO;
import UserDAO.UserDAOImpl;
import util.UserUtil;

@WebServlet("/LoginController")
public class LoginController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	RequestDispatcher dispatcher = null;

	UserDAO userDAO = new UserDAOImpl();

	public LoginController() {
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String action = request.getParameter("action");

		if (action == null) {
			action = "LOGIN";
		}

		switch (action) {

		case "LOGIN":
			getLogin(request, response);
			break;
		case "LOGOUT":
			cerrarSesion(request, response);
			break;	
		default:
			getLogin(request, response);
			break;

		}

	}
/**
 * Método Login
 * @param request
 * @param response
 * @throws ServletException
 * @throws IOException
 */
	
	private void getLogin(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String email = request.getParameter("email");
		String password = request.getParameter("password");

		UserEntidad user = null;
	    //Buscamos el usuario en la sesion por si ya estabamos logados
		user = (UserEntidad)request.getSession().getAttribute("user");
		
		//Buscamos con los datos del formulario
		if (email != null || password != null && user==null) {
			List<UserEntidad> users = userDAO.login(email, password);

			if (users != null && users.size() > 0) {
				user = users.get(0);
			}
		}
		// Estamos logados
		if (user != null) {

			request.setAttribute("user", user);
			request.getSession().setAttribute("user", user);
			
		//Falta buscar datos iniciales
			UserUtil.cargarDatosIniciales(request);
			dispatcher = request.getRequestDispatcher("/views/user-form.jsp");
		} else {

			if (email != null || password != null) {
				
		// Mostrar aviso de login invalido (navegando a la misma de login pero con
		// pametro de mostrar aviso)
				request.setAttribute("aviso", "Datos no validos");
			}

			dispatcher = request.getRequestDispatcher("/views/Login.jsp");
		}
		dispatcher.forward(request, response);
	}
	
/**
 * Método Logout
 * @param request
 * @param response
 * @throws ServletException
 * @throws IOException
 */
	 
	private void cerrarSesion(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		request.getSession().removeAttribute("user");
		dispatcher = request.getRequestDispatcher("/views/Login.jsp");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {


		String action = request.getParameter("action");

		if (action == null) {
			action = "LOGIN";
		}

		switch (action) {

		case "LOGIN":
			getLogin(request, response);
			break;
		case "LOGOUT":
			cerrarSesion(request, response);
			break;	
		default:
			getLogin(request, response);
			break;

		}
	}

}
