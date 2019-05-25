package ve.com.hpsi.wsto;

import ve.com.hpsi.beans.ResultArticulos;
import ve.com.hpsi.common.BaseResponse;

public class ListaArticulosPResponse extends BaseResponse {
	ResultArticulos result;

	public ResultArticulos getResult() {
		return result;
	}

	public void setResult(ResultArticulos result) {
		this.result = result;
	}
	
	
}
