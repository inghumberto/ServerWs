package ve.com.hpsi.wsto;

import ve.com.hpsi.common.BaseRequest;

public class IncluirArticuloComandaRequest extends BaseRequest {
	private String codigoArticulo;
	private String mesa;
	private String vendedor;
	private int prox;
	private String accion;
	
	
	public String getAccion() {
		return accion;
	}
	public void setAccion(String accion) {
		this.accion = accion;
	}
	public String getCodigoArticulo() {
		return codigoArticulo;
	}
	public void setCodigoArticulo(String codigoArticulo) {
		this.codigoArticulo = codigoArticulo;
	}
	public String getMesa() {
		return mesa;
	}
	public void setMesa(String mesa) {
		this.mesa = mesa;
	}
	public String getVendedor() {
		return vendedor;
	}
	public void setVendedor(String vendedor) {
		this.vendedor = vendedor;
	}
	public int getProx() {
		return prox;
	}
	public void setProx(int prox) {
		this.prox = prox;
	}
		
}
