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
public class Prestamo {
    private int legajo;
    private int idBibliotecaAfiliacion; 
    private int idEjemplar;
    private Date from;
    private Date to;
	private String devuelto;
	private Ejemplar ejemplar;
	private Afiliado afiliado;

	public Prestamo(){
	}
	
	public Prestamo(int legajo, int idBibliotecaAfiliacion, int idEjemplar, Date from, Date to, String devuelto, Ejemplar ejemplar) {
		this.legajo = legajo;
		this.idBibliotecaAfiliacion = idBibliotecaAfiliacion;
		this.idEjemplar = idEjemplar;
		this.from = from;
		this.to = to;
		this.devuelto = devuelto;
		this.ejemplar = ejemplar;
	}
	
	public Prestamo(int legajo, int idBibliotecaAfiliacion, int idEjemplar, Date from, Date to, String devuelto, Ejemplar ejemplar, Afiliado afiliado) {
		this(legajo,idBibliotecaAfiliacion,idEjemplar,from,to,devuelto,ejemplar);
		this.afiliado = afiliado;
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
	 * @return the from
	 */
	public Date getFrom() {
		return from;
	}

	/**
	 * @param from the from to set
	 */
	public void setFrom(Date from) {
		this.from = from;
	}

	/**
	 * @return the to
	 */
	public Date getTo() {
		return to;
	}

	/**
	 * @param to the to to set
	 */
	public void setTo(Date to) {
		this.to = to;
	}
	
	/**
	 * @return the ejemplar
	 */
	public Ejemplar getEjemplar() {
		return ejemplar;
	}

	/**
	 * @param ejemplar the ejemplar to set
	 */
	public void setEjemplar(Ejemplar ejemplar) {
		this.ejemplar = ejemplar;
	}

	/**
	 * @return the afiliado
	 */
	public Afiliado getAfiliado() {
		return afiliado;
	}

	/**
	 * @param afiliado the afiliado to set
	 */
	public void setAfiliado(Afiliado afiliado) {
		this.afiliado = afiliado;
	}

	/**
	 * @return the devuelto
	 */
	public String getDevuelto() {
		return devuelto;
	}

	/**
	 * @param devuelto the devuelto to set
	 */
	public void setDevuelto(String devuelto) {
		this.devuelto = devuelto;
	}
	
	
}
