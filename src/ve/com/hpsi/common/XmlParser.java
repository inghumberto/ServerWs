package ve.com.hpsi.common;


import com.thoughtworks.xstream.XStream;

public class XmlParser{
 static XStream st=new XStream();
	
	public static String toString(Object obj){
		String xml=null;
		try{
			xml=toXml(obj);
		}catch(Exception e){
			xml="Problemas para Convertir Objeto  en XML";
		}
		return xml;
	}


	private static String toXml(Object obj) {
		return st.toXML(obj);
	}
}
