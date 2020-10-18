package pe.com.bodeguin.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
@Entity
@Table(name = "vendedores")
public class Vendedor {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "nombre", length = 40, nullable = false)
	private String nombre;
	@Column(name = "dni", length = 8, nullable = false)
	private String dni;
	
	@OneToMany(mappedBy = "vendedor")
	private List<Bodega> bodegas;
	
	public Vendedor() {
		this.bodegas = new ArrayList<>();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public List<Bodega> getBodegas() {
		return bodegas;
	}

	public void setBodegas(List<Bodega> bodegas) {
		this.bodegas = bodegas;
	}
}
