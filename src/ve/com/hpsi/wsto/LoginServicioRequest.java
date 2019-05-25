package ve.com.hpsi.wsto;

import ve.com.hpsi.common.BaseRequest;

public class LoginServicioRequest extends BaseRequest{
	private String  userApp;
	private String  passApp;
	public String getUserApp() {
		return userApp;
	}
	public void setUserApp(String userApp) {
		this.userApp = userApp;
	}
	public String getPassApp() {
		return passApp;
	}
	public void setPassApp(String passApp) {
		this.passApp = passApp;
	}
	

}
