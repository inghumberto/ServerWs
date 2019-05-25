<<<<<<< HEAD:src/ve/com/hpsi/impl/MiddlewareImpl.java
package ve.com.hpsi.impl;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.jws.WebService;
import org.apache.log4j.Logger;

import ve.com.hpsi.Middleware;
import ve.com.hpsi.beans.ArticuloChild;
import ve.com.hpsi.beans.ArticuloDetalle;
import ve.com.hpsi.beans.ArticuloDetalleBest;
import ve.com.hpsi.beans.MesaBean;
import ve.com.hpsi.beans.Result;
import ve.com.hpsi.beans.ResultArticulos;
import ve.com.hpsi.beans.ResultArticulosCh;
import ve.com.hpsi.beans.ResultDetalle;
import ve.com.hpsi.common.Articulo;
import ve.com.hpsi.common.FireBirdBD;
import ve.com.hpsi.common.HandlerComunication;
import ve.com.hpsi.common.Preference;
import ve.com.hpsi.common.Printer;
import ve.com.hpsi.common.ResultDetalleBest;
import ve.com.hpsi.common.Security;
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

@WebService(endpointInterface = "ve.com.hpsi.Middleware")
public class MiddlewareImpl implements Middleware {
	private FireBirdBD fr = new FireBirdBD();
	private Security sec;
	private Preference pre_error;
	HandlerComunication handlerError;
	private final static Logger log = Logger.getLogger(MiddlewareImpl.class);

	public MiddlewareImpl() {
		this.sec = new Security();
		this.handlerError = new HandlerComunication();
		try {
			this.pre_error = new Preference(System.getProperty("user.dir"), "\\Error.properties");

			log.debug("Load Properties" + pre_error.getPropFileName());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			log.error("Error cargando Properties");
		}
	}

