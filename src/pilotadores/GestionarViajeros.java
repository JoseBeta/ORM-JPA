package pilotadores;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import conector.Busqueda;
import conector.Reserva;
import conector.Viajero;
import conector.Vuelo;

public class GestionarViajeros {
	private static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	
	public static void nuevoViajero(String dni,String fecha, String nombre, int numAsiento) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("ORM-JPA");
		EntityManager em = emf.createEntityManager();
		
		try {
			Viajero viajero = new Viajero();
			Date date = sdf.parse(fecha);
			viajero.setFNaciemiento(date);
			viajero.setDni(dni);
			viajero.setNombre(nombre);
			viajero.setNumAsiento(numAsiento);
			
			em.getTransaction().begin();
			em.persist(viajero);
			em.getTransaction().commit();
		}catch(Exception e){
			em.getTransaction().rollback();
			System.out.println("error "+e.getMessage());
		}finally {
			em.close();
			emf.close();
		}
	}
	
	public static void modificarViajero(Viajero viajero, String dni,String fecha, String nombre, int numAsiento) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("ORM-JPA");
		EntityManager em = emf.createEntityManager();
		
		try {
			if(!dni.equals("")) {
				viajero.setDni(dni);
			}
			if(!fecha.equals("")) {
				Date date = sdf.parse(fecha);
				viajero.setFNaciemiento(date);
			}
			if(!nombre.equals("")) {
				viajero.setNombre(nombre);
			}
			viajero.setNumAsiento(numAsiento);
			
			em.getTransaction().begin();
			em.merge(viajero);
			em.getTransaction().commit();
		}catch(Exception e){
			em.getTransaction().rollback();
			System.out.println("error "+e.getMessage());
		}finally {
			em.close();
			emf.close();
		}
	}
	
	public static void reservar(Viajero viajero, Reserva reserva) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("ORM-JPA");
		EntityManager em = emf.createEntityManager();
		
		try {
			viajero.getReservas().add(reserva);
			
			em.getTransaction().begin();
			em.remove(viajero);
			em.getTransaction().commit();
		}catch(Exception e){
			em.getTransaction().rollback();
			System.out.println("error "+e.getMessage());
		}finally {
			em.close();
			emf.close();
		}
	}
	
	public static void borrarViajero(Viajero viajero) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("ORM-JPA");
		EntityManager em = emf.createEntityManager();
		
		try {
			Viajero viajeroABorrar = em.find(Viajero.class, viajero.getId());
			
			em.getTransaction().begin();
			em.remove(viajeroABorrar);
			em.getTransaction().commit();
		}catch(Exception e){
			em.getTransaction().rollback();
			System.out.println("error "+e.getMessage());
		}finally {
			em.close();
			emf.close();
		}
	}
	
	public static Viajero encontrarViajero(int id) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("ORM-JPA");
		EntityManager em = emf.createEntityManager();
		Viajero viajero = new Viajero();
		try {
			viajero = em.find(Viajero.class, id);

		}catch(Exception e){
			System.out.println("error "+e.getMessage());
		}finally {
			em.close();
			emf.close();
		}
		
		return viajero;		
	}
	
	public static Viajero encontrarViajeroPorDni(String dni) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("ORM-JPA");
		EntityManager em = emf.createEntityManager();
		
		Viajero viajero = new Viajero();
		
		try {
			List viajeros;
			
			Query q1 = em.createNamedQuery("Viajero.findByDNI");
			q1.setParameter(1, dni.toLowerCase());
			viajeros = q1.getResultList();
			viajero = (Viajero) viajeros.get(0);



		}catch(Exception e){
			System.out.println("error: "+e.getMessage());
		}finally {
			em.close();
			emf.close();
		}
		
		return viajero;	
	}
}
