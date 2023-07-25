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
    
    public void insertAutor(Autor autor) throws Exception {
        
        try {
            if (autor == null){
                throw new Exception("Null object");
            }
            
            connectDB();
            updateDB("START TRANSACTION;");
            
            StringBuilder query = new StringBuilder(
                    """
                        INSERT IGNORE INTO `autores`(`nombre`,`apellido`,
                                                    `nacionalidad`)
                        VALUES (
                        """); // no puse fecha de naciimenot
            query.append("'").append(autor.getNombre()).append("',");
            query.append("'").append(autor.getApellido()).append("',");
            query.append("'").append(autor.getNacionalidad()).append("'");
            //query.append("`").append(autor.getFechaNacimiento()).append("`");
            query.append(");");

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
    
    public void updateAutor(Autor autor) throws Exception {
        
        try {
            if (autor == null) {
                throw new Exception("Null object");
            }
            
            connectDB();
            updateDB("START TRANSACTION;");
            
            StringBuilder query = new StringBuilder("""
                                                    UPDATE autores
                                                    SET 
                                                    """);
            query.append("nombre='").append(autor.getNombre()).append("', ");
			query.append("apellido='").append(autor.getApellido()).append("', ");
			query.append("nacionalidad='").append(autor.getNacionalidad()).append("' ");
			//query.append("fecha_nacimiento=").append(autor.getFechaNacimiento()).append(" ");
			query.append("WHERE id=").append(autor.getIdAutor()).append(";");
			
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
	
	public void deleteAutor(Autor autor) throws Exception {
		try {
			if (autor == null) {
				throw new Exception("Null object");
			}
			
			connectDB();
			updateDB("DELETE FROM autores WHERE id="
					+ autor.getIdAutor() + ";");
		}
		catch (Exception e) {
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
				autores.add(new Autor(
						rs.getInt("id"),
						rs.getString("nombre"), 
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
					SELECT *
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
