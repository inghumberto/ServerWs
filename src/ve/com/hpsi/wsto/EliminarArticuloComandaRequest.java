package ve.com.hpsi.wsto;

import ve.com.hpsi.common.BaseRequest;

public class EliminarArticuloComandaRequest extends BaseRequest {
	private String codigoArticulo;
	private String mesa;
	private String vendedor;
	private int posicion;
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
	public int getPosicion() {
		return posicion;
	}
	public void setPosicion(int posicion) {
		this.posicion = posicion;
	}
}
