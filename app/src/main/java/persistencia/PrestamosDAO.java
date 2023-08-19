/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia;

import dominio.Afiliado;
import dominio.Autor;
import dominio.Biblioteca;
import dominio.Editorial;
import dominio.Ejemplar;
import dominio.Libro;
import dominio.Prestamo;
import java.util.ArrayList;

/**
 *
 * @author emiliano
 */
public class PrestamosDAO extends DAO {
	
	public void insertPrestamo(Prestamo prestamo) throws Exception {
		try {
            if (prestamo == null){
                throw new Exception("Null object");
            }
            
            connectDB();
            updateDB("START TRANSACTION;");
			
			StringBuilder query = new StringBuilder("""
                                           INSERT IGNORE INTO prestamos(legajo, id_biblioteca_afiliado,
																		id_ejemplar, `from`, `to`, devuelto)
                                           VALUES 
                                           """);
			query.append("('").append(prestamo.getLegajo()).append("','");
			query.append(prestamo.getIdBibliotecaAfiliacion()).append("','");
			query.append(prestamo.getIdEjemplar()).append("','");
			query.append(prestamo.getFrom().toString()).append("','");
			query.append(prestamo.getTo().toString()).append("','");
			query.append(prestamo.getDevuelto()).append("');");
			
			updateDB(query.toString());
			
            updateDB("COMMIT;");
        }
        catch (Exception e) {
            updateDB("ROLLBACK;");
            throw e;
        }
        finally {
            disconnectDB();
        }
	}
	
	public void updatePrestamo(Prestamo prestamo) throws Exception {
        
        try {
            if (prestamo == null) {
                throw new Exception("Null object");
            }
            
            connectDB();
            updateDB("START TRANSACTION;");
            
			StringBuilder query = new StringBuilder();
			
            query.append("""
                         UPDATE prestamos
                         SET 
                         """);
            query.append("prestamos.to='").append(prestamo.getTo().toString()).append("' ");
			query.append("WHERE legajo=").append(prestamo.getLegajo()).append(" AND ");
			query.append("id_biblioteca_afiliado=").append(prestamo.getIdBibliotecaAfiliacion()).append((";"));
			
            updateDB(query.toString());
            
            updateDB("COMMIT;");
        }
        catch (Exception e) {
            updateDB("ROLLBACK;");
            throw e;
        }
        finally {
            disconnectDB();
        }
    }
	
	public void devolucionPrestamo(Prestamo prestamo) throws Exception {
		try {
			if (prestamo == null) {
				throw new Exception("Null object");
			}
			
			connectDB();
			updateDB("START TRANSACTION;");
			
			StringBuilder query = new StringBuilder("""
                         UPDATE prestamos
                         SET 
                         """);
            query.append("prestamos.to=CURRENT_DATE(), devuelto='SI'");
			query.append("WHERE legajo=").append(prestamo.getLegajo()).append(" AND ");
			query.append("id_biblioteca_afiliado=").append(prestamo.getIdBibliotecaAfiliacion()).append((";"));
			
			updateDB(query.toString());
			updateDB("COMMIT;");
		}
		catch (Exception e) {
			updateDB("ROLLBACK;");
			throw e;
		}
		finally {
			disconnectDB();
		}
	}
	
	private ArrayList<Prestamo> executeSearch(String sql) throws Exception{
        try{
			connectDB();
			
            requestDB(sql);
            ArrayList<Prestamo> prestamos = new ArrayList();
			
            if(rs == null){
                throw new Exception("No results found");
            }
			
            while(rs.next()){
				prestamos.add(new Prestamo(rs.getInt("legajo"),
											rs.getInt("id_biblioteca_afiliado"),
											rs.getInt("id_ejemplar"),
											rs.getDate("from"),
											rs.getDate("to"),
											rs.getString("devuelto"),
											new Ejemplar(rs.getInt("id_ejemplar"),
														rs.getString("ISBN"),
														rs.getInt("numero"),
														rs.getInt("id_biblioteca_ejemplar"),
														rs.getString("id_ubicacion"),
														new Biblioteca(rs.getString("nombre_biblioteca_ejemplar"),
																		rs.getString("dependencia_ejemplar")),
														new Libro(rs.getString("ISBN"),
																	rs.getString("titulo"))
													),
											new Afiliado(rs.getInt("legajo"),
														rs.getInt("id_biblioteca_afiliado"),
														rs.getString("nombre_afiliado"),
														rs.getString("apellido_afiliado")
														)
				));
            }
			
            return prestamos;
        }
		catch(Exception e){
            throw e;
        }
		finally {
			disconnectDB();
		}
    }
	
