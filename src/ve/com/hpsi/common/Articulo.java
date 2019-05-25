package ve.com.hpsi.common;

public class Articulo {
	String codigo;
	String titulo;
	String tematica;
	double precio;
	String nombreIngl;
	double referenciaIva;
	String impresora;
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getTematica() {
		return tematica;
	}
	public void setTematica(String tematica) {
		this.tematica = tematica;
	}
	public double getPrecio() {
		return precio;
	}
	public void setPrecio(double precio) {
		this.precio = precio;
	}
	public String getNombreIngl() {
		return nombreIngl;
	}
	public void setNombreIngl(String nombreIngl) {
		this.nombreIngl = nombreIngl;
	}
	public double getReferenciaIva() {
		return referenciaIva;
	}
	public void setReferenciaIva(double referenciaIva) {
		this.referenciaIva = referenciaIva;
	}
	public String getImpresora() {
		return impresora;
	}
	public void setImpresora(String impresora) {
		this.impresora = impresora;
	} 
	
}
