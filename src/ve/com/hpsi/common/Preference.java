package ve.com.hpsi.common;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.util.Properties;

import org.apache.log4j.Logger;

public class Preference extends Properties {
     String        result = "";
     String        propFileName;
     String        pathProp;
     static Logger log    = Logger.getLogger(Preference.class);

     public Preference(String path, String name) throws IOException {
          this.pathProp = path;
          this.propFileName = pathProp+"\\" + name;
//          loader();
            FileInputStream inputStream;
          try {
               inputStream = new FileInputStream(propFileName);
               if (inputStream != null) {
                    this.load(inputStream);
               } else {
                    this.log.error("property file '" + propFileName + "' not found in the classpath");
                    throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
               }
          }catch (IOException e) {
                         // TODO Auto-generated catch block
                         e.printStackTrace();
          }
     }
      public Preference( String name) throws IOException {
          this.pathProp = "";
          this.propFileName = pathProp + name;
          FileInputStream inputStream;
          try {
               inputStream = new FileInputStream(propFileName);
               if (inputStream != null) {
                    this.load(inputStream);
               } else {
                    this.log.error("property file '" + propFileName + "' not found in the classpath");
                    throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
               }
          }catch (IOException e) {
                         // TODO Auto-generated catch block
                         e.printStackTrace();
          }

     }

     public void loader() {
          FileInputStream inputStream;
          try {
               inputStream = new FileInputStream(propFileName);
               if (inputStream != null) {
                    this.load(inputStream);
               } else {
                    this.log.error("property file '" + propFileName + "' not found in the classpath");
                    throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
               }
          }catch (IOException e) {
                         // TODO Auto-generated catch block
                         e.printStackTrace();
          }

     }

     public Properties getPropValues() throws IOException {
          // get the property value and print it out
          return this;
     }

     public void insertValueProperties(String clave, String valor) {
//          loader();
          try {
               FileInputStream inputStream;
               inputStream = new FileInputStream(propFileName);
               if (inputStream != null) {
                    this.load(inputStream);
               } else {
                    this.log.error("property file '" + propFileName + "' not found in the classpath");
                    throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
               }
          }catch (IOException e) {
                         // TODO Auto-generated catch block
                         e.printStackTrace();
          }
          this.setProperty(clave, valor);
     }

     public String getPropertie(String key) {
//          loader();
          try {
               FileInputStream inputStream;
               inputStream = new FileInputStream(propFileName);
               if (inputStream != null) {
                    this.load(inputStream);
               } else {
                    this.log.error("property file '" + propFileName + "' not found in the classpath");
                    throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
               }
          }catch (IOException e) {
                         // TODO Auto-generated catch block
                         e.printStackTrace();
          }
          return this.getProperty(key);

     }

     public String getResult() {
          return result;
     }

     public void setResult(String result) {
          this.result = result;
     }

     public String getPropFileName() {
          return propFileName;
     }

     public void setPropFileName(String propFileName) {
          this.propFileName = propFileName;
     }

     public String getPathProp() {
          return pathProp;
     }

     public void setPathProp(String pathProp) {
          this.pathProp = pathProp;
     }

}
