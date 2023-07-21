/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia;

import dominio.Libro;

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
            query.append("`").append(libro.getISBN()).append("`,");
            query.append("`").append(libro.getNombre()).append("`,");
            query.append("`").append(libro.getIdioma()).append("`,");
            query.append("`").append(libro.getGenero()).append("`,");
            query.append("`").append(libro.getIdEditorial()).append("`,");
            query.append("`").append(libro.getEdicion()).append("`,");
            query.append("`").append(libro.getPaginas()).append("`,");
            query.append("`").append(libro.getFechaPublicacion()).append("`");
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
    
    public void updateLibro(Libro libro) throws Exception {
        
        try {
            if (libro == null) {
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
}
