package ve.com.hpsi;


import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import ve.com.hpsi.beans.interfaces.InterfaceError;
import ve.com.hpsi.wsto.MesaDetallesRequest;
import ve.com.hpsi.wsto.MesaDetallesResponse;
import ve.com.hpsi.wsto.LoginServicioRequest;
import ve.com.hpsi.wsto.LoginServicioResponse;
import ve.com.hpsi.wsto.printerRequest;
import ve.com.hpsi.wsto.printerResponse;
import ve.com.hpsi.wsto.EliminarArticuloComandaRequest;
import ve.com.hpsi.wsto.EliminarArticuloResponse;
import ve.com.hpsi.wsto.EntregarArticuloComandaRequest;
import ve.com.hpsi.wsto.EntregarArticuloResponse;
import ve.com.hpsi.wsto.EnviarArticuloComandaRequest;
import ve.com.hpsi.wsto.EnviarArticuloResponse;
import ve.com.hpsi.wsto.IncluirArticuloComandaRequest;
import ve.com.hpsi.wsto.IncluirArticuloResponse;
import ve.com.hpsi.wsto.ListaArticulosChResponse;
import ve.com.hpsi.wsto.ListaArticulosPResponse;
import ve.com.hpsi.wsto.ListaMesasResponse;
import ve.com.hpsi.wsto.ListarArticulosChRequest;
import ve.com.hpsi.wsto.ListarArticulosPRequest;
import ve.com.hpsi.wsto.ListarMesasRequest;
import ve.com.hpsi.wsto.LoginRequest;
import ve.com.hpsi.wsto.LoginResponse;


@WebService
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT)
public interface Middleware extends InterfaceError{

@WebMethod	public LoginResponse loginMesero(@WebParam(name="request")LoginRequest request);
@WebMethod	public ListaMesasResponse listarMesas(@WebParam(name="request")ListarMesasRequest request);
@WebMethod	public ListaArticulosPResponse listarArticulosP(@WebParam(name="request")ListarArticulosPRequest request);
@WebMethod	public ListaArticulosChResponse listarArticulosCh(@WebParam(name="request")ListarArticulosChRequest request);
@WebMethod	public MesaDetallesResponse detallesMesa(@WebParam(name="request")MesaDetallesRequest request);
@WebMethod	public IncluirArticuloResponse incluirArticuloComanda(@WebParam(name="request")IncluirArticuloComandaRequest request);
@WebMethod	public EliminarArticuloResponse eliminarArticuloComanda(@WebParam(name="request")EliminarArticuloComandaRequest request);
@WebMethod	public EntregarArticuloResponse entregarArticuloComanda(@WebParam(name="request")EntregarArticuloComandaRequest request);
@WebMethod	public EnviarArticuloResponse enviarArticuloComanda(@WebParam(name="request")EnviarArticuloComandaRequest request);
@WebMethod	public printerResponse printEstadoCta(@WebParam(name="request")printerRequest request);

}
