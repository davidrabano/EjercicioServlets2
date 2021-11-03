package dao;

import java.util.List;

import org.hibernate.Session;

import entity.Usuarios;

public class UsuariosDAO {
	
	// Create
	public static void insertUsuarios(Session s, Usuarios usuarios) {
		s.save(usuarios);
	}
	
	// Read
	public static Usuarios getUsuarios(Session s, int id) {
		return s.get(Usuarios.class, id);
	}
	
	// Update
	public static void updateUsuarios(Session s, Usuarios usuarios) {
		s.update(usuarios);
	}
	
	// Delete
	public static void deleteUsuarios(Session s, Usuarios usuarios) {
		s.delete(usuarios);
	}
	
	public static List<Usuarios> getAllUsuarios(Session s) {
		//String hQuery = "from Usuarios"; // Sin ordenar
		String hQuery = "from Usuarios";
		List<Usuarios> usuariosList = s.createQuery(hQuery, Usuarios.class).list();
				   	   			           
		return usuariosList;
	}


}
