package ve.com.hpsi.common;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

import ve.com.hpsi.impl.endpoint.CapaWS;

public class FireBirdBD {
	private Preference preference;
	private Connection connection = null;
	private ResultSet resultSet = null;
	private Statement statement = null;
	private String db = null;
	private String user = null;
	private String password = null;

	private final static Logger log = Logger.getLogger(FireBirdBD.class);

	// Constructor de la clase que se conecta a la base de datos una vez que se
	// crea la instancia
	public FireBirdBD() {
		try {
			preference=new Preference(System.getProperty("user.dir"),"security.properties");

			log.debug("Load Properties"+preference.getPropFileName());
			log.debug("Path:"+preference.getProperty("database.path")+preference.getProperty("database.database"));
			this.db=preference.getProperty("database.path")+preference.getProperty("database.database");
			this.user=preference.getProperty("database.user");
			this.password=preference.getProperty("database.password");
			Class.forName(preference.getProperty("database.driver"));
			log.debug("-> [ " + this.db + "]");
		
			connection = DriverManager.getConnection("jdbc:firebirdsql://localhost/" + this.db, this.user, this.password);
			log.info("Conectado a la base de datos [ " + this.db + "]");
		} catch (Exception e) {
			log.error("Error al conectar con Base de datos "+this.db);
			log.error(e.getMessage());
			e.printStackTrace();
			
		}
	}

	// ___________________________________________________________________________________
	// Soy una barra separadora :)
	/*
	 * METODO PARA INSERTAR UN REGISTRO EN LA BASE DE DATOS INPUT: table =
	 * Nombre de la tabla fields = String con los nombres de los campos donde
	 * insertar Ej.: campo1,campo2campo_n values = String con los datos de los
	 * campos a insertar Ej.: valor1, valor2, valor_n OUTPUT: Boolean
	 */
	public boolean insert(String table, String fields, String values) {
		boolean res = false;
		// Se arma la consulta
		String q = " INSERT INTO " + table + " ( " + fields + " ) VALUES ( " + values + " ) ";
		log.debug(q);
		// se ejecuta la consulta
		try {
			PreparedStatement pstm = connection.prepareStatement(q);
			pstm.execute();
			pstm.close();
			res = true;
		} catch (Exception e) {
			log.error("Error en Insert"+e.getMessage());
		}
		return res;
	}

	// ___________________________________________________________________________________
	// Soy una barra separadora :)
	/*
	 * METODO PARA REALIZAR UNA CONSULTA A LA BASE DE DATOS INPUT:
	 * 
	 * OUTPUT: String con los datos concatenados
	 */
	public ResultSet select(String campos,String tablas,String condicion,String orden) {
		try {
			statement = connection.createStatement();
			String sql="SELECT "+campos+" FROM "+tablas+" "+condicion+" "+orden;
			log.debug("Sentencia SQL"+sql);
			resultSet = statement.executeQuery(sql);
		
		} catch (SQLException ex) {
			log.error("Error en Insert"+ex.getMessage());
		}
		return resultSet;
	}

	// ___________________________________________________________________________________
	// Soy una barra separadora :)
	public void desconectar() {
		try {
			resultSet.close();
			statement.close();
			connection.close();
			log.debug("Desconectado de la base de datos [ " + this.db + "]");
		} catch (SQLException ex) {
			log.error(ex.getMessage());
			log.error(ex);
		}
	}
	// ___________________________________________________________________________________
	// Soy una barra separadora :)
	
	
	// ___________________________________________________________________________________
			// Soy una barra separadora :)
			/*
			 * METODO PARA UPDATE UN REGISTRO EN LA BASE DE DATOS INPUT: table =
			 * Nombre de la tabla fields = String con los nombres de los campos donde
			 * insertar Ej.: campo1,campo2campo_n values = String con los datos de los
			 * campos a insertar Ej.: valor1, valor2, valor_n OUTPUT: Boolean
			 */
			public boolean update(String table, String fields, String condicion) {
				boolean res = false;
				// Se arma la consulta
				String q = " UPDATE " + table + " SET "+fields+" WHERE "+condicion+"";
				log.debug(q);
				// se ejecuta la consulta
				try {
					PreparedStatement pstm = connection.prepareStatement(q);
					pstm.execute();
					pstm.close();
					res = true;
				} catch (Exception e) {
					log.error("Error en Update"+e.getMessage());
				}
				return res;
			}
			
			// ___________________________________________________________________________________
			// Soy una barra separadora :)
			/*
			 * METODO PARA delete UN REGISTRO EN LA BASE DE DATOS INPUT: table =
			 * Nombre de la tabla fields = String con los nombres de los campos donde
			 * insertar Ej.: campo1,campo2campo_n values = String con los datos de los
			 * campos a insertar Ej.: valor1, valor2, valor_n OUTPUT: Boolean
			 */
			public boolean delete(String table,  String condicion) {
				boolean res = false;
				// Se arma la consulta
				String q = " DELETE FROM " + table + " WHERE "+condicion+"";
				log.debug(q);
				// se ejecuta la consulta
				try {
					PreparedStatement pstm = connection.prepareStatement(q);
					pstm.execute();
					pstm.close();
					res = true;
				} catch (Exception e) {
					log.error("Error en Delete"+e.getMessage());
				}
				return res;
			}

}
