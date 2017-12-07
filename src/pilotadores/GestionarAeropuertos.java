package pilotadores;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import conector.Aeropuerto;

public class GestionarAeropuertos {
	
	public static void nuevoAeropuerto(String ciudad, String nombre) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("ORM-JPA");
		EntityManager em = emf.createEntityManager();
		
		try {
			Aeropuerto aeropuerto = new Aeropuerto();
			aeropuerto.setCiudad(ciudad);
			aeropuerto.setNombre(nombre);
			
			em.getTransaction().begin();
			em.persist(aeropuerto);
			em.getTransaction().commit();
		}catch(Exception e){
			em.getTransaction().rollback();
			System.out.println("error "+e.getMessage());
		}finally {
			em.close();
			emf.close();
		}
	}
	
	public static void modificarAeropuerto(Aeropuerto aeropuerto, String ciudad, String nombre) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("ORM-JPA");
		EntityManager em = emf.createEntityManager();
		
		try {
			if(!ciudad.equals("")) {
				aeropuerto.setCiudad(ciudad);
			}
			if(!nombre.equals("")) {
				aeropuerto.setNombre(nombre);
			}
			
			em.getTransaction().begin();
			em.merge(aeropuerto);
			em.getTransaction().commit();
		}catch(Exception e){
			em.getTransaction().rollback();
			System.out.println("error "+e.getMessage());
		}finally {
			em.close();
			emf.close();
		}
	}
	
	public static void borrarAeropuerto(Aeropuerto aeropuerto) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("ORM-JPA");
		EntityManager em = emf.createEntityManager();
		
		try {
			Aeropuerto aeropuertoABorrar = em.find(Aeropuerto.class, aeropuerto.getId());
			
			em.getTransaction().begin();
			em.remove(aeropuertoABorrar);
			em.getTransaction().commit();
		}catch(Exception e){
			em.getTransaction().rollback();
			System.out.println("error "+e.getMessage());
		}finally {
			em.close();
			emf.close();
		}
	}
	
	public static Aeropuerto encontrarAeropuerto(int id) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("ORM-JPA");
		EntityManager em = emf.createEntityManager();
		Aeropuerto aeropuerto = new Aeropuerto();
		try {
			aeropuerto = em.find(Aeropuerto.class, id);

		}catch(Exception e){
			System.out.println("error "+e.getMessage());
		}finally {
			em.close();
			emf.close();
		}
		
		return aeropuerto;		
	}

}
