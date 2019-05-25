package ve.com.hpsi.wsto;

import ve.com.hpsi.common.BaseResponse;
import ve.com.hpsi.beans.Result;
public class ListaMesasResponse extends BaseResponse {
	Result result;

	public Result getResult() {
		return result;
	}

	public void setResult(Result result) {
		this.result = result;
	}
	
}
