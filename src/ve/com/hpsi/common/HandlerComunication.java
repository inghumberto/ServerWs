package ve.com.hpsi.common;

import java.io.IOException;


import org.apache.log4j.Logger;

import ve.com.hpsi.beans.interfaces.InterfaceError;
import ve.com.hpsi.impl.MiddlewareImpl;

public class HandlerComunication implements InterfaceError{
	public  Preference pre_error;
	private final static Logger log = Logger.getLogger(MiddlewareImpl.class);
	
	public HandlerComunication(){
		try {
			this.pre_error = new Preference(System.getProperty("user.dir"), "\\Error.properties");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		log.debug("Load Properties" + pre_error.getPropFileName());
	}
	
	public  BaseResponse handlerError(BaseResponse response, String Error) {
			
			response.setCodigo(Error);
			response.setMesaje(pre_error.getPropertie(Error));
			log.debug("Response Message:" + pre_error.getPropertie(Error));
		
		return response;
	}
}
