/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dominio;

/**
 *
 * @author emiliano
 */
public class Editorial {
    private int idEditorial;
    private int idCiudad; //foreign
    private String nombre;

    /**
     * @return the idEditorial
     */
    public int getIdEditorial() {
        return idEditorial;
    }

    /**
     * @param idEditorial the idEditorial to set
     */
    public void setIdEditorial(int idEditorial) {
        this.idEditorial = idEditorial;
    }

    /**
     * @return the idCiudad
     */
    public int getIdCiudad() {
        return idCiudad;
    }

    /**
     * @param idCiudad the idCiudad to set
     */
    public void setIdCiudad(int idCiudad) {
        this.idCiudad = idCiudad;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
}