	public ArrayList<Prestamo> searchPrestamo(String pattern, String column) throws Exception {
		try {
			if (pattern == null || column == null) {
				throw new Exception("Null object");
			}
			
			StringBuilder query;
			
			if (column.equals("ISBN")){
				query = new StringBuilder ("""
						WITH filtro_pattern AS ( SELECT ISBN, titulo
						FROM libros
						WHERE ISBN 
						""");
				query.append("REGEXP '").append(pattern).append("'), ");
				// obtenemos ejemplares con ISBN
				query.append("""
                 libros_ejemplares_bibl AS (
                 SELECT filtro_pattern.*,
						ejemplares.id AS id_ejemplar, ejemplares.numero, ejemplares.id_ubicacion, ejemplares.id_biblioteca AS id_biblioteca_ejemplar,
                        bibliotecas.nombre AS nombre_biblioteca_ejemplar, bibliotecas.dependencia AS dependencia_ejemplar
                 FROM filtro_pattern INNER JOIN ejemplares
                                       ON filtro_pattern.ISBN=ejemplares.ISBN
                                     INNER JOIN bibliotecas
                                       ON ejemplares.id=bibliotecas.id),
                 """);
				// obtenemos prestamos de esos ejemplares y filtramos por no devueltos o en fecha de prestamo
				query.append("""
                 lib_ej_prestamos AS (
                 SELECT libros_ejemplares_bibl.*,
                        prestamos.legajo, prestamos.from, prestamos.to, prestamos.devuelto, prestamos.id_biblioteca_afiliado
                 FROM libros_ejemplares_bibl INNER JOIN prestamos
                                                  ON libros_ejemplares_bibl.id_ejemplar=prestamos.id_ejemplar
                 WHERE prestamos.devuelto='NO' OR prestamos.to>CURRENT_DATE)
                 """);
				// filtramos afiliados y bibliotecas de afiliacino
				query.append("""
                 SELECT lib_ej_prestamos.*,
						afiliados.nombre AS nombre_afiliado, afiliados.apellido AS apellido_afiliado,
                        bibliotecas.nombre AS nombre_biblioteca_afiliado, bibliotecas.dependencia AS dependencia_afiliado
                 FROM lib_ej_prestamos INNER JOIN afiliados
										ON lib_ej_prestamos.legajo=afiliados.legajo
                                          AND lib_ej_prestamos.id_biblioteca_afiliado=afiliados.id_biblioteca_afiliacion
                                       INNER JOIN bibliotecas 
										ON lib_ej_prestamos.id_biblioteca_afiliado=bibliotecas.id
                 ORDER BY "from";                           
                 """);
			}
			else {
				// filtro por patron --> filtra por no devueltos, 
				query = new StringBuilder ("""
						WITH filtro_pattern AS ( SELECT *
						FROM prestamos
						WHERE 
						""");
				query.append(column).append(" REGEXP '").append(pattern).append("' AND ");
				query.append("(devuelto='NO' OR prestamos.to>CURRENT_DATE)), ");
				// unimos los filtrados del prestamo con ejemplares y biblioteca de los ejemplares
				query.append("""
                 prestamo_ejemplar_bibl AS (
                 SELECT filtro_pattern.*,
						ejemplares.ISBN, ejemplares.numero, ejemplares.id_biblioteca AS id_biblioteca_ejemplar, ejemplares.id_ubicacion,
                        bibliotecas.nombre AS nombre_biblioteca_ejemplar, bibliotecas.dependencia AS dependencia_ejemplar
                 FROM filtro_pattern INNER JOIN ejemplares
                                       ON filtro_pattern.id_ejemplar=ejemplares.id
                                     INNER JOIN bibliotecas
                                       ON ejemplares.id_biblioteca=bibliotecas.id),
                 
                 """);
				// unimos con datos afiliado y nombre de la biblioteca del afiliado
				query.append("""
                 prestamo_afiliado_biblioteca AS (
                 SELECT prestamo_ejemplar_bibl.*,
						bibliotecas.nombre AS nombre_biblioteca_afiliado, bibliotecas.dependencia AS dependencia_afiliado,
                        afiliados.nombre AS nombre_afiliado, afiliados.apellido AS apellido_afiliado
                 FROM prestamo_ejemplar_bibl INNER JOIN bibliotecas
                                                  ON prestamo_ejemplar_bibl.id_biblioteca_afiliado=bibliotecas.id
                                             INNER JOIN afiliados
                                                  ON prestamo_ejemplar_bibl.legajo=afiliados.legajo
													AND prestamo_ejemplar_bibl.id_biblioteca_afiliado=afiliados.id_biblioteca_afiliacion)
                 """);
				query.append("""
                 SELECT prestamo_afiliado_biblioteca.*,
						libros.titulo
                 FROM prestamo_afiliado_biblioteca INNER JOIN libros
                                                     ON prestamo_afiliado_biblioteca.ISBN=libros.ISBN
                 ORDER BY "from";
                 """);
			}
			
			return executeSearch(query.toString());
		}
		catch (Exception e) {
			throw e;
		}
	}
	
