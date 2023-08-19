/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package biblioteca;

import dominio.Afiliado;
import dominio.Prestamo;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.PDPageContentStream;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author emiliano
 */
public class Reporte {
	public static void generarReporte(String rutaArchivo, ArrayList<Afiliado> afiliados) {
		try {
			// Crea un nuevo documento PDF
			PDDocument document = new PDDocument();
			PDPage page = new PDPage(PDRectangle.A4);
			document.addPage(page);
			rutaArchivo += "/report.pdf";

			// Crea el contenido del reporte
			PDPageContentStream contentStream = new PDPageContentStream(document, page);
			contentStream.beginText();
			contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
			contentStream.newLineAtOffset(50, 800);
			contentStream.showText("Afiliados de cada biblioteca de la Universidad Nacional de Cuyo");
			contentStream.endText();

			// Agrega más contenido aquí, como texto, imágenes, tablas, etc.
			// Tamaño de las celdas de la tabla
			float margin = 50;
			float yStart = 750;
			float tableWidth = page.getMediaBox().getWidth() - 2 * margin;
			float tableHeight = 100f;
			float rowHeight = 20f;
			int numberOfColumns = 4;
			float[] columnWidths = new float[]{tableWidth / (float) numberOfColumns, tableWidth / (float) numberOfColumns, tableWidth / (float) numberOfColumns, tableWidth / (float) numberOfColumns};
				
			
			// hardcodeo de la cantidad de bibliotecas xd
			int bib = 5;
			ArrayList<ArrayList> afiliadosPorBib = segmentBibs(afiliados, bib);
			
			float yPosition = yStart;
			ArrayList<Afiliado> segmentado;
			for (int i=1; i<bib; i++) {
				segmentado = afiliadosPorBib.get(i);
				
				contentStream.beginText();
				contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
				contentStream.newLineAtOffset(50, yPosition);
				yPosition -= rowHeight;
				contentStream.showText(segmentado.get(0).getBiblioteca().getNombre() + " - " + segmentado.get(0).getBiblioteca().getDependencia());
				contentStream.endText();
			
				
				for (String[] row : toListOfStringVector(segmentado, numberOfColumns)) {
					drawTableRow(contentStream, yPosition, tableWidth, rowHeight, row, columnWidths);
					yPosition -= rowHeight;
				}
			}
			
// Dibuja la tabla y rellena los registros de SQL
			/*float yPosition = yStart;
			for (String[] row : toListOfStringVector(afiliados, numberOfColumns)) {
				drawTableRow(contentStream, yPosition, tableWidth, rowHeight, row, columnWidths);
				yPosition -= rowHeight;
			}*/
		
			contentStream.close();

			// Guarda el documento como archivo PDF
			document.save(rutaArchivo);

			// Cierra el documento
			document.close();

			System.out.println("Reporte generado exitosamente.");
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static List<String[]> toListOfStringVector(ArrayList<Afiliado> afiliados, int columns) {
		ArrayList<String[]> lista = new ArrayList();
		String[] tmp;
		Afiliado afiliado;
		for (int i=0; i<afiliados.size(); i++) {
			afiliado = afiliados.get(i);
			tmp = new String[columns];
			tmp[0] = String.valueOf(afiliado.getLegajo());
			tmp[1] = afiliado.getApellido();
			tmp[2] = afiliado.getNombre();
			tmp[3] = afiliado.getFechaRegistro().toString();
			lista.add(tmp);
		}
		return lista;
	}

	// Método para dibujar una fila en la tabla
	private static void drawTableRow(PDPageContentStream contentStream, float y, float tableWidth, float rowHeight, String[] rowData, float[] columnWidths) throws IOException {
		float x = 50;
		for (int i = 0; i < rowData.length; i++) {
			contentStream.setFont(PDType1Font.HELVETICA, 12);
			contentStream.beginText();
			contentStream.newLineAtOffset(x, y);
			contentStream.showText(rowData[i]);
			contentStream.endText();
			x += columnWidths[i];
		}
	}

	private static ArrayList<ArrayList> segmentBibs(ArrayList<Afiliado> afiliados, int cantidadBibliotecas) {
		ArrayList<ArrayList> biblios = new ArrayList();
		for (int i=0; i<cantidadBibliotecas; i++) {
			biblios.add(new ArrayList<Afiliado>());
		}
		Afiliado afiliado;
		for (int i=0; i<afiliados.size(); i++) {
			afiliado = afiliados.get(i);
			biblios.get(afiliado.getIdBibliotecaAfiliacion()).add(afiliado);
		}
		return biblios;
	}
}
