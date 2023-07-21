/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dominio;

import java.sql.Date;
/**
 *
 * @author emiliano
 */
public class Libro {
    private String ISBN;
    private int idAutor; // foreign
    private String nombre;
    private String idioma;
    private String genero;
    private int idEditorial; // foreign
    private int edicion;
    private int paginas;
    private Date fechaPublicacion;

    /**
     * @return the ISBN
     */
    public String getISBN() {
        return ISBN;
    }

    /**
     * @param ISBN the ISBN to set
     */
    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    /**
     * @return the idAutor
     */
    public int getIdAutor() {
        return idAutor;
    }

    /**
     * @param idAutor the idAutor to set
     */
    public void setIdAutor(int idAutor) {
        this.idAutor = idAutor;
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

    /**
     * @return the idioma
     */
    public String getIdioma() {
        return idioma;
    }

    /**
     * @param idioma the idioma to set
     */
    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    /**
     * @return the genero
     */
    public String getGenero() {
        return genero;
    }

    /**
     * @param genero the genero to set
     */
    public void setGenero(String genero) {
        this.genero = genero;
    }

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
     * @return the edicion
     */
    public int getEdicion() {
        return edicion;
    }

    /**
     * @param edicion the edicion to set
     */
    public void setEdicion(int edicion) {
        this.edicion = edicion;
    }

    /**
     * @return the paginas
     */
    public int getPaginas() {
        return paginas;
    }

    /**
     * @param paginas the paginas to set
     */
    public void setPaginas(int paginas) {
        this.paginas = paginas;
    }

    /**
     * @return the fechaPublicacion
     */
    public Date getFechaPublicacion() {
        return fechaPublicacion;
    }

    /**
     * @param fechaPublicacion the fechaPublicacion to set
     */
    public void setFechaPublicacion(Date fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }
    
}
