package pilotadores;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import conector.Reserva;
import conector.Usuario;
import conector.Viajero;
import conector.Vuelo;

public class GestionarReservas {
	private static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	
	public static void nuevaReserva(Usuario user, Vuelo vuelo) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("ORM-JPA");
		EntityManager em = emf.createEntityManager();
		
		try {
			Reserva reserva = new Reserva();
			reserva.setPrecioPagado(vuelo.getPrecio());
			reserva.setUsuario(user);
			reserva.setVuelo(vuelo);
			
			em.getTransaction().begin();
			em.persist(reserva);
			em.getTransaction().commit();
		}catch(Exception e){
			em.getTransaction().rollback();
			System.out.println("error "+e.getMessage());
		}finally {
			em.close();
			emf.close();
		}
	}
	
	public static void modificarReserva(Reserva reserva, Usuario user, Vuelo vuelo) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("ORM-JPA");
		EntityManager em = emf.createEntityManager();
		
		try {
			reserva.setUsuario(user);
			reserva.setVuelo(vuelo);
			
			em.getTransaction().begin();
			em.merge(reserva);
			em.getTransaction().commit();
		}catch(Exception e){
			em.getTransaction().rollback();
			System.out.println("error "+e.getMessage());
		}finally {
			em.close();
			emf.close();
		}
	}
	
	
	public static void cambiarVuelo(Reserva reserva, Vuelo vuelo) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("ORM-JPA");
		EntityManager em = emf.createEntityManager();
		
		try {
			reserva.setVuelo(vuelo);
			
			em.getTransaction().begin();
			em.merge(reserva);
			em.getTransaction().commit();
		}catch(Exception e){
			em.getTransaction().rollback();
			System.out.println("error "+e.getMessage());
		}finally {
			em.close();
			emf.close();
		}
	}
	
	public static void borrarReserva(int id) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("ORM-JPA");
		EntityManager em = emf.createEntityManager();
		
		Reserva reservaABorrar = em.find(Reserva.class, id);
		
		try {
			em.getTransaction().begin();
			em.remove(reservaABorrar);
			em.getTransaction().commit();
		}catch(Exception e){
			em.getTransaction().rollback();
			System.out.println("error "+e.getMessage());
		}finally {
			em.close();
			emf.close();
		}
	}
	
	public static Reserva encontrarReserva(int id) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("ORM-JPA");
		EntityManager em = emf.createEntityManager();
		Reserva reserva = new Reserva();
		try {
			reserva = em.find(Reserva.class, id);

		}catch(Exception e){
			System.out.println("error "+e.getMessage());
		}finally {
			em.close();
			emf.close();
		}
		
		return reserva;		
	}
}
