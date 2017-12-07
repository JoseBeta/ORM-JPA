package pilotadores;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import conector.Aeropuerto;
import conector.Busqueda;
import conector.Usuario;
import conector.Vuelo;

public class GestionarBusquedas {
	
	public static void nuevaBusqueda(String origen, String destino){
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("ORM-JPA");
		EntityManager em = emf.createEntityManager();
		
		try {
			Busqueda busqueda = new Busqueda();
			busqueda.setSalida(origen);
			busqueda.setDestino(destino);
			
			em.getTransaction().begin();
			
			Usuario user= em.find(Usuario.class, 1);
			busqueda.getUsuarios().add(user);
			
			em.persist(busqueda);
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
	
	public static Busqueda encontrarBusquedaSalidaYOrigen(String salida, String destino){
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("ORM-JPA");
		EntityManager em = emf.createEntityManager();
		
		Busqueda busqueda = new Busqueda();
		
		try {
			List busquedas;
			
			Query q1 = em.createNamedQuery("Busqueda.origenYDestisno");
			q1.setParameter(1, salida);
			q1.setParameter(2, destino);
			busquedas = q1.getResultList();
			busqueda = (Busqueda) busquedas.get(0);



		}catch(Exception e){
			System.out.println("error: "+e.getMessage());
		}finally {
			em.close();
			emf.close();
		}
		
		return busqueda;	
	}
	
	
	public static List<Vuelo> listarVuelos(String salida, String destino) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("ORM-JPA");
		EntityManager em = emf.createEntityManager();
		
		List<Vuelo> vuelos = new ArrayList<Vuelo>();
		
		try {
			List listaAeropuertosSalida;
			List listaAeropuertosDestino;
			
			Query q1 = em.createNamedQuery("Aeropuerto.Ciudad");
			q1.setParameter(1, salida);
			listaAeropuertosSalida = q1.getResultList();
			
			q1.setParameter(1, destino);
			listaAeropuertosDestino = q1.getResultList();
			
			Aeropuerto aeropuertoSalida;
			Aeropuerto aeropuertoDestino;
			List<Object> listaVuelos = new ArrayList();
			for(int i=0;i<listaAeropuertosSalida.size();i++) {
				for(int j=0;j<listaAeropuertosDestino.size();j++) {
					aeropuertoSalida =(Aeropuerto) listaAeropuertosSalida.get(i);
					aeropuertoDestino =(Aeropuerto) listaAeropuertosDestino.get(i);

					
					Query q2 = em.createQuery("SELECT v from Vuelo v where v.aeropuerto1.id="+aeropuertoSalida.getId()+" AND v.aeropuerto2.id="+aeropuertoDestino.getId());
					listaVuelos.addAll(q2.getResultList());
				}
			}
			
			for(int i=0;i<listaVuelos.size();i++) {
				vuelos.add((Vuelo) listaVuelos.get(i));
			}
		}catch(Exception e){
			System.out.println("error: "+e.getMessage());
		}finally {
			em.close();
			emf.close();
		}
		
		return vuelos;
	}
}
