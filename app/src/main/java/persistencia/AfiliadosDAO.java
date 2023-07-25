/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia;

import dominio.Afiliado;
import dominio.Biblioteca;
import java.util.ArrayList;

/**
 *
 * @author emiliano
 */
public class AfiliadosDAO extends DAO {
	public void insertAfiliado(Afiliado afiliado) throws Exception {
        
        try {
            if (afiliado == null){
                throw new Exception("Null object");
            }
            
            connectDB();
            updateDB("START TRANSACTION;");
            
            StringBuilder query = new StringBuilder(
                    """
                        INSERT IGNORE INTO `afiliados`(`legajo`,`id_biblioteca_afiliacion`,
                                                    `nombre`, `apellido`,`fecha_registro`)
                        VALUES (
                        """); 
            query.append("'").append(afiliado.getLegajo()).append("',");
            query.append("'").append(afiliado.getIdBibliotecaAfiliacion()).append("',");
            query.append("'").append(afiliado.getNombre()).append("',");
            query.append("'").append(afiliado.getApellido()).append("',");
            query.append("'").append(afiliado.getFechaRegistro().toString()).append("'");
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
    
    public void updateAfiliado(Afiliado afiliado) throws Exception {
        
        try {
            if (afiliado == null) {
                throw new Exception("Null object");
            }
            
            connectDB();
            updateDB("START TRANSACTION;");
            
            StringBuilder query = new StringBuilder("""
                                                    UPDATE afiliados
                                                    SET 
                                                    """);
            query.append("nombre='").append(afiliado.getNombre()).append("', ");
			query.append("apellido='").append(afiliado.getApellido()).append("' ");
			query.append("WHERE legajo=").append(afiliado.getLegajo()).append(" and ");
			query.append("id_biblioteca_afiliacion=").append(afiliado.getIdBibliotecaAfiliacion()).append(";");
			
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
	
	public void deleteAfiliado(Afiliado afiliado) throws Exception {
		try {
			if (afiliado == null) {
				throw new Exception("Null object");
			}
			
			connectDB();
			updateDB("DELETE FROM afiliados WHERE legajo="
					+ afiliado.getLegajo() + " and id_biblioteca_afiliacion="
					+ afiliado.getIdBibliotecaAfiliacion() + ";");
		}
		catch (Exception e) {
			throw e;
		}
		finally {
			disconnectDB();
		}
	}
	
	private ArrayList<Afiliado> executeSearch(String sql) throws Exception{
        try{
			connectDB();
			
            requestDB(sql);
            ArrayList<Afiliado> afiliados = new ArrayList();
			
            if(rs == null){
                throw new Exception("No results found");
            }
			
            while(rs.next()){
				afiliados.add(new Afiliado(
						rs.getInt("legajo"),
						rs.getInt("id"),
						rs.getString("nombre_afiliado"), 
						rs.getString("apellido"),
						rs.getDate("fecha_registro"),
						new Biblioteca(rs.getString("nombre_bibl"),
									   rs.getString("dependencia"))));
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
	
	public ArrayList<Afiliado> searchAfiliado(String pattern, String column) throws Exception {
		try {
			if (pattern == null || column == null) {
				throw new Exception("Null object");
			}
			
			StringBuilder query = new StringBuilder ("""
					WITH tmp_afiliados AS ( SELECT *
					FROM afiliados
                    WHERE 
					""");
			query.append(column).append(" REGEXP '").append(pattern).append("') ");
			query.append("""
						SELECT tmp_afiliados.nombre AS nombre_afiliado, 
						apellido, legajo, id, fecha_registro, 
						bibliotecas.nombre AS nombre_bibl, dependencia 
						FROM tmp_afiliados INNER JOIN bibliotecas 
						on tmp_afiliados.id_biblioteca_afiliacion=bibliotecas.id;
						""");
			return executeSearch(query.toString());
		}
		catch (Exception e) {
			throw e;
		}
	}
}
