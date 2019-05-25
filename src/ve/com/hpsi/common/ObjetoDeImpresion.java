package ve.com.hpsi.common;

import java.awt.Graphics;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;

public class ObjetoDeImpresion  implements Printable {
	@Override
	public int print(Graphics g, PageFormat f, int pageIndex) {
		if (pageIndex == 0) {
			g.drawString("texto que se imprime", 100, 200);
			return PAGE_EXISTS;
		} else {
			return NO_SUCH_PAGE;
		}
	}

}
