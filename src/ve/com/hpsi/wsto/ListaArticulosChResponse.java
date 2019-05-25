package ve.com.hpsi.wsto;

import ve.com.hpsi.beans.ResultArticulosCh;
import ve.com.hpsi.common.BaseResponse;

public class ListaArticulosChResponse extends BaseResponse {
	ResultArticulosCh result;

	public ResultArticulosCh getResult() {
		return result;
	}

	public void setResult(ResultArticulosCh result) {
		this.result = result;
	}
	
	
	

}
