package pilotadores;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import conector.Aeropuerto;
import conector.Vuelo;

public class GestionarVuelos {
	private static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	
	public static void nuevoVuelo(String fecha, float precio,Aeropuerto aeropuertoSalida, Aeropuerto aeropuertoLlegada) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("ORM-JPA");
		EntityManager em = emf.createEntityManager();
		
		try {
			Vuelo vuelo = new Vuelo();
			Date date = sdf.parse(fecha);
			vuelo.setFecha(date);
			vuelo.setPrecio(precio);
			vuelo.setAeropuerto1(aeropuertoSalida);
			vuelo.setAeropuerto2(aeropuertoLlegada);

			
			em.getTransaction().begin();
			em.persist(precio);
			em.getTransaction().commit();
		}catch(Exception e){
			em.getTransaction().rollback();
			System.out.println("error "+e.getMessage());
		}finally {
			em.close();
			emf.close();
		}
	}
	
	public static void cambiarFechaVuelo(Vuelo vuelo, String fecha) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("ORM-JPA");
		EntityManager em = emf.createEntityManager();
		
		try {
			if(!fecha.equals("")) {
				Date date = sdf.parse(fecha);
				vuelo.setFecha(date);
			}

			
			em.getTransaction().begin();
			em.merge(vuelo);
			em.getTransaction().commit();
		}catch(Exception e){
			em.getTransaction().rollback();
			System.out.println("error "+e.getMessage());
		}finally {
			em.close();
			emf.close();
		}
	}
	
	public static void cambiarPrecioVuelo(Vuelo vuelo, float precio) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("ORM-JPA");
		EntityManager em = emf.createEntityManager();
		
		try {
			vuelo.setPrecio(precio);

			
			em.getTransaction().begin();
			em.merge(vuelo);
			em.getTransaction().commit();
		}catch(Exception e){
			em.getTransaction().rollback();
			System.out.println("error "+e.getMessage());
		}finally {
			em.close();
			emf.close();
		}
	}
	
	public static void cambiarAeropuertosVuelo(Vuelo vuelo, Aeropuerto aeropuertoSalida, Aeropuerto aeropuertoLlegada) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("ORM-JPA");
		EntityManager em = emf.createEntityManager();
		
		try {
			vuelo.setAeropuerto1(aeropuertoSalida);
			vuelo.setAeropuerto2(aeropuertoLlegada);

			
			em.getTransaction().begin();
			em.merge(vuelo);
			em.getTransaction().commit();
		}catch(Exception e){
			em.getTransaction().rollback();
			System.out.println("error "+e.getMessage());
		}finally {
			em.close();
			emf.close();
		}
	}
	
	public static void borrarVuelo(Vuelo vuelo) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("ORM-JPA");
		EntityManager em = emf.createEntityManager();
		
		try {
			Vuelo vueloABorrar = em.find(Vuelo.class, vuelo.getId());
			
			em.getTransaction().begin();
			em.remove(vueloABorrar);
			em.getTransaction().commit();
		}catch(Exception e){
			em.getTransaction().rollback();
			System.out.println("error "+e.getMessage());
		}finally {
			em.close();
			emf.close();
		}
	}
	
	public static Vuelo encontrarVuelo(int id) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("ORM-JPA");
		EntityManager em = emf.createEntityManager();
		Vuelo vuelo = new Vuelo();
		try {
			vuelo = em.find(Vuelo.class, id);

		}catch(Exception e){
			System.out.println("error "+e.getMessage());
		}finally {
			em.close();
			emf.close();
		}
		
		return vuelo;		
	}
}
