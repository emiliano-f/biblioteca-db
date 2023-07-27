/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia;

import dominio.Autor;
import dominio.Biblioteca;
import dominio.Editorial;
import dominio.Ejemplar;
import dominio.Libro;
import java.util.ArrayList;

/**
 *
 * @author emiliano
 */
public class EjemplaresDAO extends DAO {
    
    public void insertEjemplar(Ejemplar ejemplar, int cantidad) throws Exception {
        
        try {
            if (ejemplar == null){
                throw new Exception("Null object");
            }
            
            connectDB();
            updateDB("START TRANSACTION;");
			
			StringBuilder query = new StringBuilder("""
                                           INSERT IGNORE INTO ubicaciones(id, id_biblioteca)
                                           VALUES ('""");
			query.append(ejemplar.getIdUbicacion()).append("','");
			query.append(ejemplar.getIdBiblioteca()).append("');");
			
			updateDB(query.toString());
			
			int idMin = ejemplar.getNumero();
			query.setLength(0);
            query.append(
                    """
                        INSERT IGNORE INTO `ejemplares`(`ISBN`,`id_biblioteca`,
                                                    `id_ubicacion`,`numero`)
                        VALUES 
                        """);
			
			for (int i=0; i<cantidad; i++) {
				query.append("('").append(ejemplar.getISBN()).append("',");
				query.append("'").append(ejemplar.getIdBiblioteca()).append("',");
				query.append("'").append(ejemplar.getIdUbicacion()).append("',");
				query.append("'").append(idMin).append("'),");
				idMin++;
			}
			
			query.deleteCharAt(query.length()-1);
			query.append(";");
			
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
    
    public void updateEjemplar(Ejemplar ejemplar) throws Exception {
        
        try {
            if (ejemplar == null) {
                throw new Exception("Null object");
            }
            
            connectDB();
            updateDB("START TRANSACTION;");
            
			StringBuilder query = new StringBuilder("""
                                           INSERT IGNORE INTO ubicaciones(id, id_biblioteca)
                                           VALUES ('""");
			query.append(ejemplar.getIdUbicacion()).append("','");
			query.append(ejemplar.getIdBiblioteca()).append("');");
			updateDB(query.toString());
			
			query.setLength(0);
            query.append("""
                         UPDATE ejemplares
                         SET 
                         """);
            query.append("id_ubicacion='").append(ejemplar.getIdUbicacion()).append("' ");
			query.append("WHERE id='").append(ejemplar.getIdEjemplar()).append("';");
			
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
	
	public void deleteEjemplar(Ejemplar ejemplar) throws Exception {
		try {
			if (ejemplar == null) {
				throw new Exception("Null object");
			}
			
			connectDB();
			updateDB("START TRANSACTION;");
			
			updateDB("DELETE FROM ejemplares WHERE id="
					+ ejemplar.getIdEjemplar() + ";");
			
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
	
	private ArrayList<Ejemplar> executeSearch(String sql) throws Exception{
        try{
			connectDB();
			
            requestDB(sql);
            ArrayList<Ejemplar> ejemplares = new ArrayList();
			
            if(rs == null){
                throw new Exception("No results found");
            }
			
			ArrayList<Autor> autores;
			Libro libro;
			
            while(rs.next()){
				
				libro = new Libro(rs.getString("ISBN"),
									rs.getString("titulo"),
									rs.getInt("edicion"),
									rs.getInt("id_editorial"),
									new Editorial(rs.getInt("id_editorial"),
													rs.getString("nombre_editorial"))
								);
				autores = new ArrayList();
				autores.add(new Autor("", rs.getString("all_autores")));
				libro.setAutores(autores);
				
				ejemplares.add(new Ejemplar(rs.getInt("id_ejemplar"),
											rs.getString("ISBN"),
											rs.getInt("numero"),
											rs.getInt("id_biblioteca"),
											rs.getString("id_ubicacion"),
											new Biblioteca(rs.getInt("id_biblioteca"),
															rs.getString("nombre_biblioteca"),
															rs.getString("dependencia")),
											libro
				));
            }
			
            return ejemplares;
        }
		catch(Exception e){
            throw e;
        }
		finally {
			disconnectDB();
		}
    }
	
	public ArrayList<Ejemplar> searchEjemplar(String pattern, String column) throws Exception {
		try {
			if (pattern == null || column == null) {
				throw new Exception("Null object");
			}			
			
			StringBuilder query;
			
			if (column.equals("autor")){
				query = new StringBuilder ("""
						WITH filtro_pattern AS ( SELECT id
						FROM autores
						WHERE 
						""");
				query.append("nombre REGEXP '").append(pattern).append("' OR ");
				query.append("apellido REGEXP '").append(pattern).append("'), ");
				
				// obtenemos los ISBN de los libros con los autores matcheados
				query.append("""
                        tmp_libros_1 AS (
                        SELECT libros_autores.id_libro, libros_autores.id_autor
                        FROM libros_autores INNER JOIN filtro_pattern
                                                  ON libros_autores.id_autor=filtro_pattern.id), 
						""");
				// obtenemos todos los autores de los ISBN filtrados anteriormente
				query.append("""
                        tmp_libros_2 AS (
                        SELECT libros_autores.id_libro, libros_autores.id_autor
                        FROM libros_autores INNER JOIN tmp_libros_1
                                                  ON libros_autores.id_libro=tmp_libro_1.id_libro), 
						""");
				// obtenemos el nombre y apellido de los autores
				query.append("""
                        tmp_libros_autores AS (
                        SELECT tmp_libros_2.id_libro AS ISBN,
                               CONCAT(autores.apellido, ', ', autores.nombre) AS full_name
						FROM tmp_libros_2 INNER JOIN autores
											ON tmp_libros_2.id_autor=autores.id), 
						""");
				// agrupamos los ISBN y los nombres de todos los autores
				query.append("""
                        tmp_libros_full_autores AS (
                        SELECT ISBN, GROUP_CONCAT(full_name SEPARATOR '; ') AS all_autores
                        FROM tmp_libros_autores
                        GROUP BY ISBN), 
						""");
				// completamos datos de los libros
				query.append("""
                        tmp_full_libros AS (
						SELECT libros.*, tmp_libros_full_autores.all_autores, 
								editoriales.nombre AS nombre_editorial
                        FROM tmp_libros_full_autores INNER JOIN libros
														ON tmp_libros_full_autores.ISBN=libros.ISBN
													INNER JOIN editoriales
														ON libros.id_editorial=editoriales.id)
						""");
				query.append("""
                        SELECT tmp_full_libros.*,
                               ejemplares.id AS id_ejemplar, ejemplares.numero, ejemplares.id_biblioteca, ejemplares.id_ubicacion,
                               bibliotecas.nombre AS nombre_biblioteca, bibliotecas.dependencia
                        FROM tmp_full_libros INNER JOIN	ejemplares
                                                  ON tmp_full_libros.ISBN=ejemplares.ISBN
                                             INNER JOIN bibliotecas
                                                  ON ejemplares.id_biblioteca=biblioteca.id
                        ORDER BY tmp_full_libros.ISBN;
						""");
			}
			else {
				query = new StringBuilder ("""
						WITH filtro_pattern AS ( SELECT *
						FROM libros
						WHERE 
						""");
				query.append(column).append(" REGEXP '").append(pattern).append("'), ");
				query.append(""" 
						libro_edit AS (
						SELECT filtro_pattern.*, editoriales.nombre AS nombre_editorial
                        FROM filtro_pattern INNER JOIN editoriales
												ON filtro_pattern.id_editorial=editoriales.id),    
						""");
				query.append("""
                        tmp_last_filter AS (
                        SELECT libro_edit.*, 
                               GROUP_CONCAT(CONCAT(autores.apellido, ', ', autores.nombre) SEPARATOR '; ') AS all_autores
                        FROM libro_edit INNER JOIN libros_autores
                                             ON libro_edit.ISBN=libros_autores.id_libro
                                        INNER JOIN autores
                                             ON libros_autores.id_autor=autores.id
						GROUP BY libro_edit.ISBN), 
						""");
				query.append("""
                        SELECT tmp_last_filter.*,
                               ejemplares.id AS id_ejemplar, ejemplares.numero, ejemplares.id_biblioteca, ejemplares.id_ubicacion,
                               bibliotecas.nombre AS nombre_biblioteca, bibliotecas.dependencia
                        FROM tmp_last_filter INNER JOIN	ejemplares
                                                  ON tmp_last_filter.ISBN=ejemplares.ISBN
                                             INNER JOIN bibliotecas
                                                  ON ejemplares.id_biblioteca=biblioteca.id
                        ORDER BY tmp_last_filter.ISBN;
						""");
			}
			
			return executeSearch(query.toString());
		}
		catch (Exception e) {
			throw e;
		}
	}
	
	public int searchMaxIdEjemplar(String ISBN, int idBiblioteca) throws Exception {
		try {
			if (ISBN == null || ISBN.length() <= 0) {
				throw new Exception("Null arguments");
			}
			StringBuilder query = new StringBuilder("""
                                           SELECT max(numero) AS id_max
                                           FROM ejemplares
                                           WHERE ISBN='""");
			query.append(ISBN).append("' and id_biblioteca=");
			query.append(idBiblioteca).append(";");
			
			connectDB();
			requestDB(query.toString());
			
			if(rs == null){
                throw new Exception("No results found");
            }
			
			rs.next();
			return rs.getInt("id_max");
		}
		catch (Exception e) {
			throw e;
		}
		finally {
			disconnectDB();
		}
	}
}
