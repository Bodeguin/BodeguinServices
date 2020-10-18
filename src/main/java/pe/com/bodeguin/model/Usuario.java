	package pe.com.bodeguin.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import pe.com.bodeguin.model.Authority;

@Entity
@Table(name = "usuarios")
public class Usuario {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "correo", length = 50, nullable = false)
	private String correo;
	@Column(name = "password", length = 50, nullable = false)
	private String password;
	@Column(name = "nombre", length = 40, nullable = false)
	private String nombre;
	@Column(name = "apellido_materno", length = 40, nullable = false)
	private String apellidoMaterno;
	@Column(name = "apellido_paterno", length = 40, nullable = false)
	private String apellidoPaterno;
	@Column(name = "direccion", length = 100, nullable = true)
	private String direccion;
	@Column(name = "dni", length = 8, nullable = false)
	private String dni;
	@Column(name = "isAdm", columnDefinition = "boolean", nullable = true)
	private boolean isAdm;
	
	private boolean isEnable;
	@JsonIgnore
	@OneToMany(mappedBy = "usuario")
	private List<Compra> compras;
	@JsonIgnore
	@OneToMany(mappedBy = "usuario", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<Authority> authorities;
	
	public Usuario() {
		this.compras = new ArrayList<>();
		this.authorities = new ArrayList<>();
	}
	
	public Usuario(String correo, String password) {
		this.correo = correo;
		this.password = password;
		this.authorities = new ArrayList<>();
	}
	public void addAuthority(String rol) {
		Authority authority = new Authority();
		authority.setAuthority(rol);
		authority.setUsuario(this);
		this.authorities.add(authority);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidoMaterno() {
		return apellidoMaterno;
	}

	public void setApellidoMaterno(String apellidoMaterno) {
		this.apellidoMaterno = apellidoMaterno;
	}

	public String getApellidoPaterno() {
		return apellidoPaterno;
	}

	public void setApellidoPaterno(String apellidoPaterno) {
		this.apellidoPaterno = apellidoPaterno;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public List<Compra> getCompras() {
		return compras;
	}

	public void setCompras(List<Compra> compras) {
		this.compras = compras;
	}

	public List<Authority> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(List<Authority> authorities) {
		this.authorities = authorities;
	}
	
	public boolean isEnable() {
		return isEnable;
	}

	public void setEnable(boolean isEnable) {
		this.isEnable = isEnable;
	}

	public boolean isAdm() {
		return isAdm;
	}

	public void setAdm(boolean isAdm) {
		this.isAdm = isAdm;
	}
	
}
