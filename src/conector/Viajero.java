package conector;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the viajero database table.
 * 
 */
@Entity
@NamedQuery(name="Viajero.findAll", query="SELECT v FROM Viajero v")
public class Viajero implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int id;

	private String dni;

	@Temporal(TemporalType.DATE)
	private Date FNaciemiento;

	private String nombre;

	private int numAsiento;

	//bi-directional many-to-many association to Reserva
	@ManyToMany
	@JoinTable(
		name="viajero_reserva"
		, joinColumns={
			@JoinColumn(name="ViajeroID")
			}
		, inverseJoinColumns={
			@JoinColumn(name="ReservaID")
			}
		)
	private List<Reserva> reservas;

	public Viajero() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDni() {
		return this.dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public Date getFNaciemiento() {
		return this.FNaciemiento;
	}

	public void setFNaciemiento(Date FNaciemiento) {
		this.FNaciemiento = FNaciemiento;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getNumAsiento() {
		return this.numAsiento;
	}

	public void setNumAsiento(int numAsiento) {
		this.numAsiento = numAsiento;
	}

	public List<Reserva> getReservas() {
		return this.reservas;
	}

	public void setReservas(List<Reserva> reservas) {
		this.reservas = reservas;
	}

}