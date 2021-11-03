package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.LogManager;

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
	private static String urlPath;
	
	// Hay que crear una instancia de tipo Logger en cada clase que queramos hacer un seguimiento de log
	private static Logger logger = LogManager.getLogger(LoginServlet.class);
	//private static Logger logger = LogManager.getLogger(NOMBRE_CLASE.class);


       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    /**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		URL url = loader.getResource("log4j.properties");
		PropertyConfigurator.configure(url);
	}

    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		
		logger.info(">>>>>>>>>>>>>>>> El servlet ha entrado por la petición doGet");

		processRequest(request, response);
		

		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
		
		logger.info(">>>>>>>>>>>>>>>> El servlet ha entrado por la petición doPost");
	}
	
	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String paramUsuario=request.getParameter("user");
		String paramPass=request.getParameter("password");
		logger.info(">>>>>>>>>>>>>>> param user " + paramUsuario);
		logger.info(">>>>>>>>>>>>>>> param pass " + paramPass);
		Session session = HibernateUtil.getSessionFactory().openSession(); // Copiada la clase HibernateUtil de su proyecto

		//Transaction tx = null;

		try {
			//tx = session.beginTransaction();
			List<Usuarios> usuariosTabla = UsuariosDAO.getAllUsuarios(session);
			//tx.commit();
			logger.info(">>>>>>>>>>>>>>> PASA POR AQUI ");
			for (Usuarios usuario : usuariosTabla) {
				logger.info(">>>>>>>>>>>>>>Contenido del arrayList usuariosTabla: " + ((usuariosTabla!=null) ? "Tiene usuarios" : "Está vacío"));
				logger.info(">>>>>>>>>>>>>>NOMBRE: " + usuario.getNombre());
				logger.info(">>>>>>>>>>>>>>CLAVE: " + usuario.getClave());
				logger.info(">>>>>>>>>>>>>>EMAIL: " + usuario.getEmail());
				if (usuario.getNombre().equals(paramUsuario) && usuario.getClave().equals(paramPass)) {
					// Mostrar mensaje de bienvenida
					response.sendRedirect("Bienvenido.html");
					logger.info(">>>>>>>>>>>>>> (Usuario y Contraseña coinciden con la BBDD) Redirige a 'Bienvenido.html'");
					
				}
				else {
					// Redirigir a la página de login
					response.sendRedirect("login.html");
					logger.info(">>>>>>>>>>>>>> (Usuario y Contraseña NO coinciden con la BBDD) Redirige a 'login.html'");
					logger.info(">>>>>>>>>>>>>>> param user " + paramUsuario);
					logger.info(">>>>>>>>>>>>>>> param pass " + paramPass);
				}
				
			}
			
		} catch (Exception e) {
//			if (tx != null) {
//				tx.rollback();
//			}
			logger.error(String.format("%1$s: No existe el usuario.", ""), e);

		} finally {
			if (session != null) {
				session.close();
			}
		}
	}
	

}