	public LoginResponse loginMesero(LoginRequest request) {
		// TODO Auto-generated method stub
		log.debug("Inicio de servicio:Listar Mesas");
		LoginResponse response = new LoginResponse();

		this.sec = new Security();
		if (sec.autenticateUser(request.getUser(), request.getPassword())) {
			log.debug("Autenticacion Realizada con exito");
			/*
			 * Comienzo del servicio
			 * 
			 */

			try {
				String campos = "*";
				String tabla = "claves";
				String condicion = " where PASSWORDNO='" + request.getMesero() + "'";
				String orden = "";
				ResultSet rs = fr.select(campos, tabla, condicion, orden);
				String passwd = "";
				String mesero = null;
				int codigo = 0;
				while (rs.next()) {
					log.debug(rs.getString("LOGIN").toString());
					passwd = rs.getString("PASSWORDNO").toString();
					mesero = rs.getString("LOGIN").toString();
					codigo= Integer.parseInt(rs.getString("CODIGO_VENDEDOR").toString());
				}
				if (!passwd.equals("")) {
					response = (LoginResponse) handlerError.handlerError(response, ERROR_SUSSES);
					response.setMesero(mesero);
					response.setCodigoMesero(codigo);
				} else {
					handlerError.handlerError(response, ERROR_NOTFOUND);
					response.setMesero("0");
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block

				e.printStackTrace();
				handlerError.handlerError(response, ERROR_GENERAL);
				log.error(e.getMessage());
			} catch (Exception e) {

				e.printStackTrace();
				handlerError.handlerError(response, ERROR_GENERAL);
				log.error(e.getMessage());

			}

			/*
			 * 
			 * FIN DEL SERVICCIO LOGICA
			 */
		} else {
			handlerError.handlerError(response, ERROR_AUT);
			log.error(pre_error.getPropertie(ERROR_AUT));
		}
		return response;
	}

	@Override
	public ListaMesasResponse listarMesas(ListarMesasRequest request) {
		// TODO Auto-generated method stub
		log.debug("Inicio de servicio:Login");
		ListaMesasResponse response = new ListaMesasResponse();

		this.sec = new Security();
		if (sec.autenticateUser(request.getUser(), request.getPassword())) {
			log.debug("Autenticacion Realizada con exito");
			/*
			 * Comienzo del servicio
			 * 
			 */

			try {
				String campos = "*";
				String tabla = "FACTURASABIERTAS";
				String condicion = "";
				String orden = "order by ORDERNO ASC";
				ResultSet rs = fr.select(campos, tabla, condicion, orden);

				ArrayList<MesaBean> result = new ArrayList<MesaBean>();
				Result resulta = new Result();

				int reg = 0;
				while (rs.next()) {
					if (rs.getString("ORDERNO") != null) {
						MesaBean mesa = new MesaBean();
						rs.getString("ORDERNO");
						double dob=Double.parseDouble(rs.getString("ORDERNO").toString());
						mesa.setNumero((int)dob+"");
						mesa.setStatus(rs.getString("COMPANY").toString());
						mesa.setMesero(rs.getString("CODIGO_VENDEDOR"));
						result.add(mesa);
						reg++;
					}

				}
				resulta.setMesa(result);
				log.debug("Cantidad de Rs->" + reg);
				if (reg != 0) {
					handlerError.handlerError(response, ERROR_SUSSES);
					response.setResult(resulta);
				} else {
					handlerError.handlerError(response, ERROR_NOTFOUND_MESAS);
				}

				response.setResult(resulta);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				handlerError.handlerError(response, ERROR_GENERAL);
				log.error(e.getCause());
				log.error(e.getMessage());
			} catch (Exception e) {
				handlerError.handlerError(response, ERROR_GENERAL);
				log.error(e.getMessage());
				log.error(e.getCause());

			}
			/*
			 * 
			 * FIN DEL SERVICCIO LOGICA
			 */
		} else {
			handlerError.handlerError(response, ERROR_AUT);
			log.error(pre_error.getPropertie(ERROR_AUT));
		}
		return response;
	}

	@Override
	public ListaArticulosPResponse listarArticulosP(ListarArticulosPRequest request) {
		// TODO Auto-generated method stub
		log.debug("Inicio de servicio: listarArticulosP");
		ListaArticulosPResponse response = new ListaArticulosPResponse();

		this.sec = new Security();
		if (sec.autenticateUser(request.getUser(), request.getPassword())) {
			log.debug("Autenticacion Realizada con exito");
			/*
			 * Comienzo del servicio
			 * 
			 */

			try {
				String campos = "TEMATICA";
				String tabla = "LIBROS";
				String condicion = "GROUP BY TEMATICA";
				String orden = "";
				ResultSet rs = fr.select(campos, tabla, condicion, orden);

				ArrayList<String> result = new ArrayList<String>();
				ResultArticulos resulta = new ResultArticulos();

				int reg = 0;
				while (rs.next()) {
					if (rs.getString("TEMATICA") != null) {
						result.add(rs.getString("TEMATICA"));
						reg++;
					}
				}
				resulta.setArticulos(result);
				log.debug("Cantidad de Rs->" + reg);
				if (reg != 0) {
					handlerError.handlerError(response, ERROR_SUSSES);
					response.setResult(resulta);
				} else {
					handlerError.handlerError(response, ERROR_NOTFOUND_ARTS);
				}

				response.setResult(resulta);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				handlerError.handlerError(response, ERROR_GENERAL);
				log.error(e.getCause());
				log.error(e.getMessage());
			} catch (Exception e) {
				handlerError.handlerError(response, ERROR_GENERAL);
				log.error(e.getMessage());
				log.error(e.getCause());

			}
			/*
			 * 
			 * FIN DEL SERVICCIO LOGICA
			 */
		} else {
			handlerError.handlerError(response, ERROR_AUT);
			log.error(pre_error.getPropertie(ERROR_AUT));
		}
		return response;
	}

	@Override
	public ListaArticulosChResponse listarArticulosCh(ListarArticulosChRequest request) {
		log.debug("Inicio de servicio: listarArticulosCH");
		ListaArticulosChResponse response = new ListaArticulosChResponse();

		this.sec = new Security();
		if (sec.autenticateUser(request.getUser(), request.getPassword())) {
			log.debug("Autenticacion Realizada con exito");
			/*
			 * Comienzo del servicio
			 * 
			 */

			try {
				String campos = "CODIGO_LIBRO,TITULO,PRECIOREFERENCIA,IMPRESOR_PARA_COMANDA";
				String tabla = "LIBROS";
				String condicion = "WHERE TEMATICA ='"+request.getTematica()+"'";
				String orden = "";
				ResultSet rs = fr.select(campos, tabla, condicion, orden);

				ArrayList<ArticuloChild> result = new ArrayList<ArticuloChild>();
				ResultArticulosCh resulta = new ResultArticulosCh();

				int reg = 0;
				while (rs.next()) {
					ArticuloChild art=new ArticuloChild();
					if (rs.getString("CODIGO_LIBRO") != null) {
						art.setCodLibro(rs.getString("CODIGO_LIBRO"));
						art.setNombre(rs.getString("TITULO"));
						art.setPrecio(Double.parseDouble(rs.getString("PRECIOREFERENCIA")));
						art.setImpresora(rs.getString("IMPRESOR_PARA_COMANDA"));
						reg++;
					}
					result.add(art);
				}
				resulta.setResult(result);
				log.debug("Cantidad de Rs->" + reg);
				if (reg != 0) {
					handlerError.handlerError(response, ERROR_SUSSES);
					response.setResult(resulta);
				} else {
					handlerError.handlerError(response, ERROR_NOTFOUND_ARTS);
				}

				response.setResult(resulta);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				handlerError.handlerError(response, ERROR_GENERAL);
				log.error(e.getCause());
				log.error(e.getMessage());
			} catch (Exception e) {
				handlerError.handlerError(response, ERROR_GENERAL);
				log.error(e.getMessage());
				log.error(e.getCause());

			}
			/*
			 * 
			 * FIN DEL SERVICCIO LOGICA
			 */
		} else {
			handlerError.handlerError(response, ERROR_AUT);
			log.error(pre_error.getPropertie(ERROR_AUT));
		}
		return response;
	}

	@Override
	public MesaDetallesResponse detallesMesa(MesaDetallesRequest request) {
		log.debug("Inicio de servicio: detallesMesa");
		MesaDetallesResponse response = new MesaDetallesResponse();

		this.sec = new Security();
		if (sec.autenticateUser(request.getUser(), request.getPassword())) {
			log.debug("Autenticacion Realizada con exito");
			/*
			 * Comienzo del servicio
			 * 
			 */

			try {
				String campos = "*";
				String tabla = "ITEMSFACTURASABIERTAS";
				String condicion = "WHERE ORDERNO ='"+request.getMesa()+"'";
				String orden = "";
				ResultSet rs = fr.select(campos, tabla, condicion, orden);

				ArrayList<ArticuloDetalle> result = new ArrayList<ArticuloDetalle>();
				ResultDetalleBest resulta = new ResultDetalleBest();

				int reg = 0;
				while (rs.next()) {
					ArticuloDetalle srd=new ArticuloDetalle();
					if (rs.getString("DESCRIPCION") != null) {
						srd.setCodigo(rs.getString("CODIGO_LIBRO"));
						srd.setCodigoCom(rs.getString("ORDERNO"));
						srd.setCantidad(rs.getInt("ITEMNO"));
						srd.setDesc(rs.getDouble("ESDESCUENTO"));
						srd.setInstruCli(rs.getString("DESCRIPCION1"));
						srd.setMesero(rs.getString("DEPARTAMENTO"));
						srd.setPrecio(rs.getDouble("EXTPRICE"));
						srd.setSubTotal(rs.getDouble("TAXTOTAL"));
						srd.setComandaEnviada(rs.getString("COMANDA_ENVIADA"));
						srd.setEntregado(rs.getString("ENTREGADO"));
						srd.setDescripcion(rs.getString("DESCRIPCION"));
						result.add(srd);
						reg++;
					}
				}
				log.debug("Cantidad de Rs->" + reg);
				if (reg != 0) {resulta.setArticulo(result);
				response.setResult(resulta);
					handlerError.handlerError(response, ERROR_SUSSES);
					
				} else {
					handlerError.handlerError(response, ERROR_NOTFOUND_ARTS);
				}

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				handlerError.handlerError(response, ERROR_GENERAL);
				log.error(e.getCause());
				log.error(e.getMessage());
			} catch (Exception e) {
				handlerError.handlerError(response, ERROR_GENERAL);
				log.error(e.getMessage());
				log.error(e.getCause());

			}
			/*
			 * 
			 * FIN DEL SERVICCIO LOGICA
			 */
		} else {
			handlerError.handlerError(response, ERROR_AUT);
			log.error(pre_error.getPropertie(ERROR_AUT));
		}
		return response;
	}

	
	@Override
	public IncluirArticuloResponse incluirArticuloComanda(IncluirArticuloComandaRequest request) {
		// TODO Auto-generated method stub
		log.debug("Inicio de servicio: incluirArticuloComanda");
		IncluirArticuloResponse response = new IncluirArticuloResponse();

		this.sec = new Security();
		if (sec.autenticateUser(request.getUser(), request.getPassword())) {
			try {
			log.debug("Autenticacion Realizada con exito");
			/*
			 * Comienzo del servicio
			 * 
			 */
			
		if(!request.getAccion().equals("update")){
			boolean resUp=fr.update("FACTURASABIERTAS", " AMOUNTPAID=0,TAXTOTAL=0 ,COMPANY='CUENTA ABIERTA' ,CODIGO_VENDEDOR="+request.getVendedor()+""," ORDERNO="+request.getMesa());
			log.debug("Abriendo mesa"+request.getMesa()+" con estado"+request.getAccion());
			
		}
			
			Articulo ar=new Articulo();
			ResultSet rs=fr.select("*", "LIBROS", "WHERE CODIGO_LIBRO='"+request.getCodigoArticulo()+"'", "");
			while(rs.next()){
				ar.setCodigo(rs.getString("CODIGO_LIBRO"));
				ar.setImpresora(rs.getString("IMPRESOR_PARA_COMANDA"));
				ar.setPrecio(rs.getDouble("PRECIO_DETAL"));
				ar.setReferenciaIva(rs.getDouble("REFENCIACONIVA"));
				ar.setTitulo(rs.getString("TITULO"));
				log.debug(rs.getString("CODIGO_LIBRO"));
				log.debug(rs.getString("IMPRESOR_PARA_COMANDA"));
				log.debug(rs.getDouble("PRECIO_DETAL"));
				log.debug(rs.getDouble("REFENCIACONIVA"));
				log.debug(rs.getString("TITULO"));
			}
			
			if(ar.getCodigo().equals(null)){
				handlerError.handlerError(response, ERROR_NOTINSERT_ARTS);
			}
			
				String tabla = "ITEMSFACTURASABIERTAS";
				String campos = "ORDERNO,ITEMNO,CODIGO_LIBRO,DESCRIPCION,EXTPRICE ,TAXTOTAL , DEPARTAMENTO ,FECHA,ESDESCUENTO,ENTREGADO ,COMANDA_ENVIADA ";
				String valores = request.getMesa()+" ,"+request.getProx()+",'"+ar.getCodigo()+"','"+ar.getTitulo()+"' ,'"+ar.getPrecio()+"' ,'"+ar.getPrecio()+"' ,'"+request.getVendedor()+"' ,'"+getFecha()+"',0  ,'NO' ,'NO'";
				boolean resultado = fr.insert(tabla, campos, valores);
				double precio=0;
				precio=ar.getPrecio()+ar.getReferenciaIva();
				log.debug("precio"+ar.getPrecio());
				log.debug("precio mas impuesto"+precio);
				
				
				//actualizar Total
				boolean resUp=fr.update("FACTURASABIERTAS", " AMOUNTPAID=AMOUNTPAID+"+precio+",TAXTOTAL=TAXTOTAL+"+precio, " ORDERNO="+request.getMesa());
				
					if (resUp) {
						handlerError.handlerError(response, ERROR_SUSSES);
					}else {
					handlerError.handlerError(response, ERROR_NOTINSERT_ARTS);
				}

			} catch (Exception e) {
				handlerError.handlerError(response, ERROR_GENERAL);
				log.error(e.getMessage());
				log.error(e.getCause());
				e.printStackTrace();
				handlerError.handlerError(response, ERROR_NOTINSERT_ARTS);
			}
			/*
			 * 
			 * FIN DEL SERVICCIO LOGICA
			 */
		} else {
			handlerError.handlerError(response, ERROR_AUT);
			log.error(pre_error.getPropertie(ERROR_AUT));
		}
		return response;
	
	}
	private String getFecha(){
		Calendar fecha = new GregorianCalendar();
        //Obtenemos el valor del año, mes, día,
        //hora, minuto y segundo del sistema
        //usando el método get y el parámetro correspondiente
        int año = fecha.get(Calendar.YEAR);
        int mes = fecha.get(Calendar.MONTH);
        int dia = fecha.get(Calendar.DAY_OF_MONTH);
        int hora = fecha.get(Calendar.HOUR_OF_DAY);
        int minuto = fecha.get(Calendar.MINUTE);
        int segundo = fecha.get(Calendar.SECOND);
        String Fecha=año+"-"+(mes+1)+"-"+dia+" "+hora+":"+minuto+":"+segundo;
        log.debug(Fecha);
		return Fecha;
	}

	@Override
	public EliminarArticuloResponse eliminarArticuloComanda(EliminarArticuloComandaRequest request) {
		// TODO Auto-generated method stub
		log.debug("Inicio de servicio: incluirArticuloComanda");
		EliminarArticuloResponse response = new EliminarArticuloResponse();

		this.sec = new Security();
		if (sec.autenticateUser(request.getUser(), request.getPassword())) {
			try {
			log.debug("Autenticacion Realizada con exito");
			/*
			 * Comienzo del servicio
			 * 
			 */
			Articulo ar=new Articulo();
			ResultSet rs=fr.select("*", "LIBROS", "WHERE CODIGO_LIBRO='"+request.getCodigoArticulo()+"'", "");
			while(rs.next()){
				ar.setCodigo(rs.getString("CODIGO_LIBRO"));
				ar.setImpresora(rs.getString("IMPRESOR_PARA_COMANDA"));
				ar.setPrecio(rs.getDouble("PRECIO_DETAL"));
				ar.setReferenciaIva(rs.getDouble("REFENCIACONIVA"));
				ar.setTitulo(rs.getString("TITULO"));
				log.debug(rs.getString("CODIGO_LIBRO"));
				log.debug(rs.getString("IMPRESOR_PARA_COMANDA"));
				log.debug(rs.getDouble("PRECIO_DETAL"));
				log.debug(rs.getDouble("REFENCIACONIVA"));
				log.debug(rs.getString("TITULO"));
			}
			
			if(ar.getCodigo().equals(null)){
				handlerError.handlerError(response, ERROR_NOTINSERT_ARTS);
			}
			
				String tabla = "ITEMSFACTURASABIERTAS";
				String condicion = "ITEMNO="+request.getPosicion()+ " AND ORDERNO="+request.getMesa();
				boolean resultado = fr.delete(tabla,  condicion);
				double precio=0;
				precio=ar.getPrecio()+ar.getReferenciaIva();
				log.debug("precio"+ar.getPrecio());
				log.debug("precio mas impuesto"+precio);
				
				if (resultado) {
					handlerError.handlerError(response, ERROR_SUSSES);
				}else {
				handlerError.handlerError(response, ERROR_NOTINSERT_ARTS);
			}
				//actualizar Total

				
				boolean resUpa=fr.update("FACTURASABIERTAS", " AMOUNTPAID=AMOUNTPAID-"+precio+",TAXTOTAL=TAXTOTAL-"+precio, " ORDERNO="+request.getMesa());
				
					if (resUpa) {
						handlerError.handlerError(response, ERROR_SUSSES);
					}else {
					handlerError.handlerError(response, ERROR_NOTINSERT_ARTS);
				}

			} catch (Exception e) {
				handlerError.handlerError(response, ERROR_GENERAL);
				log.error(e.getMessage());
				log.error(e.getCause());
				e.printStackTrace();

			}
			/*
			 * 
			 * FIN DEL SERVICCIO LOGICA
			 */
		} else {
			handlerError.handlerError(response, ERROR_AUT);
			log.error(pre_error.getPropertie(ERROR_AUT));
		}
		return response;
	}

	@Override
	public EntregarArticuloResponse entregarArticuloComanda(EntregarArticuloComandaRequest request) {
		// TODO Auto-generated method stub
		log.debug("Inicio de servicio: incluirArticuloComanda");
		EntregarArticuloResponse  response = new EntregarArticuloResponse();

		this.sec = new Security();
		if (sec.autenticateUser(request.getUser(), request.getPassword())) {
			try {
			log.debug("Autenticacion Realizada con exito");
			/*
			 * Comienzo del servicio
			 * 
			 */
			
			boolean resUp=fr.update("ITEMSFACTURASABIERTAS", " ENTREGADO='SI'" ,"ORDERNO="+request.getMesa());
					if (resUp) {
						handlerError.handlerError(response, ERROR_SUSSES);
					}else {
					handlerError.handlerError(response, ERROR_NOTINSERT_ARTS);
				}

			} catch (Exception e) {
				handlerError.handlerError(response, ERROR_GENERAL);
				log.error(e.getMessage());
				log.error(e.getCause());
				e.printStackTrace();
				handlerError.handlerError(response, ERROR_NOTINSERT_ARTS);
			}
			/*
			 * 
			 * FIN DEL SERVICCIO LOGICA
			 */
		} else {
			handlerError.handlerError(response, ERROR_AUT);
			log.error(pre_error.getPropertie(ERROR_AUT));
		}
		return response;
	}

	@Override
	public EnviarArticuloResponse enviarArticuloComanda(EnviarArticuloComandaRequest request) {
		// TODO Auto-generated method stub
		log.debug("Inicio de servicio: enviarArticuloComanda");
		EnviarArticuloResponse  response = new EnviarArticuloResponse();

		this.sec = new Security();
		if (sec.autenticateUser(request.getUser(), request.getPassword())) {
			try {
			log.debug("Autenticacion Realizada con exito");
			/*
			 * Comienzo del servicio
			 * 
			 */
			//En este punto debo realizar la impresion de la comanda
			
			Printer administradorImpresora=new Printer();
			String campos = "*";
			String tabla = "ITEMSFACTURASABIERTAS";
			String condicion = "WHERE ORDERNO ='"+request.getMesa()+"'";
			String orden = "";
			ResultSet rs = fr.select(campos, tabla, condicion, orden);

			ArrayList<ArticuloDetalle> result = new ArrayList<ArticuloDetalle>();
			ResultDetalleBest resulta = new ResultDetalleBest();

			int reg = 0;
			while (rs.next()) {
				if (rs.getString("DESCRIPCION") != null) {
					String []arr=getImpresoraDescripcion(rs.getString("CODIGO_LIBRO"));
					administradorImpresora.imprimir(arr[0], arr[1]);
					reg++;
				}
			}
			
			boolean resUp=fr.update("ITEMSFACTURASABIERTAS", " COMANDA_ENVIADA='SI'" ,"ORDERNO="+request.getMesa());
					if (resUp) {
						handlerError.handlerError(response, ERROR_SUSSES);
					}else {
					handlerError.handlerError(response, ERROR_NOTINSERT_ARTS);
				}

			} catch (Exception e) {
				handlerError.handlerError(response, ERROR_GENERAL);
				log.error(e.getMessage());
				log.error(e.getCause());
				e.printStackTrace();
				handlerError.handlerError(response, ERROR_NOTINSERT_ARTS);
			}
			/*
			 * 
			 * FIN DEL SERVICCIO LOGICA
			 */
		} else {
			handlerError.handlerError(response, ERROR_AUT);
			log.error(pre_error.getPropertie(ERROR_AUT));
		}
		return response;
		}

	@Override
	public printerResponse printEstadoCta(printerRequest request) {
		// TODO Auto-generated method stub
		log.debug("Inicio de servicio: enviarArticuloComanda");
		printerResponse  response = new printerResponse();

		this.sec = new Security();
		if (sec.autenticateUser(request.getUser(), request.getPassword())) {
			try {
			log.debug("Autenticacion Realizada con exito");
			/*
			 * Comienzo del servicio
			 * 
			 */
			
			boolean resUp=fr.update("ITEMSFACTURASABIERTAS", " COMANDA_ENVIADA='SI'" ,"ORDERNO="+request.getUser());
					if (resUp) {
						handlerError.handlerError(response, ERROR_SUSSES);
					}else {
					handlerError.handlerError(response, ERROR_NOTINSERT_ARTS);
				}

			} catch (Exception e) {
				handlerError.handlerError(response, ERROR_GENERAL);
				log.error(e.getMessage());
				log.error(e.getCause());
				e.printStackTrace();
				handlerError.handlerError(response, ERROR_NOTINSERT_ARTS);
			}
			/*
			 * 
			 * FIN DEL SERVICCIO LOGICA
			 */
		} else {
			handlerError.handlerError(response, ERROR_AUT);
			log.error(pre_error.getPropertie(ERROR_AUT));
		}
		return response;
		}

	public String[] getImpresoraDescripcion(String codigo){
		String[] info=new String[2];
		String campos = "TITULO,IMPRESOR_PARA_COMANDA";
		String tabla = "LIBROS";
		String condicion = "WHERE CODIGO_LIBRO ='"+codigo+"'";
		String orden = "";
		ResultSet rs = fr.select(campos, tabla, condicion, orden);

		
		ResultDetalleBest resulta = new ResultDetalleBest();

		int reg = 0;
		try {
			while (rs.next()) {
				if (rs.getString("TITULO") != null) {
					info[0]=rs.getString("TITULO");
					info[1]=rs.getString("IMPRESOR_PARA_COMANDA");
					reg++;
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return info;
	}
}
=======
package ve.com.hpsi;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.jws.WebService;
import org.apache.log4j.Logger;
import ve.com.hpsi.beans.ArticuloChild;
import ve.com.hpsi.beans.ArticuloDetalle;
import ve.com.hpsi.beans.ArticuloDetalleBest;
import ve.com.hpsi.beans.MesaBean;
import ve.com.hpsi.beans.Result;
import ve.com.hpsi.beans.ResultArticulos;
import ve.com.hpsi.beans.ResultArticulosCh;
import ve.com.hpsi.beans.ResultDetalle;
import ve.com.hpsi.common.Articulo;
import ve.com.hpsi.common.FireBirdBD;
import ve.com.hpsi.common.HandlerComunication;
import ve.com.hpsi.common.Preference;
import ve.com.hpsi.common.ResultDetalleBest;
import ve.com.hpsi.common.Security;
import ve.com.hpsi.wsto.MesaDetallesRequest;
import ve.com.hpsi.wsto.MesaDetallesResponse;
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

@WebService(endpointInterface = "ve.com.hpsi.Middleware")
public class MiddlewareImpl implements Middleware {
	private FireBirdBD fr = new FireBirdBD();
	private Security sec;
	private Preference pre_error;
	HandlerComunication handlerError;
	private final static Logger log = Logger.getLogger(MiddlewareImpl.class);

	public MiddlewareImpl() {
		this.sec = new Security();
		this.handlerError = new HandlerComunication();
		try {
			this.pre_error = new Preference(System.getProperty("user.dir"), "\\Error.properties");

			log.debug("Load Properties" + pre_error.getPropFileName());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			log.error("Error cargando Properties");
		}
	}

	public LoginResponse loginMesero(LoginRequest request) {
		// TODO Auto-generated method stub
		log.debug("Inicio de servicio:Listar Mesas");
		LoginResponse response = new LoginResponse();

		this.sec = new Security();
		if (sec.autenticateUser(request.getUser(), request.getPassword())) {
			log.debug("Autenticacion Realizada con exito");
			/*
			 * Comienzo del servicio
			 * 
			 */

			try {
				String campos = "*";
				String tabla = "claves";
				String condicion = " where PASSWORDNO='" + request.getMesero() + "'";
				String orden = "";
				ResultSet rs = fr.select(campos, tabla, condicion, orden);
				String passwd = "";
				String mesero = null;
				int codigo = 0;
				while (rs.next()) {
					log.debug(rs.getString("LOGIN").toString());
					passwd = rs.getString("PASSWORDNO").toString();
					mesero = rs.getString("LOGIN").toString();
					codigo= Integer.parseInt(rs.getString("CODIGO_VENDEDOR").toString());
				}
				if (!passwd.equals("")) {
					response = (LoginResponse) handlerError.handlerError(response, ERROR_SUSSES);
					response.setMesero(mesero);
					response.setCodigoMesero(codigo);
				} else {
					handlerError.handlerError(response, ERROR_NOTFOUND);
					response.setMesero("0");
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block

				e.printStackTrace();
				handlerError.handlerError(response, ERROR_GENERAL);
				log.error(e.getMessage());
			} catch (Exception e) {

				e.printStackTrace();
				handlerError.handlerError(response, ERROR_GENERAL);
				log.error(e.getMessage());

			}

			/*
			 * 
			 * FIN DEL SERVICCIO LOGICA
			 */
		} else {
			handlerError.handlerError(response, ERROR_AUT);
			log.error(pre_error.getPropertie(ERROR_AUT));
		}
		return response;
	}

	@Override
	public ListaMesasResponse listarMesas(ListarMesasRequest request) {
		// TODO Auto-generated method stub
		log.debug("Inicio de servicio:Login");
		ListaMesasResponse response = new ListaMesasResponse();

		this.sec = new Security();
		if (sec.autenticateUser(request.getUser(), request.getPassword())) {
			log.debug("Autenticacion Realizada con exito");
			/*
			 * Comienzo del servicio
			 * 
			 */

			try {
				String campos = "*";
				String tabla = "FACTURASABIERTAS";
				String condicion = "";
				String orden = "order by ORDERNO ASC";
				ResultSet rs = fr.select(campos, tabla, condicion, orden);

				ArrayList<MesaBean> result = new ArrayList<MesaBean>();
				Result resulta = new Result();

				int reg = 0;
				while (rs.next()) {
					if (rs.getString("ORDERNO") != null) {
						MesaBean mesa = new MesaBean();
						rs.getString("ORDERNO");
						double dob=Double.parseDouble(rs.getString("ORDERNO").toString());
						mesa.setNumero((int)dob+"");
						mesa.setStatus(rs.getString("COMPANY").toString());
						mesa.setMesero(rs.getString("CODIGO_VENDEDOR"));
						result.add(mesa);
						reg++;
					}

				}
				resulta.setMesa(result);
				log.debug("Cantidad de Rs->" + reg);
				if (reg != 0) {
					handlerError.handlerError(response, ERROR_SUSSES);
					response.setResult(resulta);
				} else {
					handlerError.handlerError(response, ERROR_NOTFOUND_MESAS);
				}

				response.setResult(resulta);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				handlerError.handlerError(response, ERROR_GENERAL);
				log.error(e.getCause());
				log.error(e.getMessage());
			} catch (Exception e) {
				handlerError.handlerError(response, ERROR_GENERAL);
				log.error(e.getMessage());
				log.error(e.getCause());

			}
			/*
			 * 
			 * FIN DEL SERVICCIO LOGICA
			 */
		} else {
			handlerError.handlerError(response, ERROR_AUT);
			log.error(pre_error.getPropertie(ERROR_AUT));
		}
		return response;
	}

	@Override
	public ListaArticulosPResponse listarArticulosP(ListarArticulosPRequest request) {
		// TODO Auto-generated method stub
		log.debug("Inicio de servicio: listarArticulosP");
		ListaArticulosPResponse response = new ListaArticulosPResponse();

		this.sec = new Security();
		if (sec.autenticateUser(request.getUser(), request.getPassword())) {
			log.debug("Autenticacion Realizada con exito");
			/*
			 * Comienzo del servicio
			 * 
			 */

			try {
				String campos = "TEMATICA";
				String tabla = "LIBROS";
				String condicion = "GROUP BY TEMATICA";
				String orden = "";
				ResultSet rs = fr.select(campos, tabla, condicion, orden);

				ArrayList<String> result = new ArrayList<String>();
				ResultArticulos resulta = new ResultArticulos();

				int reg = 0;
				while (rs.next()) {
					if (rs.getString("TEMATICA") != null) {
						result.add(rs.getString("TEMATICA"));
						reg++;
					}
				}
				resulta.setArticulos(result);
				log.debug("Cantidad de Rs->" + reg);
				if (reg != 0) {
					handlerError.handlerError(response, ERROR_SUSSES);
					response.setResult(resulta);
				} else {
					handlerError.handlerError(response, ERROR_NOTFOUND_ARTS);
				}

				response.setResult(resulta);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				handlerError.handlerError(response, ERROR_GENERAL);
				log.error(e.getCause());
				log.error(e.getMessage());
			} catch (Exception e) {
				handlerError.handlerError(response, ERROR_GENERAL);
				log.error(e.getMessage());
				log.error(e.getCause());

			}
			/*
			 * 
			 * FIN DEL SERVICCIO LOGICA
			 */
		} else {
			handlerError.handlerError(response, ERROR_AUT);
			log.error(pre_error.getPropertie(ERROR_AUT));
		}
		return response;
	}

	@Override
	public ListaArticulosChResponse listarArticulosCh(ListarArticulosChRequest request) {
		log.debug("Inicio de servicio: listarArticulosCH");
		ListaArticulosChResponse response = new ListaArticulosChResponse();

		this.sec = new Security();
		if (sec.autenticateUser(request.getUser(), request.getPassword())) {
			log.debug("Autenticacion Realizada con exito");
			/*
			 * Comienzo del servicio
			 * 
			 */

			try {
				String campos = "CODIGO_LIBRO,TITULO,PRECIOREFERENCIA,IMPRESOR_PARA_COMANDA";
				String tabla = "LIBROS";
				String condicion = "WHERE TEMATICA ='"+request.getTematica()+"'";
				String orden = "";
				ResultSet rs = fr.select(campos, tabla, condicion, orden);

				ArrayList<ArticuloChild> result = new ArrayList<ArticuloChild>();
				ResultArticulosCh resulta = new ResultArticulosCh();

				int reg = 0;
				while (rs.next()) {
					ArticuloChild art=new ArticuloChild();
					if (rs.getString("CODIGO_LIBRO") != null) {
						art.setCodLibro(rs.getString("CODIGO_LIBRO"));
						art.setNombre(rs.getString("TITULO"));
						art.setPrecio(Double.parseDouble(rs.getString("PRECIOREFERENCIA")));
						art.setImpresora(rs.getString("IMPRESOR_PARA_COMANDA"));
						reg++;
					}
					result.add(art);
				}
				resulta.setResult(result);
				log.debug("Cantidad de Rs->" + reg);
				if (reg != 0) {
					handlerError.handlerError(response, ERROR_SUSSES);
					response.setResult(resulta);
				} else {
					handlerError.handlerError(response, ERROR_NOTFOUND_ARTS);
				}

				response.setResult(resulta);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				handlerError.handlerError(response, ERROR_GENERAL);
				log.error(e.getCause());
				log.error(e.getMessage());
			} catch (Exception e) {
				handlerError.handlerError(response, ERROR_GENERAL);
				log.error(e.getMessage());
				log.error(e.getCause());

			}
			/*
			 * 
			 * FIN DEL SERVICCIO LOGICA
			 */
		} else {
			handlerError.handlerError(response, ERROR_AUT);
			log.error(pre_error.getPropertie(ERROR_AUT));
		}
		return response;
	}

	@Override
	public MesaDetallesResponse detallesMesa(MesaDetallesRequest request) {
		log.debug("Inicio de servicio: detallesMesa");
		MesaDetallesResponse response = new MesaDetallesResponse();

		this.sec = new Security();
		if (sec.autenticateUser(request.getUser(), request.getPassword())) {
			log.debug("Autenticacion Realizada con exito");
			/*
			 * Comienzo del servicio
			 * 
			 */

			try {
				String campos = "*";
				String tabla = "ITEMSFACTURASABIERTAS";
				String condicion = "WHERE ORDERNO ='"+request.getMesa()+"'";
				String orden = "";
				ResultSet rs = fr.select(campos, tabla, condicion, orden);

				ArrayList<ArticuloDetalle> result = new ArrayList<ArticuloDetalle>();
				ResultDetalleBest resulta = new ResultDetalleBest();

				int reg = 0;
				while (rs.next()) {
					ArticuloDetalle srd=new ArticuloDetalle();
					if (rs.getString("DESCRIPCION") != null) {
						srd.setCodigo(rs.getString("CODIGO_LIBRO"));
						srd.setCodigoCom(rs.getString("ORDERNO"));
						srd.setCantidad(rs.getInt("ITEMNO"));
						srd.setDesc(rs.getDouble("ESDESCUENTO"));
						srd.setInstruCli(rs.getString("DESCRIPCION1"));
						srd.setMesero(rs.getString("DEPARTAMENTO"));
						srd.setPrecio(rs.getDouble("EXTPRICE"));
						srd.setSubTotal(rs.getDouble("TAXTOTAL"));
						srd.setComandaEnviada(rs.getString("COMANDA_ENVIADA"));
						srd.setEntregado(rs.getString("ENTREGADO"));
						srd.setDescripcion(rs.getString("DESCRIPCION"));
						result.add(srd);
						reg++;
					}
				}
				log.debug("Cantidad de Rs->" + reg);
				if (reg != 0) {resulta.setArticulo(result);
				response.setResult(resulta);
					handlerError.handlerError(response, ERROR_SUSSES);
					
				} else {
					handlerError.handlerError(response, ERROR_NOTFOUND_ARTS);
				}

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				handlerError.handlerError(response, ERROR_GENERAL);
				log.error(e.getCause());
				log.error(e.getMessage());
			} catch (Exception e) {
				handlerError.handlerError(response, ERROR_GENERAL);
				log.error(e.getMessage());
				log.error(e.getCause());

			}
			/*
			 * 
			 * FIN DEL SERVICCIO LOGICA
			 */
		} else {
			handlerError.handlerError(response, ERROR_AUT);
			log.error(pre_error.getPropertie(ERROR_AUT));
		}
		return response;
	}

	
	@Override
	public IncluirArticuloResponse incluirArticuloComanda(IncluirArticuloComandaRequest request) {
		// TODO Auto-generated method stub
		log.debug("Inicio de servicio: incluirArticuloComanda");
		IncluirArticuloResponse response = new IncluirArticuloResponse();

		this.sec = new Security();
		if (sec.autenticateUser(request.getUser(), request.getPassword())) {
			try {
			log.debug("Autenticacion Realizada con exito");
			/*
			 * Comienzo del servicio
			 * 
			 */
			
		if(!request.getAccion().equals("update")){
			boolean resUp=fr.update("FACTURASABIERTAS", " AMOUNTPAID=0,TAXTOTAL=0 ,COMPANY='CUENTA ABIERTA' ,CODIGO_VENDEDOR="+request.getVendedor()+""," ORDERNO="+request.getMesa());
			log.debug("Abriendo mesa"+request.getMesa()+" con estado"+request.getAccion());
			
		}
			
			Articulo ar=new Articulo();
			ResultSet rs=fr.select("*", "LIBROS", "WHERE CODIGO_LIBRO='"+request.getCodigoArticulo()+"'", "");
			while(rs.next()){
				ar.setCodigo(rs.getString("CODIGO_LIBRO"));
				ar.setImpresora(rs.getString("IMPRESOR_PARA_COMANDA"));
				ar.setPrecio(rs.getDouble("PRECIO_DETAL"));
				ar.setReferenciaIva(rs.getDouble("REFENCIACONIVA"));
				ar.setTitulo(rs.getString("TITULO"));
				log.debug(rs.getString("CODIGO_LIBRO"));
				log.debug(rs.getString("IMPRESOR_PARA_COMANDA"));
				log.debug(rs.getDouble("PRECIO_DETAL"));
				log.debug(rs.getDouble("REFENCIACONIVA"));
				log.debug(rs.getString("TITULO"));
			}
			
			if(ar.getCodigo().equals(null)){
				handlerError.handlerError(response, ERROR_NOTINSERT_ARTS);
			}
			
				String tabla = "ITEMSFACTURASABIERTAS";
				String campos = "ORDERNO,ITEMNO,CODIGO_LIBRO,DESCRIPCION,EXTPRICE ,TAXTOTAL , DEPARTAMENTO ,FECHA,ESDESCUENTO,ENTREGADO ,COMANDA_ENVIADA ";
				String valores = request.getMesa()+" ,"+request.getProx()+",'"+ar.getCodigo()+"','"+ar.getTitulo()+"' ,'"+ar.getPrecio()+"' ,'"+ar.getPrecio()+"' ,'"+request.getVendedor()+"' ,'"+getFecha()+"',0  ,'NO' ,'NO'";
				boolean resultado = fr.insert(tabla, campos, valores);
				double precio=0;
				precio=ar.getPrecio()+ar.getReferenciaIva();
				log.debug("precio"+ar.getPrecio());
				log.debug("precio mas impuesto"+precio);
				
				
				//actualizar Total
				boolean resUp=fr.update("FACTURASABIERTAS", " AMOUNTPAID=AMOUNTPAID+"+precio+",TAXTOTAL=TAXTOTAL+"+precio, " ORDERNO="+request.getMesa());
				
					if (resUp) {
						handlerError.handlerError(response, ERROR_SUSSES);
					}else {
					handlerError.handlerError(response, ERROR_NOTINSERT_ARTS);
				}

			} catch (Exception e) {
				handlerError.handlerError(response, ERROR_GENERAL);
				log.error(e.getMessage());
				log.error(e.getCause());
				e.printStackTrace();
				handlerError.handlerError(response, ERROR_NOTINSERT_ARTS);
			}
			/*
			 * 
			 * FIN DEL SERVICCIO LOGICA
			 */
		} else {
			handlerError.handlerError(response, ERROR_AUT);
			log.error(pre_error.getPropertie(ERROR_AUT));
		}
		return response;
	
	}
	private String getFecha(){
		Calendar fecha = new GregorianCalendar();
        //Obtenemos el valor del año, mes, día,
        //hora, minuto y segundo del sistema
        //usando el método get y el parámetro correspondiente
        int año = fecha.get(Calendar.YEAR);
        int mes = fecha.get(Calendar.MONTH);
        int dia = fecha.get(Calendar.DAY_OF_MONTH);
        int hora = fecha.get(Calendar.HOUR_OF_DAY);
        int minuto = fecha.get(Calendar.MINUTE);
        int segundo = fecha.get(Calendar.SECOND);
        String Fecha=año+"-"+(mes+1)+"-"+dia+" "+hora+":"+minuto+":"+segundo;
        log.debug(Fecha);
		return Fecha;
	}

	@Override
	public EliminarArticuloResponse eliminarArticuloComanda(EliminarArticuloComandaRequest request) {
		// TODO Auto-generated method stub
		log.debug("Inicio de servicio: incluirArticuloComanda");
		EliminarArticuloResponse response = new EliminarArticuloResponse();

		this.sec = new Security();
		if (sec.autenticateUser(request.getUser(), request.getPassword())) {
			try {
			log.debug("Autenticacion Realizada con exito");
			/*
			 * Comienzo del servicio
			 * 
			 */
			Articulo ar=new Articulo();
			ResultSet rs=fr.select("*", "LIBROS", "WHERE CODIGO_LIBRO='"+request.getCodigoArticulo()+"'", "");
			while(rs.next()){
				ar.setCodigo(rs.getString("CODIGO_LIBRO"));
				ar.setImpresora(rs.getString("IMPRESOR_PARA_COMANDA"));
				ar.setPrecio(rs.getDouble("PRECIO_DETAL"));
				ar.setReferenciaIva(rs.getDouble("REFENCIACONIVA"));
				ar.setTitulo(rs.getString("TITULO"));
				log.debug(rs.getString("CODIGO_LIBRO"));
				log.debug(rs.getString("IMPRESOR_PARA_COMANDA"));
				log.debug(rs.getDouble("PRECIO_DETAL"));
				log.debug(rs.getDouble("REFENCIACONIVA"));
				log.debug(rs.getString("TITULO"));
			}
			
			if(ar.getCodigo().equals(null)){
				handlerError.handlerError(response, ERROR_NOTINSERT_ARTS);
			}
			
				String tabla = "ITEMSFACTURASABIERTAS";
				String condicion = "ITEMNO="+request.getPosicion()+ " AND ORDERNO="+request.getMesa();
				boolean resultado = fr.delete(tabla,  condicion);
				double precio=0;
				precio=ar.getPrecio()+ar.getReferenciaIva();
				log.debug("precio"+ar.getPrecio());
				log.debug("precio mas impuesto"+precio);
				
				if (resultado) {
					handlerError.handlerError(response, ERROR_SUSSES);
				}else {
				handlerError.handlerError(response, ERROR_NOTINSERT_ARTS);
			}
				//actualizar Total

				
				boolean resUpa=fr.update("FACTURASABIERTAS", " AMOUNTPAID=AMOUNTPAID-"+precio+",TAXTOTAL=TAXTOTAL-"+precio, " ORDERNO="+request.getMesa());
				
					if (resUpa) {
						handlerError.handlerError(response, ERROR_SUSSES);
					}else {
					handlerError.handlerError(response, ERROR_NOTINSERT_ARTS);
				}

			} catch (Exception e) {
				handlerError.handlerError(response, ERROR_GENERAL);
				log.error(e.getMessage());
				log.error(e.getCause());
				e.printStackTrace();

			}
			/*
			 * 
			 * FIN DEL SERVICCIO LOGICA
			 */
		} else {
			handlerError.handlerError(response, ERROR_AUT);
			log.error(pre_error.getPropertie(ERROR_AUT));
		}
		return response;
	}

	@Override
	public EntregarArticuloResponse entregarArticuloComanda(EntregarArticuloComandaRequest request) {
		// TODO Auto-generated method stub
		log.debug("Inicio de servicio: incluirArticuloComanda");
		EntregarArticuloResponse  response = new EntregarArticuloResponse();

		this.sec = new Security();
		if (sec.autenticateUser(request.getUser(), request.getPassword())) {
			try {
			log.debug("Autenticacion Realizada con exito");
			/*
			 * Comienzo del servicio
			 * 
			 */
			
			boolean resUp=fr.update("ITEMSFACTURASABIERTAS", " ENTREGADO='SI'" ,"ORDERNO="+request.getMesa());
					if (resUp) {
						handlerError.handlerError(response, ERROR_SUSSES);
					}else {
					handlerError.handlerError(response, ERROR_NOTINSERT_ARTS);
				}

			} catch (Exception e) {
				handlerError.handlerError(response, ERROR_GENERAL);
				log.error(e.getMessage());
				log.error(e.getCause());
				e.printStackTrace();
				handlerError.handlerError(response, ERROR_NOTINSERT_ARTS);
			}
			/*
			 * 
			 * FIN DEL SERVICCIO LOGICA
			 */
		} else {
			handlerError.handlerError(response, ERROR_AUT);
			log.error(pre_error.getPropertie(ERROR_AUT));
		}
		return response;
	}

	@Override
	public EnviarArticuloResponse enviarArticuloComanda(EnviarArticuloComandaRequest request) {
		// TODO Auto-generated method stub
		log.debug("Inicio de servicio: enviarArticuloComanda");
		EnviarArticuloResponse  response = new EnviarArticuloResponse();

		this.sec = new Security();
		if (sec.autenticateUser(request.getUser(), request.getPassword())) {
			try {
			log.debug("Autenticacion Realizada con exito");
			/*
			 * Comienzo del servicio
			 * 
			 */
			//En este punto debo realizar la impresion de la comanda
			boolean resUp=fr.update("ITEMSFACTURASABIERTAS", " COMANDA_ENVIADA='SI'" ,"ORDERNO="+request.getMesa());
					if (resUp) {
						handlerError.handlerError(response, ERROR_SUSSES);
					}else {
					handlerError.handlerError(response, ERROR_NOTINSERT_ARTS);
				}

			} catch (Exception e) {
				handlerError.handlerError(response, ERROR_GENERAL);
				log.error(e.getMessage());
				log.error(e.getCause());
				e.printStackTrace();
				handlerError.handlerError(response, ERROR_NOTINSERT_ARTS);
			}
			/*
			 * 
			 * FIN DEL SERVICCIO LOGICA
			 */
		} else {
			handlerError.handlerError(response, ERROR_AUT);
			log.error(pre_error.getPropertie(ERROR_AUT));
		}
		return response;
		}

	@Override
	public printerResponse printEstadoCta(printerRequest request) {
		// TODO Auto-generated method stub
		log.debug("Inicio de servicio: enviarArticuloComanda");
		printerResponse  response = new printerResponse();

		this.sec = new Security();
		if (sec.autenticateUser(request.getUser(), request.getPassword())) {
			try {
			log.debug("Autenticacion Realizada con exito");
			/*
			 * Comienzo del servicio
			 * 
			 */
			
			boolean resUp=fr.update("ITEMSFACTURASABIERTAS", " COMANDA_ENVIADA='SI'" ,"ORDERNO="+request.getUser());
					if (resUp) {
						handlerError.handlerError(response, ERROR_SUSSES);
					}else {
					handlerError.handlerError(response, ERROR_NOTINSERT_ARTS);
				}

			} catch (Exception e) {
				handlerError.handlerError(response, ERROR_GENERAL);
				log.error(e.getMessage());
				log.error(e.getCause());
				e.printStackTrace();
				handlerError.handlerError(response, ERROR_NOTINSERT_ARTS);
			}
			/*
			 * 
			 * FIN DEL SERVICCIO LOGICA
			 */
		} else {
			handlerError.handlerError(response, ERROR_AUT);
			log.error(pre_error.getPropertie(ERROR_AUT));
		}
		return response;
		}

	
}
>>>>>>> master:src/ve/com/hpsi/MiddlewareImpl.java
