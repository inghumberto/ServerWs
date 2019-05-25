package ve.com.hpsi.impl.endpoint;

import java.io.IOException;

import javax.xml.ws.Endpoint;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import ve.com.hpsi.common.Preference;
import ve.com.hpsi.impl.MiddlewareFinancImpl;
import ve.com.hpsi.impl.MiddlewareImpl;

public class CapaWS {
	private final static Logger log = Logger.getLogger(CapaWS.class);

	public static void main(String[] args)  throws Exception{
		try {
			
			//carga de preferencias
			Preference pre = new Preference(System.getProperty("user.dir"), "\\security.properties");
			/*
			 * 
			 * Genreando End Points
			 */
			String log4jConfPath = System.getProperty("user.dir") + "\\log4j2.properties";
			PropertyConfigurator.configure(log4jConfPath);
			log.info("Servicio Endpoint:" + pre.getPropertie("ws.endpoint_base") + ":"
					+ pre.getPropertie("ws.endpoint_port") + "/WS/MiddlewareImpl");
			Endpoint.publish(pre.getPropertie("ws.endpoint_base") + ":" + pre.getPropertie("ws.endpoint_port")
					+ "/WS/MiddlewareImpl", new MiddlewareImpl());
			log.debug("Servicio Comanda Arriba con la clase...");
			
			
			/*
			 * 
			 * Genreando End Points otro service
			 */
			log.info("Servicio Endpoint:" + pre.getPropertie("ws.endpoint_base") + ":"
					+ pre.getPropertie("ws.endpoint_port2") + "/WS/MiddlewareFinancImpl");
			Endpoint.publish(pre.getPropertie("ws.endpoint_base") + ":" + pre.getPropertie("ws.endpoint_port2")
					+ "/WS/MiddlewareFinancImpl", new MiddlewareFinancImpl());
			log.debug("Servicio Finance Arriba...");
			
			System.out.println("Press Any Key To Continue...");
			new java.util.Scanner(System.in).nextLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			log.error(e.getMessage());
		} catch (Exception e) {
			log.fatal("Error Para levantar servicios Fatal");
			log.fatal(e.getMessage());
			e.printStackTrace();
			log.fatal(e);
		}
	}

}
