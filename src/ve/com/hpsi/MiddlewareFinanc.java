package ve.com.hpsi;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import ve.com.hpsi.beans.interfaces.InterfaceError;
import ve.com.hpsi.wsto.LoginServicioRequest;
import ve.com.hpsi.wsto.LoginServicioResponse;

@WebService
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT)
public interface MiddlewareFinanc extends InterfaceError{
	@WebMethod	public LoginServicioResponse logingUser(@WebParam(name="request")LoginServicioRequest request);
}
