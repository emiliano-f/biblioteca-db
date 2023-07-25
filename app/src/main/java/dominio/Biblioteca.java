/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dominio;

/**
 *
 * @author emiliano
 */
public class Biblioteca {
    private int idBiblioteca;
    private String nombre;
	private String dependencia;

	public Biblioteca(String nombre, String dependencia) {
		this.nombre = nombre;
		this.dependencia = dependencia;
	}
	
	public Biblioteca(int idBiblioteca, String nombre, String dependencia) {
		this(nombre, dependencia);
		this.idBiblioteca = idBiblioteca;
	}

	/**
	 * @return the idBiblioteca
	 */
	public int getIdBiblioteca() {
		return idBiblioteca;
	}

	/**
	 * @param idBiblioteca the idBiblioteca to set
	 */
	public void setIdBiblioteca(int idBiblioteca) {
		this.idBiblioteca = idBiblioteca;
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
	 * @return the dependencia
	 */
	public String getDependencia() {
		return dependencia;
	}

	/**
	 * @param dependencia the dependencia to set
	 */
	public void setDependencia(String dependencia) {
		this.dependencia = dependencia;
	}
	
	@Override
	public String toString() {
		return dependencia + " (" + nombre + ")";
	}
}
