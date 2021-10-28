package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;
import org.hibernate.Transaction;


import dao.UsuariosDAO;
import entity.Usuarios;

import utils.HibernateUtil;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/Login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		
		processRequest(request, response);		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String paramUsuario=request.getParameter("user");
		String paramPass=request.getParameter("password");
		
		Session session = HibernateUtil.getSessionFactory().openSession(); // Copiada la clase HibernateUtil de su proyecto

		Transaction tx = null;

		try {
			tx = session.beginTransaction();
			List<Usuarios> usuariosTabla = UsuariosDAO.getAllUsuarios(session);
			for (Usuarios usuario : usuariosTabla) {
				if (usuario.getNombre().equals(paramUsuario) && usuario.getClave().equals(paramPass)) {
					// Mostrar mensaje de bienvenida
					response.sendRedirect("./Bienvenido.html");
				}
				else {
					// Redirigir a la página de login
					response.sendRedirect("./login.html");
				}
				
			}
		} catch (Exception e) {
			if (tx != null) {
				tx.rollback();
			}
			//logger.error(String.format("%1$s: No existe el usuario.", ""), e);

		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
	

}
