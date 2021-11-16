package tienda.alicia.v01.util;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.collection.PdfTargetDictionary;
import com.itextpdf.text.pdf.events.IndexEvents.Entry;

import tienda.alicia.v01.model.DetallePedido;
import tienda.alicia.v01.model.Pedido;
import tienda.alicia.v01.model.Producto;

public class EscribirPDF {

	/**
	 * Escribir pdf
	 * 
	 * @param args
	 */
	public void escribir(Pedido pedido, HashMap<Integer, String> listaDeProductos, List<DetallePedido> listaDetalle) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		PdfWriter writer = null;
		Document documento = new Document(PageSize.A4, 20, 20, 70, 50);

		try {
			// Obtenemos la instancia del archivo a utilizar
			writer = PdfWriter.getInstance(documento, new FileOutputStream("./ficheros/salida.pdf"));

			// Para insertar cabeceras/pies en todas las p�ginas
			writer.setPageEvent(new PDFHeaderFooter());

			// Abrimos el documento para edici�n
			documento.open();

			// PARRAFOS
			Paragraph paragraph = new Paragraph();
			// String contenido = "esto es un p�rrafo";
			// paragraph.setSpacingBefore(100);
			paragraph.add("\n\n");
			// String font = "Sans";
			// float tamanno = 11;
			// int style = Font.BOLD;
			// BaseColor color = BaseColor.BLACK;
			// float spacBefore = 0;
			// float spacAfter = 5;

			// paragraph.setAlignment(Element.ALIGN_CENTER);
			// paragraph.setFont(new Font(FontFactory.getFont(font, tamanno, style,
			// color)));
			// paragraph.add("esto es una p�rrafo");
			// paragraph.setSpacingBefore(spacBefore);
			// paragraph.setSpacingAfter(spacAfter);

			documento.add(paragraph);

			// TABLAS

			// Instanciamos una tabla de X columnas
			PdfPTable tabla = new PdfPTable(3);
			PdfPTable tablaProducto = new PdfPTable(1);
			PdfPTable tablaDetalle = new PdfPTable(2);

			// Ancho de cada columna
			// int[] values = new int[]{40,40,40,40};
			// tabla.setWidths(values);
			// tabla.setWidthPercentage(new Float(100));

			// Phrase phrase = new Phrase("contenido de la celda",
			// new Font(FontFactory.getFont("Sans", 9, Font.BOLD, BaseColor.BLACK)));
			// Phrase phrase = new Phrase("contenido de la celda");
			Phrase texto = new Phrase("cabecera");
			Phrase cabeceraFechaTexto = new Phrase("Fecha");
			Phrase cabeceraPagoTexto = new Phrase("Metodo de pago");
			Phrase cabeceraTotalTexto = new Phrase("Total");
			Phrase cabeceraProductoTexto = new Phrase("Producto");
			Phrase cabeceraCantidadTexto = new Phrase("Cantidad");
			Phrase cabeceraPrecioTexto = new Phrase("Precio");

			PdfPCell cabecera = new PdfPCell(texto);
			PdfPCell cabeceraFecha = new PdfPCell(cabeceraFechaTexto);
			PdfPCell cabeceraMetodo = new PdfPCell(cabeceraPagoTexto);
			PdfPCell cabeceraTotal = new PdfPCell(cabeceraTotalTexto);
			PdfPCell cabeceraProducto = new PdfPCell(cabeceraProductoTexto);
			PdfPCell cabeceraCantidad = new PdfPCell(cabeceraCantidadTexto);
			PdfPCell cabeceraPrecio = new PdfPCell(cabeceraPrecioTexto);

			// PdfPCell cabecera2 = new PdfPCell(texto);
			// Propiedades concretas de formato
			cabeceraFecha.setBackgroundColor(BaseColor.LIGHT_GRAY);
			cabeceraFecha.setBorderWidth(1);
			cabeceraMetodo.setBackgroundColor(BaseColor.LIGHT_GRAY);
			cabeceraMetodo.setBorderWidth(1);
			cabeceraTotal.setBackgroundColor(BaseColor.LIGHT_GRAY);
			cabeceraTotal.setBorderWidth(1);
			cabeceraProducto.setBackgroundColor(BaseColor.LIGHT_GRAY);
			cabeceraProducto.setBorderWidth(1);
			cabeceraCantidad.setBackgroundColor(BaseColor.LIGHT_GRAY);
			cabeceraCantidad.setBorderWidth(1);
			cabeceraPrecio.setBackgroundColor(BaseColor.LIGHT_GRAY);
			cabeceraPrecio.setBorderWidth(1);
			// celda.setBorderColor(BaseColor.WHITE);
			// cabecera.setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
			// celda.setVerticalAlignment(PdfPCell.ALIGN_TOP);
			// celda.setPaddingBottom(3);

			// Tabla1
			tabla.addCell(cabeceraFecha);
			tabla.addCell(cabeceraMetodo);
			tabla.addCell(cabeceraTotal);

			tabla.addCell(pedido.getFecha().toString());
			tabla.addCell(pedido.getMetodo_pago());
			tabla.addCell(String.valueOf(pedido.getTotal()));

			// Tabla 2

			tablaProducto.addCell(cabeceraProducto);
		
			for(String key : listaDeProductos.values()) {
				tablaProducto.addCell(key.toString());
				
	    	}
			
			// Tabla 3
			//tablaDetalle.addCell(cabeceraProducto);
			tablaDetalle.addCell(cabeceraCantidad);
			tablaDetalle.addCell(cabeceraPrecio);
			
			for (DetallePedido detalle : listaDetalle) {

				tablaDetalle.addCell(String.valueOf(detalle.getUnidades()));
				tablaDetalle.addCell(String.valueOf(detalle.getPrecio_unidad()));

			}
			

			// documento.add(tabla);

			/*
			 * PdfPTable tabla = new PdfPTable(4); //tabla.addCell(cabecera);
			 * //tabla.addCell(cabecera); //tabla.addCell(cabecera);
			 * 
			 * //tabla.addCell(celda);
			 * 
			 * tabla.addCell(new PdfPCell(new Phrase("1"))); tabla.addCell(new PdfPCell(new
			 * Phrase("2"))); tabla.addCell(new PdfPCell(new Phrase("3")));
			 * tabla.addCell(new PdfPCell(new Phrase("4")));
			 * 
			 * //tabla.addCell(new PdfPCell(new Phrase("contenido de la celda")));
			 * //tabla.addCell(celda); //tabla.addCell(celda); //tabla.completeRow();
			 * //tabla.addCell(celda);
			 */
			documento.add(tabla);
			documento.add(new Paragraph(" "));
			documento.add(tablaProducto);
			documento.add(tablaDetalle);

			// documento.add(new Paragraph(" "));

			documento.close(); // Cerramos el documento
			writer.close(); // Cerramos writer

			// Abrir autom�ticamente el fichero creado
			// http://docs.oracle.com/javase/6/docs/api/java/awt/Desktop.html

			try {
				File path = new File("./ficheros/salida.pdf");
				Desktop.getDesktop().open(path);
			} catch (IOException ex) {
				ex.printStackTrace();
			}

		} catch (Exception ex) {
			ex.getMessage();
		}
	}

}