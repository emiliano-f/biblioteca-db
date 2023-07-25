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
public class Autor {
    private int idAutor;
    private String nombre;
    private String apellido;
    private String nacionalidad;
    private Date fechaNacimiento;

    public Autor(String nombre, String apellido) {
		this.nombre = nombre;
		this.apellido = apellido;
    }
    
    public Autor(String nombre, String apellido, String nacionalidad) {
		this(nombre, apellido);
		this.nacionalidad = nacionalidad;
    }
	
	public Autor(int idAutor, String nombre, String apellido) {
		this(nombre, apellido);
		this.idAutor = idAutor;
	}

	public Autor(int idAutor, String nombre, String apellido, String nacionalidad) {
		this(nombre, apellido, nacionalidad);
		this.idAutor = idAutor;
    }
	
    public Autor(String nombre, String apellido, String nacionalidad, Date fechaNacimiento) {
		this(nombre, apellido, nacionalidad);
		this.fechaNacimiento = fechaNacimiento;
    }

	public Autor(int idAutor, String nombre, String apellido, String nacionalidad, Date fechaNacimiento) {
		this(nombre, apellido, nacionalidad, fechaNacimiento);
		this.idAutor = idAutor;
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
	 * @return the nacionalidad
	 */
	public String getNacionalidad() {
		return nacionalidad;
	}

	/**
	 * @param nacionalidad the nacionalidad to set
	 */
	public void setNacionalidad(String nacionalidad) {
		this.nacionalidad = nacionalidad;
	}

	/**
	 * @return the fechaNacimiento
	 */
	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	/**
	 * @param fechaNacimiento the fechaNacimiento to set
	 */
	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}
    
	@Override
	public String toString(){
		return apellido + ", " + nombre;
	}
}
