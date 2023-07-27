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
public class Afiliado {
	private int legajo;
    private int idBibliotecaAfiliacion; // foreign
    private String nombre;
    private String apellido;
    private Date fechaRegistro;
	private Biblioteca biblioteca;

	public Afiliado(int legajo, int idBibliotecaAfiliacion, String nombre, String apellido) {
		this.legajo = legajo;
		this.idBibliotecaAfiliacion = idBibliotecaAfiliacion;
		this.nombre = nombre;
		this.apellido = apellido;
	}
	
	public Afiliado(int legajo, int idBibliotecaAfiliacion, String nombre, String apellido, Date fechaRegistro) {
		this(legajo, idBibliotecaAfiliacion, nombre, apellido);
		this.fechaRegistro = fechaRegistro;
	}
	
	public Afiliado(int legajo, int idBibliotecaAfiliacion, String nombre, String apellido, Date fechaRegistro, Biblioteca biblioteca) {
		this(legajo, idBibliotecaAfiliacion, nombre, apellido, fechaRegistro);
		this.biblioteca = biblioteca;
	}
	
	/**
	 * @return the legajo
	 */
	public int getLegajo() {
		return legajo;
	}

	/**
	 * @param legajo the legajo to set
	 */
	public void setLegajo(int legajo) {
		this.legajo = legajo;
	}

	/**
	 * @return the idBibliotecaAfiliacion
	 */
	public int getIdBibliotecaAfiliacion() {
		return idBibliotecaAfiliacion;
	}

	/**
	 * @param idBibliotecaAfiliacion the idBibliotecaAfiliacion to set
	 */
	public void setIdBibliotecaAfiliacion(int idBibliotecaAfiliacion) {
		this.idBibliotecaAfiliacion = idBibliotecaAfiliacion;
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
	 * @return the apellido
	 */
	public String getApellido() {
		return apellido;
	}

	/**
	 * @param apellido the apellido to set
	 */
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	/**
	 * @return the fechaRegistro
	 */
	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	/**
	 * @param fechaRegistro the fechaRegistro to set
	 */
	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	/**
	 * @return the biblioteca
	 */
	public Biblioteca getBiblioteca() {
		return biblioteca;
	}

	/**
	 * @param biblioteca the biblioteca to set
	 */
	public void setBiblioteca(Biblioteca biblioteca) {
		this.biblioteca = biblioteca;
	}
	
}
