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
    private int idCiudadPublicacion;
    private Date fechaPublicacion;
}
