/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia;

import dominio.Autor;
import dominio.Editorial;
import dominio.Libro;
import java.util.ArrayList;

/**
 *
 * @author emiliano
 */
public class LibrosDAO extends DAO {
    
    public void insertLibro(Libro libro) throws Exception {
        
        try {
            if (libro == null){
                throw new Exception("Null object");
            }
            
            connectDB();
            updateDB("START TRANSACTION;");
            
            StringBuilder query = new StringBuilder(
                    """
                        INSERT IGNORE INTO `libros`(`ISBN`,`nombre`,`idioma`,
                                                    `genero`, `id_editorial`,
                                                    `edicion`, `paginas`,
                                                    `year_publicacion`)
                        VALUES (
                        """);
            query.append("'").append(libro.getISBN()).append("',");
            query.append("'").append(libro.getTitulo()).append("',");
            query.append("'").append(libro.getIdioma()).append("',");
            query.append("'").append(libro.getGenero()).append("',");
            query.append("'").append(libro.getIdEditorial()).append("',");
            query.append("'").append(libro.getEdicion()).append("',");
            query.append("'").append(libro.getPaginas()).append("',");
            query.append("'").append(libro.getFechaPublicacion().toString()).append("'");
            query.append(");");

            updateDB(query.toString());
			
			query.setLength(0);
			query.append("""
						INSERT IGNORE INTO libros_autores(id_autor, id_libro)
						VALUES 
						""");
			for (Autor autor: libro.getAutores()) {
				query.append("('").append(autor.getIdAutor()).append("','");
				query.append(libro.getISBN()).append("'),");
			}
			query.deleteCharAt(query.length()-1); //delete ,
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
    
    public void updateLibro(Libro libro) throws Exception {
        
        try {
            if (libro == null) {
                throw new Exception("Null object");
            }
            
            connectDB();
            updateDB("START TRANSACTION;");
            
            StringBuilder query = new StringBuilder("""
                                                    UPDATE libros
                                                    SET 
                                                    """);
            query.append("titulo='").append(libro.getTitulo()).append("', ");
            query.append("edicion='").append(libro.getEdicion()).append("', ");
            query.append("id_editorial=").append(libro.getIdEditorial()).append(", ");
            query.append("idioma='").append(libro.getIdioma()).append("', ");
            query.append("genero='").append(libro.getGenero()).append("', ");
            query.append("paginas=").append(libro.getPaginas()).append(", ");
            query.append("year_publicacion='").append(libro.getFechaPublicacion()).append("' ");
			query.append("WHERE ISBN='").append(libro.getISBN()).append("';");
			
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
	
	public void deleteLibro(Libro libro) throws Exception {
		try {
			if (libro == null) {
				throw new Exception("Null object");
			}
			
			connectDB();
			updateDB("START TRANSACTION;");
			
			updateDB("DELETE FROM libros_autores WHERE ISBN='"
					+ libro.getISBN() + "';");
			updateDB("DELETE FROM libros WHERE ISBN='"
					+ libro.getISBN() + "';");
			
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
	
	private ArrayList<Libro> executeSearch(String sql) throws Exception{
        try{
			connectDB();
			
            requestDB(sql);
            ArrayList<Libro> libros = new ArrayList();
			
            if(rs == null){
                throw new Exception("No results found");
            }
			
			String prevISBN = "";
			String ISBN;
			ArrayList<Autor> autores = new ArrayList();
			Libro libro;
			
            while(rs.next()){
				ISBN = rs.getString("ISBN");
				if (!prevISBN.equals(ISBN)){
					
					libro = new Libro(ISBN,
										rs.getString("titulo"),
										rs.getInt("edicion"),
										rs.getInt("id_editorial"),
										new Editorial(rs.getInt("id_editorial"),
													   rs.getString("nombre_editorial"))
										);
					libro.setFechaPublicacion(java.sql.Date.valueOf(rs.getString("year_publicacion")));
					libro.setIdioma(rs.getString("idioma"));
					libro.setPaginas(rs.getInt("paginas"));
					libro.setGenero(rs.getString("genero"));
					libros.add(libro);
					autores = new ArrayList();
					libro.setAutores(autores);
				}
				autores.add(new Autor(rs.getInt("id_autor"),
										rs.getString("nombre_autor"),
										rs.getString("apellido_autor")));
				prevISBN = ISBN;
            }
			
            return libros;
        }
		catch(Exception e){
            throw e;
        }
		finally {
			disconnectDB();
		}
    }
	
	public ArrayList<Libro> searchLibro(String pattern, String column) throws Exception {
		try {
			if (pattern == null || column == null) {
				throw new Exception("Null object");
			}
			
			StringBuilder query;
			
			if (column.equals("autor")){
				query = new StringBuilder ("""
						WITH filtro_pattern AS ( SELECT id AS id_autor, apellido AS apellido_autor, 
														nombre AS nombre_autor
						FROM autores
						WHERE 
						""");
				query.append("nombre REGEXP '").append(pattern).append("' OR ");
				query.append("apellido REGEXP '").append(pattern).append("'), ");
				query.append("""
						tmp_autor_libro AS (
                        SELECT filtro_pattern.*, libros_autores.id_libro as ISBN
                        FROM filtro_pattern INNER JOIN libros_autores
                                                  ON filtro_pattern.id_autor=libros_autores.id_autor)
						""");
				query.append("""
						SELECT tmp_autor_libro.*, 
								editoriales.nombre AS nombre_editorial, editoriales.id AS id_editorial,
                                libros.titulo, libros.edicion, libros.year_publicacion, libros.paginas, libros.idioma, libros.genero
                        FROM tmp_autor_libro INNER JOIN libros
                                                  ON tmp_autor_libro.ISBN=libros.ISBN
                                             INNER JOIN editoriales
                                                  ON libros.id_editorial=editoriales.id
                        ORDER BY tmp_autor_libro.ISBN;
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
												ON filtro_pattern.id_editorial=editoriales.id)    
						""");
				query.append("""
                        SELECT libro_edit.*, 
								autores.apellido AS apellido_autor, autores.nombre AS nombre_autor, autores.id as id_autor
                        FROM libro_edit INNER JOIN libros_autores
                                             ON libro_edit.ISBN=libros_autores.id_libro
                                        INNER JOIN autores
                                             ON libros_autores.id_autor=autores.id
                        ORDER BY libro_edit.ISBN;
						""");
			}
			
			return executeSearch(query.toString());
		}
		catch (Exception e) {
			throw e;
		}
	}
}
