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
    private String nombre;
	private String ciudad;

	public Editorial(String nombre, String ciudad) {
		this.nombre = nombre;
		this.ciudad = ciudad;
	}
	
	public Editorial(int idEditorial, String nombre) {
		this.nombre = nombre;
		this.idEditorial = idEditorial;
	}
	
	public Editorial(int idEditorial, String nombre, String ciudad) {
		this(nombre, ciudad);
		this.idEditorial = idEditorial;
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
	 * @return the ciudad
	 */
	public String getCiudad() {
		return ciudad;
	}

	/**
	 * @param ciudad the ciudad to set
	 */
	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}
    
	@Override
	public String toString() {
		return nombre + " (" + ciudad + ")";
	}
}
