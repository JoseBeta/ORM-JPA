package conector;

import java.io.Serializable;
import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;


/**
 * The persistent class for the reserva database table.
 * 
 */
@Entity
@NamedQuery(name="Reserva.findAll", query="SELECT r FROM Reserva r")
public class Reserva implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private float precioPagado;

	//bi-directional many-to-one association to Usuario
	@ManyToOne
	@JoinColumn(name="UsuarioID")
	private Usuario usuario;

	//bi-directional many-to-one association to Vuelo
	@ManyToOne
	@JoinColumn(name="VueloID")
	private Vuelo vuelo;

	//bi-directional many-to-many association to Viajero
	@ManyToMany(mappedBy="reservas")
	private List<Viajero> viajeros= new ArrayList<Viajero>();

	public Reserva() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public float getPrecioPagado() {
		return this.precioPagado;
	}

	public void setPrecioPagado(float precioPagado) {
		this.precioPagado = precioPagado;
	}

	public Usuario getUsuario() {
		return this.usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Vuelo getVuelo() {
		return this.vuelo;
	}

	public void setVuelo(Vuelo vuelo) {
		this.vuelo = vuelo;
	}

	public List<Viajero> getViajeros() {
		return this.viajeros;
	}

	public void setViajeros(List<Viajero> viajeros) {
		this.viajeros = viajeros;
	}

}