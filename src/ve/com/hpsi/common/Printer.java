package ve.com.hpsi.common;

import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.Attribute;
import javax.print.attribute.AttributeSet;
import javax.print.attribute.HashAttributeSet;
import javax.print.attribute.PrintServiceAttributeSet;
import javax.print.attribute.standard.Destination;
import javax.print.attribute.standard.PrinterInfo;
import javax.print.attribute.standard.PrinterIsAcceptingJobs;
import javax.print.attribute.standard.PrinterLocation;
import javax.print.attribute.standard.PrinterMakeAndModel;
import javax.print.attribute.standard.PrinterName;
import javax.print.attribute.standard.PrinterState;

/**
 * Ejemplos para ver tus impresoras
 * 
 * @author Peiretti
 */
public class Printer {

	/**
	 * @param args
	 */

	public static void printAvailable() {

		// busca los servicios de impresion...
		PrintService[] services = PrintServiceLookup.lookupPrintServices(null, null);

		// -- ver los atributos de las impresoras...
		for (PrintService printService : services) {

			System.out.println(" ---- IMPRESORA: " + printService.getName());

			PrintServiceAttributeSet printServiceAttributeSet = printService.getAttributes();

			System.out.println("--- atributos");

			// todos los atributos de la impresora
			Attribute[] a = printServiceAttributeSet.toArray();
			for (Attribute unAtribute : a) {
				System.out.println("atributo: " + unAtribute.getName());
			}

			System.out.println("--- viendo valores especificos de los atributos ");

			// valor especifico de un determinado atributo de la impresora
			System.out.println("PrinterLocation: " + printServiceAttributeSet.get(PrinterLocation.class));
			System.out.println("PrinterInfo: " + printServiceAttributeSet.get(PrinterInfo.class));
			System.out.println("PrinterState: " + printServiceAttributeSet.get(PrinterState.class));
			System.out.println("Destination: " + printServiceAttributeSet.get(Destination.class));
			System.out.println("PrinterMakeAndModel: " + printServiceAttributeSet.get(PrinterMakeAndModel.class));
			System.out.println("PrinterIsAcceptingJobs: " + printServiceAttributeSet.get(PrinterIsAcceptingJobs.class));

		}

	}

	public static void printDefault() {

		// tu impresora por default
		PrintService service = PrintServiceLookup.lookupDefaultPrintService();
		System.out.println("Tu impresora por default es: " + service.getName());

	}

	public static void printByName(String printName) {

		PrintService[] services = PrintServiceLookup.lookupPrintServices(null, null);

		AttributeSet aset = new HashAttributeSet();
		aset.add(new PrinterName(printName, null));
		// aset.add(ColorSupported.SUPPORTED); // si quisieras buscar ademas las
		// que soporten color

		services = PrintServiceLookup.lookupPrintServices(null, aset);
		if (services.length == 0) {
			System.out.println("No se encontro impresora con nombre " + printName);
		}
		for (PrintService printService : services) {
			System.out.println(printService.getName());
		}
	}

	public boolean imprimir(String Texto,String nombreImpresora){
    	boolean flag=false;

    	try {
    	AttributeSet aset = new HashAttributeSet();
        aset.add(new PrinterName(nombreImpresora, null));
        PrintService[] services = PrintServiceLookup.lookupPrintServices(null, aset);
        
        if (services.length == 0) {
			System.out.println("No se encontro impresora con nombre " + nombreImpresora);
			throw (new PrintException());
		}
        
        
        for (PrintService service : services) {
        	System.out.println(service.getName());
    	DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
    	DocPrintJob pj = service.createPrintJob();
    	String ss=Texto;
    	byte[] bytes;
    	
    	bytes=ss.getBytes();
    	
    	 Doc doc=new SimpleDoc(bytes,flavor,null);

    	  pj.print(doc, null);}
    	}
    	catch (PrintException e) {
    	  flag=false;
    	
        }
    	return flag;
    	
    }
}