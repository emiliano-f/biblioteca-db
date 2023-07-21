/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia;

import dominio.Ciudad;

/**
 *
 * @author emiliano
 */
public class CiudadesDAO extends DAO {
    public void insertCiudad(Ciudad ciudad) throws Exception {
        try {
            if (ciudad == null) {
                throw new Exception("Null object");
            }
            
            connectDB();
            updateDB("START TRANSACTION;");
            
            StringBuilder query = new StringBuilder(
                    """
                        INSERT IGNORE INTO `ciudades`(`nombre`)
                        VALUES (
                        """);
            query.append("`").append(ciudad.getNombre()).append("`");
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
}
