package conector;

import java.io.Serializable;
import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;


/**
 * The persistent class for the busqueda database table.
 * 
 */
@Entity
@NamedQuery(name="Busqueda.findAll", query="SELECT b FROM Busqueda b")
public class Busqueda implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private String destino;

	private String salida;

	//bi-directional many-to-many association to Usuario
	@ManyToMany
	@JoinTable(
		name="busqueda_usuario"
		, joinColumns={
			@JoinColumn(name="BusquedaID")
			}
		, inverseJoinColumns={
			@JoinColumn(name="UsuarioID")
			}
		)
	private List<Usuario> usuarios = new ArrayList<Usuario>();

	public Busqueda() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDestino() {
		return this.destino;
	}

	public void setDestino(String destino) {
		this.destino = destino;
	}

	public String getSalida() {
		return this.salida;
	}

	public void setSalida(String salida) {
		this.salida = salida;
	}

	public List<Usuario> getUsuarios() {
		return this.usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

}