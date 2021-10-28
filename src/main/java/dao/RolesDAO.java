package dao;

import java.util.List;

import org.hibernate.Session;

import entity.Roles;

public class RolesDAO {
	
	// Create
	public static void insertRoles(Session s, Roles roles) {
		s.save(roles);
	}
	
	// Read
	public static Roles getRoles(Session s, int id) {
		return s.get(Roles.class, id);
	}
	
	// Update
	public static void updateRoles(Session s, Roles roles) {
		s.update(roles);
	}
	
	// Delete
	public static void deleteDepartamento(Session s, Roles roles) {
		s.delete(roles);
	}
	
	public static List<Roles> getAllRoles(Session s) {
		//String hQuery = "from Roles"; // Sin ordenar
		String hQuery = "from Roles order by codigo";
		List<Roles> rolesList = s.createQuery(hQuery, Roles.class).list();
				   	   			           
		return rolesList;
	}

}
