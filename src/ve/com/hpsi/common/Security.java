package ve.com.hpsi.common;

import java.io.IOException;

import org.apache.log4j.Logger;

import ve.com.hpsi.beans.UsuarioWS;

public class Security {
	
	 Preference preference;
	private  UsuarioWS usuario;
	private final static Logger log = Logger.getLogger(FireBirdBD.class);

	public Security() {
		try {
			preference=new Preference(System.getProperty("user.dir"),"security.properties");

			log.debug("Load Properties"+preference.getPropFileName());
			usuario=new UsuarioWS(preference.getProperty("security.user"),preference.getProperty("security.password"));
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public  boolean autenticateUser(String user,String pass){
		boolean bandera=false;
		log.debug(">>>>>"+user+"="+this.usuario.getUserws()+"|"+pass+"="+this.usuario.getPassword());
		if(this.usuario.getUserws().equals(user)&& this.usuario.getPassword().equals(pass)){
			bandera=true;
			log.debug("Autenticate Sussess");
		}else{
			log.error("Error en la Autenticacion");;
		}
		
		return bandera;
	}
	
	
}
