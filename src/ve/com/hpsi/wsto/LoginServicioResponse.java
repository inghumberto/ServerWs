package ve.com.hpsi.wsto;

import ve.com.hpsi.common.BaseResponse;

public class LoginServicioResponse extends BaseResponse {
	private String nombreUser;
	private String codigoUser;
	
	
	public String getCodigoUser() {
		return codigoUser;
	}

	public void setCodigoUser(String codigoUser) {
		this.codigoUser = codigoUser;
	}

	public String getNombreUser() {
		return nombreUser;
	}

	public void setNombreUser(String nombreUser) {
		this.nombreUser = nombreUser;
	}
	
}
