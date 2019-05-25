package ve.com.hpsi.impl;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.jws.WebService;

import org.apache.log4j.Logger;

import ve.com.hpsi.MiddlewareFinanc;
import ve.com.hpsi.common.FireBirdBD;
import ve.com.hpsi.common.HandlerComunication;
import ve.com.hpsi.common.Preference;
import ve.com.hpsi.common.Security;
import ve.com.hpsi.wsto.LoginServicioRequest;
import ve.com.hpsi.wsto.LoginServicioResponse;

@WebService(endpointInterface = "ve.com.hpsi.MiddlewareFinanc")
public class MiddlewareFinancImpl implements MiddlewareFinanc {
	private FireBirdBD fr = new FireBirdBD();
	private Security sec;
	private Preference pre_error;
	HandlerComunication handlerError;
	private final static Logger log = Logger.getLogger(MiddlewareFinancImpl.class);

	public MiddlewareFinancImpl() {
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

	@Override
	public LoginServicioResponse logingUser(LoginServicioRequest request) {
		// // TODO Auto-generated method stub
		log.debug("Inicio de servicio:loginServicio");
		LoginServicioResponse response = new LoginServicioResponse();

		if (sec.autenticateUser(request.getUser(), request.getPassword())) {
			log.debug("Autenticacion Realizada con exito");
			/*
			 * Comienzo del servicio
			 * 
			 */

			try {
				String campos = "*";
				String tabla = "claves";
				String condicion = " where  LOGIN='" + request.getUserApp() + "' and PASSWORDNO='"
						+ request.getPassApp() + "'";
				String orden = "";
				ResultSet rs = fr.select(campos, tabla, condicion, orden);
				String passwd = "";
				String person = null;
				int codigo = 0;
				while (rs.next()) {
					log.debug(rs.getString("LOGIN").toString());
					person = rs.getString("LOGIN").toString();
					codigo = Integer.parseInt(rs.getString("CODIGO_VENDEDOR").toString());
				}
				if (!person.equals("")) {
					response = (LoginServicioResponse) handlerError.handlerError(response, ERROR_SUSSES);
					response.setNombreUser(person);
					response.setCodigoUser(codigo+"");
				} else {
					handlerError.handlerError(response, ERROR_NOTFOUND);
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

}
