package conector;

import java.io.Serializable;
import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;


/**
 * The persistent class for the aeropuerto database table.
 * 
 */
@Entity
@NamedQueries({
	@NamedQuery(name="Aeropuerto.findAll", query="SELECT a FROM Aeropuerto a"),
	@NamedQuery(name="Aeropuerto.Ciudad", query="SELECT a FROM Aeropuerto a WHERE a.ciudad=?1")
})
public class Aeropuerto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)    
	private int id;

	private String ciudad;

	private String nombre;

	//bi-directional many-to-one association to Vuelo
	@OneToMany(mappedBy="aeropuerto1")
	private List<Vuelo> vuelos1= new ArrayList<Vuelo>();

	//bi-directional many-to-one association to Vuelo
	@OneToMany(mappedBy="aeropuerto2")
	private List<Vuelo> vuelos2= new ArrayList<Vuelo>();

	public Aeropuerto() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCiudad() {
		return this.ciudad;
	}

	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<Vuelo> getVuelos1() {
		return this.vuelos1;
	}

	public void setVuelos1(List<Vuelo> vuelos1) {
		this.vuelos1 = vuelos1;
	}

	public Vuelo addVuelos1(Vuelo vuelos1) {
		getVuelos1().add(vuelos1);
		vuelos1.setAeropuerto1(this);

		return vuelos1;
	}

	public Vuelo removeVuelos1(Vuelo vuelos1) {
		getVuelos1().remove(vuelos1);
		vuelos1.setAeropuerto1(null);

		return vuelos1;
	}

	public List<Vuelo> getVuelos2() {
		return this.vuelos2;
	}

	public void setVuelos2(List<Vuelo> vuelos2) {
		this.vuelos2 = vuelos2;
	}

	public Vuelo addVuelos2(Vuelo vuelos2) {
		getVuelos2().add(vuelos2);
		vuelos2.setAeropuerto2(this);

		return vuelos2;
	}

	public Vuelo removeVuelos2(Vuelo vuelos2) {
		getVuelos2().remove(vuelos2);
		vuelos2.setAeropuerto2(null);

		return vuelos2;
	}

}