	public ArrayList<Prestamo> searchPrestamo(String legajo, Biblioteca biblioteca) throws Exception {
		try{
			connectDB();
			
			StringBuilder query = new StringBuilder();
			// comprobamos si afiliado tiene prestamos vigentes
			query.append("""
                WITH filter_prestamos AS (
                SELECT prestamos.*
                FROM prestamos
                WHERE prestamos.legajo=""");
			query.append(legajo);
			query.append(" AND prestamos.id_biblioteca_afiliado=").append(biblioteca.getIdBiblioteca());
			query.append(" AND (prestamos.devuelto='NO' || prestamos.to>CURRENT_DATE())) ");
			// Obtenemos datos de libro
			query.append("""
                SELECT filter_prestamos.*,
						libros.titulo, libros.ISBN,
                        ejemplares.numero, ejemplares.id_ubicacion,
                        bibliotecas.nombre AS nombre_biblioteca, bibliotecas.dependencia, bibliotecas.id AS id_biblioteca_ejemplar
                FROM filter_prestamos INNER JOIN ejemplares
										ON filter_prestamos.id_ejemplar=ejemplares.id
                                      INNER JOIN bibliotecas
                                        ON ejemplares.id_biblioteca=bibliotecas.id
									  INNER JOIN libros
										ON ejemplares.ISBN=libros.ISBN;
                """);
			
            requestDB(query.toString());
			
            ArrayList<Prestamo> prestamos = new ArrayList();
			
            if(rs == null){
                throw new Exception("No results found");
            }
			
            while(rs.next()){
				prestamos.add(new Prestamo(rs.getInt("legajo"),
											rs.getInt("id_biblioteca_afiliado"),
											rs.getInt("id_ejemplar"),
											rs.getDate("from"),
											rs.getDate("to"),
											rs.getString("devuelto"),
											new Ejemplar(rs.getInt("id_ejemplar"),
														rs.getString("ISBN"),
														rs.getInt("numero"),
														rs.getInt("id_biblioteca_ejemplar"),
														rs.getString("id_ubicacion"),
														new Biblioteca(rs.getString("nombre_biblioteca"),
																		rs.getString("dependencia")),
														new Libro(rs.getString("ISBN"),
																	rs.getString("titulo"))
													)
				));
            }
			
            return prestamos;
        }
		catch(Exception e){
            throw e;
        }
		finally {
			disconnectDB();
		}
	}
}