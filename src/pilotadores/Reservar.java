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

public class Reservar {
	private static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	
	public static void reservar(Usuario user, Vuelo vuelo) {
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
	
	public static void addViajero(Viajero viajero, Reserva reserva) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("ORM-JPA");
		EntityManager em = emf.createEntityManager();
		
		try {
			viajero.getReservas().add(reserva);
			
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
	
	public static void modificarViajero(Reserva reserva, int viajeroACambiar, String dni, String fNacimiento, String nombre, int numAsiento) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("ORM-JPA");
		EntityManager em = emf.createEntityManager();
		List<Viajero> viajeros = reserva.getViajeros();
		Viajero viajero = viajeros.get(viajeroACambiar);
		try {
			if(!dni.equals("")) {
				viajeros.get(viajeroACambiar).setDni(dni);
			}
			
			if(!fNacimiento.equals("")) {
				Date date = sdf.parse(fNacimiento);
				viajeros.get(viajeroACambiar).setFNaciemiento(date);
			}
			
			if(!nombre.equals("")) {
				viajeros.get(viajeroACambiar).setNombre(nombre);
			}
			
			if(numAsiento != reserva.getViajeros().get(viajeroACambiar).getNumAsiento() && numAsiento>0 ) {
				viajeros.get(viajeroACambiar).setNumAsiento(numAsiento);
			}
			
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
}
