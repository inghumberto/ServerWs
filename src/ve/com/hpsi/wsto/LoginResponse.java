package ve.com.hpsi.wsto;

import ve.com.hpsi.common.BaseResponse;

public class LoginResponse extends BaseResponse {
	String mesero;
	int codigoMesero;

	public String getMesero() {
		return mesero;
	}

	public void setMesero(String mesero) {
		this.mesero = mesero;
	}

	public int getCodigoMesero() {
		return codigoMesero;
	}

	public void setCodigoMesero(int codigo) {
		this.codigoMesero = codigo;
	}
	
}
