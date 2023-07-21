/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia;

import dominio.Autor;
import java.util.ArrayList;
/**
 *
 * @author emiliano
 */
public class AutoresDAO extends DAO {
    
    public void insertLibro(Autor autor) throws Exception {
        
        try {
            if (autor == null){
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
            query.append("`").append(autor.getNombre()).append("`,");
            query.append("`").append(autor.getApellido()).append("`,");
            query.append("`").append(autor.getNacionalidad()).append("`,");
            query.append("`").append(autor.getFechaNacimiento()).append("`");
            query.append(");\n");

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
    
    public void updateLibro(Autor autor) throws Exception {
        
        try {
            if (autor == null) {
                throw new Exception("Null object");
            }
            
            connectDB();
            updateDB("START TRANSACTION;");
            
            StringBuilder query = new StringBuilder();
            
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
	
	private ArrayList<Autor> executeSearch(String sql) throws Exception{
        try{
			connectDB();
			
            requestDB(sql);
            ArrayList<Autor> autores = new ArrayList();
			
            if(rs == null){
                throw new Exception("No results found");
            }
			
            while(rs.next()){
				autores.add(new Autor(rs.getString("nombre"), 
						rs.getString("apellido"),
						rs.getString("nacionalidad"),
						rs.getDate("fecha_nacimiento")));
            }
            return autores;
        }
		catch(Exception e){
            throw e;
        }
		finally {
			disconnectDB();
		}
    }
	
	public ArrayList<Autor> searchAutor(String pattern, String column) throws Exception {
		try {
			if (pattern == null || column == null) {
				throw new Exception("Null object");
			}
			
			String query = """
					SELECT nombre, apellido, nacionalidad, fecha_nacimiento
					FROM autores
                    WHERE 
					""" + column + " REGEXP '" + pattern + "';";
			return executeSearch(query);
		}
		catch (Exception e) {
			throw e;
		}
	}
}
