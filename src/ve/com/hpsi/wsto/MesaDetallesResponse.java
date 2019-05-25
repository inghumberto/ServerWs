package ve.com.hpsi.wsto;

import ve.com.hpsi.beans.ResultDetalle;
import ve.com.hpsi.common.BaseResponse;
import ve.com.hpsi.common.ResultDetalleBest;

public class MesaDetallesResponse extends BaseResponse{
	ResultDetalleBest result;

	public ResultDetalleBest getResult() {
		return result;
	}

	public void setResult(ResultDetalleBest result) {
		this.result = result;
	}
}
