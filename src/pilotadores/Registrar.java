package pilotadores;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import conector.Aeropuerto;
import conector.Reserva;
import conector.Usuario;
import conector.Vuelo;

public class Registrar {
	private static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	
	public static void registrarse(String fecha, String pass, String nombre) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("ORM-JPA");
		EntityManager em = emf.createEntityManager();
		
		try {
			Usuario user = new Usuario();
			user.setAdmin(false);
			Date date = sdf.parse(fecha);
			user.setFNacimiento(date);
			user.setContrasena(pass);
			user.setNombre(nombre);
			
			em.getTransaction().begin();
			em.persist(user);
			em.getTransaction().commit();
		}catch(Exception e){
			em.getTransaction().rollback();
			System.out.println("error "+e.getMessage());
		}finally {
			em.close();
			emf.close();
		}
	}
	
	public static void modificarUsuario(Usuario user, String fecha, String nombre) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("ORM-JPA");
		EntityManager em = emf.createEntityManager();
		
		try {
			if(!fecha.equals("")) {
				Date date = sdf.parse(fecha);
				user.setFNacimiento(date);
			}
			if(!nombre.equals("")) {
				user.setNombre(nombre);
			}

			em.getTransaction().begin();
			em.merge(user);
			em.getTransaction().commit();
		}catch(Exception e){
			em.getTransaction().rollback();
			System.out.println("error "+e.getMessage());
		}finally {
			em.close();
			emf.close();
		}
	}
	
	public static void cambiarContrasena(Usuario user, String pass) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("ORM-JPA");
		EntityManager em = emf.createEntityManager();
		
		try {
			user.setContrasena(pass);

			em.getTransaction().begin();
			em.merge(user);
			em.getTransaction().commit();
		}catch(Exception e){
			em.getTransaction().rollback();
			System.out.println("error "+e.getMessage());
		}finally {
			em.close();
			emf.close();
		}
	}
	
	public static void hacerAdministrador(Usuario user) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("ORM-JPA");
		EntityManager em = emf.createEntityManager();
		
		try {
			user.setAdmin(true);

			em.getTransaction().begin();
			em.merge(user);
			em.getTransaction().commit();
		}catch(Exception e){
			em.getTransaction().rollback();
			System.out.println("error "+e.getMessage());
		}finally {
			em.close();
			emf.close();
		}
	}
	
	public static void quitarAdministrador(Usuario user) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("ORM-JPA");
		EntityManager em = emf.createEntityManager();
		
		try {
			user.setAdmin(false);

			em.getTransaction().begin();
			em.merge(user);
			em.getTransaction().commit();
		}catch(Exception e){
			em.getTransaction().rollback();
			System.out.println("error "+e.getMessage());
		}finally {
			em.close();
			emf.close();
		}
	}
}
