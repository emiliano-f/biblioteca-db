/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia;

import dominio.Editorial;
import java.util.ArrayList;
/**
 *
 * @author emiliano
 */
public class EditorialesDAO extends DAO {
    
    public void insertEditorial(Editorial editorial) throws Exception {
        try {
            if (editorial == null) {
                throw new Exception("Null object");
            }
            
            connectDB();
            updateDB("START TRANSACTION;");
            
            StringBuilder query = new StringBuilder(
                    """
                        INSERT IGNORE INTO `editoriales`(`nombre`,`ciudad`)
                        VALUES (
                        """);
            query.append("'").append(editorial.getNombre()).append("',");
            query.append("'").append(editorial.getCiudad()).append("'");
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
    
    public void updateEditorial(Editorial editorial) throws Exception {
        
        try {
            if (editorial == null) {
                throw new Exception("Null object");
            }
            
            connectDB();
            updateDB("START TRANSACTION;");
            
            StringBuilder query = new StringBuilder("""
                                                    UPDATE editoriales
                                                    SET 
                                                    """);
            query.append("nombre='").append(editorial.getNombre()).append("', ");
			query.append("ciudad='").append(editorial.getCiudad()).append("' ");
			query.append("WHERE id=").append(editorial.getIdEditorial()).append(";");
			
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
	
	public void deleteEditorial(Editorial editorial) throws Exception {
		try {
			if (editorial == null) {
				throw new Exception("Null object");
			}
			
			connectDB();
			updateDB("DELETE FROM editoriales WHERE id="
					+ editorial.getIdEditorial() + ";");
		}
		catch (Exception e) {
			throw e;
		}
		finally {
			disconnectDB();
		}
	}
	
	private ArrayList<Editorial> executeSearch(String sql) throws Exception{
        try{
			connectDB();
			
            requestDB(sql);
            ArrayList<Editorial> editoriales = new ArrayList();
			
            if(rs == null){
                throw new Exception("No results found");
            }
			
            while(rs.next()){
				editoriales.add(new Editorial(
						rs.getInt("id"),
						rs.getString("nombre"), 
						rs.getString("ciudad")));
            }
            return editoriales;
        }
		catch(Exception e){
            throw e;
        }
		finally {
			disconnectDB();
		}
    }
	
	public ArrayList<Editorial> searchEditorial(String pattern, String column) throws Exception {
		try {
			if (pattern == null || column == null) {
				throw new Exception("Null object");
			}
			
			String query = """
					SELECT *
					FROM editoriales
                    WHERE 
					""" + column + " REGEXP '" + pattern + "';";
			return executeSearch(query);
		}
		catch (Exception e) {
			throw e;
		}
	}
}
