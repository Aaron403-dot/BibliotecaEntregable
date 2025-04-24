package model;

import java.time.LocalDate;

import enums.GeneroLibro;

public class Libro extends RecursoBiblioteca {
	
	private String autor;
	private LocalDate fechaDePublicacion;
	private GeneroLibro genero = GeneroLibro.COMEDIA;
	
	public Libro(long id, String titulo,String autor,LocalDate fechaDePublicacion,GeneroLibro genero,int existencias) {
		super(id, titulo,existencias);
		this.autor = autor;
		this.fechaDePublicacion = fechaDePublicacion;
		this.genero = genero;
		// TODO Auto-generated constructor stub
	}

	@Override
	public String descripcion() {
		// TODO Auto-generated method stub
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
