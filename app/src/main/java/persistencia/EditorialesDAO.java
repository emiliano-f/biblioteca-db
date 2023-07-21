/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia;

import dominio.Editorial;
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
                        INSERT IGNORE INTO `editoriales`(`nombre`,`id_ciudad`)
                        VALUES (
                        """);
            query.append("`").append(editorial.getNombre()).append("`,");
            query.append("`").append(editorial.getIdEditorial()).append("`");
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
