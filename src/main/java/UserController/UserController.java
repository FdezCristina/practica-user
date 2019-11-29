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

@WebServlet("/UserController")
public class UserController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	RequestDispatcher dispatcher = null;

	UserDAO userDAO = new UserDAOImpl();

	public UserController() {
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
 
		//Si el usuario no esta en sesion redirigir al login 
		if(!userIsInSession(request)) {
			dispatcher = request.getRequestDispatcher("/views/Login.jsp");
			dispatcher.forward(request, response);
		}
	
		String action = request.getParameter("action");

		if (action == null) {
			action = "LIST";
		}

		switch (action) {

		case "LIST":
			listUserEntidad(request, response);
			break;

		case "EDIT":
			getSingleUserEntidad(request, response);
			break;

		case "DELETE":
			deleteUserEntidad(request, response);
			break;

		case "LOGIN":
			getLogin(request, response);
			break;
		default:
			listUserEntidad(request, response);
			break;

		}

	}

	private void deleteUserEntidad(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String id = request.getParameter("id");

		if (userDAO.delete(Integer.parseInt(id))) {
			request.setAttribute("NOTIFICATION", "Employee Deleted Successfully!");
		}

		listUserEntidad(request, response);
	}

	private void getSingleUserEntidad(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String id = request.getParameter("id");

		UserEntidad user = userDAO.get(Integer.parseInt(id));

		request.setAttribute("user", user);

		dispatcher = request.getRequestDispatcher("/views/user-form.jsp");

		dispatcher.forward(request, response);
	}

	private void getLogin(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String email = request.getParameter("email");
		String password = request.getParameter("password");
		
		UserEntidad user = null;
		List<UserEntidad> users = userDAO.login(email, password);

		if(users!=null && users.size()>0) {
			user = users.get(0);
		}
		
		//Estamos logados
		if(user!= null) {
		
		request.setAttribute("user", user);
		dispatcher = request.getRequestDispatcher("/views/user-form.jsp");
		}else {
			request.setAttribute("aviso", "Datos no validos");
			
		//Mostrar aviso de login invalido (navegando a la misma de login pero con mostrar aviso)
		dispatcher = request.getRequestDispatcher("/views/Login.jsp");
		}
		dispatcher.forward(request, response);
	}

	
	private void listUserEntidad(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		List<UserEntidad> theList = userDAO.get();

		request.setAttribute("list", theList);

		dispatcher = request.getRequestDispatcher("/views/user-list.jsp");

		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String id = request.getParameter("id");

		UserEntidad user = new UserEntidad();
		user.setName(request.getParameter("name"));
		
		user.setSurname(request.getParameter("surname"));
		
		user.setEmail(request.getParameter("email"));
		
		user.setPassword(request.getParameter("password"));
		
		user.setAge(Integer.parseInt(request.getParameter("age")));
		
		if (id == null || id.isEmpty()  ) {

			if (userDAO.save(user)) {
				request.setAttribute("NOTIFICATION", "User Saved Successfully!");
			}

		} else {

			user.setId(Long.parseLong(id));
			if (userDAO.update(user)) {
				request.setAttribute("NOTIFICATION", "User Updated Successfully!");
			}

		}

		listUserEntidad(request, response);
	}


	private boolean userIsInSession(HttpServletRequest request) {
		
		boolean enSesion = false;
		
		if(request.getSession().getAttribute("user")!=null) {
			enSesion = true;
		}
		return enSesion;
	}

}
