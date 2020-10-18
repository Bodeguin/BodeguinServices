package pe.com.bodeguin.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "compras")
public class Compra {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "codigo", length = 20, nullable = false)
	private String codigo;
	@Column(name = "importe", precision = 2, nullable = false)	
	private float importe;
	@Column(name = "con_factura", columnDefinition = "boolean", nullable = true)
	private boolean conFactura;
	@Column(name = "con_boleta", columnDefinition = "boolean", nullable = true)
	private boolean conBoleta;
	@Column(name = "solo_efectivo", columnDefinition = "boolean", nullable = true)
	private boolean soloEfectivo;
	
	@OneToOne
	@JoinColumn(name = "factura_id",  nullable = true)
	private Factura factura;
	@OneToOne
	@JoinColumn(name = "boleta_id",  nullable = true)
	private Boleta boleta;
	@ManyToOne
	@JoinColumn(name = "tipoPago_id")
	private TipoPago tipoPago;
	@ManyToOne
	@JoinColumn(name = "usuario_id")
	private Usuario usuario;
	@OneToMany(mappedBy = "compra", fetch = FetchType.LAZY)
	private List<Producto> productos;
	
	public Compra() {
		this.productos = new ArrayList<>();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public float getImporte() {
		return importe;
	}

	public void setImporte(float importe) {
		this.importe = importe;
	}

	public boolean isConFactura() {
		return conFactura;
	}

	public void setConFactura(boolean conFactura) {
		this.conFactura = conFactura;
	}

	public boolean isConBoleta() {
		return conBoleta;
	}

	public void setConBoleta(boolean conBoleta) {
		this.conBoleta = conBoleta;
	}

	public boolean isSoloEfectivo() {
		return soloEfectivo;
	}

	public void setSoloEfectivo(boolean soloEfectivo) {
		this.soloEfectivo = soloEfectivo;
	}

	public Factura getFactura() {
		return factura;
	}

	public void setFactura(Factura factura) {
		this.factura = factura;
	}

	public Boleta getBoleta() {
		return boleta;
	}

	public void setBoleta(Boleta boleta) {
		this.boleta = boleta;
	}

	public TipoPago getTipoPago() {
		return tipoPago;
	}

	public void setTipoPago(TipoPago tipoPago) {
		this.tipoPago = tipoPago;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Producto> getProductos() {
		return productos;
	}

	public void setProductos(List<Producto> productos) {
		this.productos = productos;
	}
}
