/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia;

import dominio.Afiliado;
import dominio.Biblioteca;
import dominio.Ejemplar;
import dominio.Libro;
import dominio.Prestamo;
import java.util.ArrayList;

/**
 *
 * @author emiliano
 */
public class ReportesDAO extends DAO {
	public ArrayList<Afiliado> generarReporte() throws Exception {
		return searchAfiliado();
	}
	
	private ArrayList<Afiliado> executeSearch(String sql) throws Exception{
        try{
			connectDB();
			
            requestDB(sql);
            ArrayList<Afiliado> afiliados = new ArrayList();
			Afiliado afiliado;
			
            if(rs == null){
                throw new Exception("No results found");
            }
			
            while(rs.next()){
				afiliado = new Afiliado(rs.getInt("legajo"),
										rs.getInt("id"),
										rs.getString("nombre"),
										rs.getString("apellido"),
										rs.getDate("fecha_registro"),
										new Biblioteca(rs.getInt("id"),
														rs.getString("nombre"),
														rs.getString("dependencia")));
				
				afiliados.add(afiliado);
            }
			
            return afiliados;
        }
		catch(Exception e){
            throw e;
        }
		finally {
			disconnectDB();
		}
    }
	
	public ArrayList<Afiliado> searchAfiliado() throws Exception {
		try {
			
			String query = """
                           SELECT *
                           FROM afiliados, bibliotecas
						   WHERE id=id_biblioteca_afiliacion;
                  """;
			
			return executeSearch(query);
		}
		catch (Exception e) {
			throw e;
		}
		finally {
			disconnectDB();
		}
	}
}
