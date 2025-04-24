package model;

import java.time.LocalDate;

import enums.GeneroLibro;

/**
 * <H1>Clase Hija de Recursos de Biblioteca (Libro)</H1>
 * Es La clase de la cual hereda de la clase RecursosBiblioteca
 * @author Aaron del Cristo Suárez Suárez
 * */

public class Libro extends RecursoBiblioteca{
	
	private String autor;
	private LocalDate fechaDePublicacion;
	private GeneroLibro genero = GeneroLibro.COMEDIA;
	
	public Libro(long id, String titulo,String autor,LocalDate fechaDePublicacion,GeneroLibro genero,int existencias) {
		super(id, titulo,existencias);
		this.autor = autor;
		this.fechaDePublicacion = fechaDePublicacion;
		this.genero = genero;
	}

	
	
	public Libro(Libro recursos) {
		super(recursos);
		this.autor= recursos.autor;
		this.fechaDePublicacion = recursos.fechaDePublicacion;
		this.genero = recursos.genero;
	}



	@Override
	public String descripcion() {
		String resumen;
		resumen = "El libro se llama: " + getTitulo() + "\n fue escrito por: " + getAutor() + "\n es del Genero: " + getGenero().name() + "\n fue escrito el: " + getFechaDePublicacion().toString();
		return resumen;
	}

	public String getAutor() {
		return autor;
	}

	public LocalDate getFechaDePublicacion() {
		return fechaDePublicacion;
	}

	public GeneroLibro getGenero() {
		return genero;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public void setFechaDePublicacion(LocalDate fechaDePublicacion) {
		this.fechaDePublicacion = fechaDePublicacion;
	}

	public void setGenero(GeneroLibro genero) {
		this.genero = genero;
	}
	

}
