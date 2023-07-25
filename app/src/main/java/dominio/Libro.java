/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dominio;

import java.sql.Date;
import java.util.ArrayList;
/**
 *
 * @author emiliano
 */
public class Libro {
    private String ISBN;
    private String titulo;
    private String idioma;
    private String genero;
    private int idEditorial; // foreign
    private int edicion;
    private int paginas;
    private Date fechaPublicacion;
	private Editorial editorial;
	private ArrayList<Autor> autores;

	public Libro(String ISBN, String titulo, int edicion, int idEditorial) {
		this.ISBN = ISBN;
		this.titulo = titulo;
		this.idEditorial = idEditorial;
		this.edicion = edicion;
	}
	
	public Libro(String ISBN, String titulo, int edicion, int idEditorial, Editorial editorial) {
		this(ISBN, titulo, edicion, idEditorial);
		this.editorial = editorial;
	}
	
	public Libro(String ISBN, String titulo, int edicion, int idEditorial, Editorial editorial, ArrayList<Autor> autores) {
		this(ISBN, titulo, edicion, idEditorial, editorial);
		this.autores = autores;
	}
	
	public Libro(String ISBN, String titulo, int edicion, int idEditorial, String idioma, String genero, int paginas, Date año) {
		this(ISBN, titulo, edicion, idEditorial);
		this.idioma = idioma;
		this.genero = genero;
		this.paginas = paginas;
		this.fechaPublicacion = año;
		
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

	/**
	 * @return the titulo
	 */
	public String getTitulo() {
		return titulo;
	}

	/**
	 * @param titulo the titulo to set
	 */
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	/**
	 * @return the editorial
	 */
	public Editorial getEditorial() {
		return editorial;
	}

	/**
	 * @param editorial the editorial to set
	 */
	public void setEditorial(Editorial editorial) {
		this.editorial = editorial;
	}

	/**
	 * @return the autores
	 */
	public ArrayList<Autor> getAutores() {
		return autores;
	}

	/**
	 * @param autores the autores to set
	 */
	public void setAutores(ArrayList<Autor> autores) {
		this.autores = autores;
	}
    
	public String getAutoresString() {
		StringBuilder autores = new StringBuilder();
		for (Autor autor: this.autores) {
			autores.append(autor.getApellido()).append(", ");
			autores.append(autor.getNombre()).append("; ");
		}
		return autores.toString();
	}
}
