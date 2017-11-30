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
import conector.Busqueda;
import conector.Usuario;
import conector.Vuelo;

public class Pilotadores {
	public static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	
	public static void main(String [] args)
	{
		buscar("Gran Canaria", "Madrid", encontrarUsuario("2"));
	}
	
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
			System.out.println("OK");
		}catch(Exception e){
			em.getTransaction().rollback();
			System.out.println("error "+e.getMessage());
		}finally {
			em.close();
			emf.close();
		}
	}
	
	public static void buscar(String origen, String destino, Usuario user){
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("ORM-JPA");
		EntityManager em = emf.createEntityManager();
		List<Usuario> usuarios;
		try {
			Busqueda busqueda = new Busqueda();
			busqueda.setSalida(origen);
			busqueda.setDestino(destino);
			
			em.getTransaction().begin();	
			
			usuarios = busqueda.getUsuarios();
			usuarios.add(user);
			busqueda.setUsuarios(usuarios);
			
			em.persist(busqueda);
			em.getTransaction().commit();
			System.out.println("OK");
		}catch(Exception e){
			em.getTransaction().rollback();
			System.out.println("error "+e.getMessage());
		}finally {
			em.close();
			emf.close();
			
			listarVuelos(origen,destino);
		}
	}
	
	private static void listarVuelos(String origen, String destino) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("ORM-JPA");
		EntityManager em = emf.createEntityManager();
		try {
			List listaAeropuertosOrigen;
			List listaAeropuertosDestino;
			
			Query q1 = em.createNamedQuery("Aeropuerto.Ciudad");
			q1.setParameter(1, origen);
			listaAeropuertosOrigen = q1.getResultList();
			
			q1.setParameter(1, destino);
			listaAeropuertosDestino = q1.getResultList();
			
			Vuelo vuelo;
			Aeropuerto aeropuertoOrigen;
			Aeropuerto aeropuertoDestino;
			List<Object> listaVuelos = new ArrayList();
			for(int i=0;i<listaAeropuertosOrigen.size();i++) {
				for(int j=0;j<listaAeropuertosDestino.size();j++) {
					aeropuertoOrigen =(Aeropuerto) listaAeropuertosOrigen.get(i);
					aeropuertoDestino =(Aeropuerto) listaAeropuertosDestino.get(i);

					
					Query q2 = em.createQuery("SELECT v from Vuelo v where v.aeropuerto1.id="+aeropuertoOrigen.getId()+" AND v.aeropuerto2.id="+aeropuertoDestino.getId());
					listaVuelos.addAll(q2.getResultList());
				}
			}
			
			for(int i=0;i<listaVuelos.size();i++) {
				vuelo = (Vuelo) listaVuelos.get(i);
				vuelo.leer();
			}
		}catch(Exception e){
			System.out.println("error: "+e.getMessage());
		}finally {
			em.close();
			emf.close();
		}
	}
	
	public static Usuario encontrarUsuario(String id) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("ORM-JPA");
		EntityManager em = emf.createEntityManager();
		Usuario user = new Usuario();
		try {
			user= em.find(Usuario.class, id);
			
			if(user == null) {
				System.out.println("Usuario no encontrado");
			}
			System.out.println("OK");

		}catch(Exception e){
			System.out.println("error "+e.getMessage());
		}finally {
			em.close();
			emf.close();
		}
		
		return user;
		
	}
}
