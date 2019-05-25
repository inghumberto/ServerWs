package ve.com.hpsi.beans;

public class UsuarioWS {
	private String userws;
	private String password;
	
	
	public UsuarioWS(String userws, String password) {
		this.userws = userws;
		this.password = password;
	}
	public String getUserws() {
		return userws;
	}
	public void setUserws(String userws) {
		this.userws = userws;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
