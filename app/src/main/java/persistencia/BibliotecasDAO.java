/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia;
import java.util.ArrayList;
import dominio.Biblioteca;
/**
 *
 * @author emiliano
 */
public class BibliotecasDAO extends DAO {
	
	private ArrayList<Biblioteca> executeSearch(String sql) throws Exception{
        try{
			connectDB();
			
            requestDB(sql);
            ArrayList<Biblioteca> bibliotecas = new ArrayList();
			
            if(rs == null){
                throw new Exception("No results found");
            }
			
            while(rs.next()){
				bibliotecas.add(new Biblioteca(
						rs.getInt("id"),
						rs.getString("nombre"), 
						rs.getString("dependencia")));
            }
            return bibliotecas;
        }
		catch(Exception e){
            throw e;
        }
		finally {
			disconnectDB();
		}
    }
	
	public ArrayList<Biblioteca> getAll() throws Exception {
		try {
			String query = """
					SELECT *
					FROM bibliotecas;
                    """;
			
			return executeSearch(query);
		}
		catch (Exception e) {
			throw e;
		}
	}
	
	public ArrayList<Biblioteca> searchBiblioteca(String pattern, String column) throws Exception {
		try {
			if (pattern == null || column == null) {
				throw new Exception("Null object");
			}
			
			String query = """
					SELECT *
					FROM bibliotecas
                    WHERE 
					""" + column + " REGEXP '" + pattern + "';";
			return executeSearch(query);
		}
		catch (Exception e) {
			throw e;
		}
	}
}
