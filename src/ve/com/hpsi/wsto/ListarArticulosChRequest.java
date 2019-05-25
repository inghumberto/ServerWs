package ve.com.hpsi.wsto;

import ve.com.hpsi.common.BaseRequest;

public class ListarArticulosChRequest extends BaseRequest {
	String tematica;

	public String getTematica() {
		return tematica;
	}

	public void setTematica(String t) {
		tematica = t;
	}
	
}
