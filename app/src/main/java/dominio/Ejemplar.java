/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dominio;

/**
 *
 * @author emiliano
 */
public class Ejemplar {
    private int idEjemplar;
    private String ISBN;
    private int numero;
    private int idBiblioteca;
    private String idUbicacion;
	
	private Biblioteca biblioteca;
	private Libro libro;
	private String disponible;
	
	private int cantidad = 1;

	public Ejemplar(String ISBN, int idBiblioteca, String idUbicacion, int numero) {
		this.numero = numero;
		this.idUbicacion = idUbicacion;
		this.idBiblioteca = idBiblioteca;
		this.ISBN = ISBN;
	}

	public Ejemplar(int idEjemplar, String ISBN, int numero, int idBiblioteca, String idUbicacion, Biblioteca biblioteca, Libro libro) {
		this.idEjemplar = idEjemplar;
		this.ISBN = ISBN;
		this.numero = numero;
		this.idBiblioteca = idBiblioteca;
		this.idUbicacion = idUbicacion;
		this.biblioteca = biblioteca;
		this.libro = libro;
	}

	/**
	 * @return the idEjemplar
	 */
	public int getIdEjemplar() {
		return idEjemplar;
	}

	/**
	 * @param idEjemplar the idEjemplar to set
	 */
	public void setIdEjemplar(int idEjemplar) {
		this.idEjemplar = idEjemplar;
	}

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
	 * @return the numero
	 */
	public int getNumero() {
		return numero;
	}

	/**
	 * @param numero the numero to set
	 */
	public void setNumero(int numero) {
		this.numero = numero;
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
	 * @return the idUbicacion
	 */
	public String getIdUbicacion() {
		return idUbicacion;
	}

	/**
	 * @param idUbicacion the idUbicacion to set
	 */
	public void setIdUbicacion(String idUbicacion) {
		this.idUbicacion = idUbicacion;
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

	/**
	 * @return the libro
	 */
	public Libro getLibro() {
		return libro;
	}

	/**
	 * @param libro the libro to set
	 */
	public void setLibro(Libro libro) {
		this.libro = libro;
	}
	
	public int getCantidad(){
		return cantidad;
	}
	
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
	
	public void addCantidad(){
		this.cantidad++;
	}

	/**
	 * @return the disponible
	 */
	public String getDisponible() {
		return disponible;
	}

	/**
	 * @param disponible the disponible to set
	 */
	public void setDisponible(String disponible) {
		this.disponible = disponible;
	}
}
