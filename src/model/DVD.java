package model;

import java.time.LocalDate;

import enums.GeneroDePelicula;

/**
 * Clase Hija de Recursos de Biblioteca (DVD)
 * Es La clase de la cual hereda de la clase RecursosBiblioteca
 * @author Aaron del Cristo Suárez Suárez
 * */

public class DVD extends RecursoBiblioteca{
	
	private String studio;
	private String director;
	private LocalDate fechaDeSalida;
	private int duracionMinutos;
	private GeneroDePelicula genero = GeneroDePelicula.COMEDIA;
	
	public DVD(long id, String titulo,String studio,String director,LocalDate fechaDeSalida,int duracionMinutos,GeneroDePelicula genero,int existencias) {
		super(id, titulo,existencias);
		this.studio = studio;
		this.director=director;
		this.fechaDeSalida=fechaDeSalida;
		this.duracionMinutos=duracionMinutos;
		this.genero=genero;
	}
	
	
	

	public DVD(DVD recursos) {
		super(recursos);
		this.studio = recursos.studio;
		this.director = recursos.director;
		this.fechaDeSalida = recursos.fechaDeSalida;
		this.duracionMinutos = recursos.duracionMinutos;
		this.genero = recursos.genero;
	}




	public String getStudio() {
		return studio;
	}



	public String getDirector() {
		return director;
	}



	public LocalDate getFechaDeSalida() {
		return fechaDeSalida;
	}



	public int getDuracionMinutos() {
		return duracionMinutos;
	}



	public GeneroDePelicula getGenero() {
		return genero;
	}



	public void setStudio(String studio) {
		this.studio = studio;
	}



	public void setDirector(String director) {
		this.director = director;
	}



	public void setFechaDeSalida(LocalDate fechaDeSalida) {
		this.fechaDeSalida = fechaDeSalida;
	}



	public void setDuracionMinutos(int duracionMinutos) {
		this.duracionMinutos = duracionMinutos;
	}



	public void setGenero(GeneroDePelicula genero) {
		this.genero = genero;
	}



	@Override
	public String descripcion() {
		String resumen;
		resumen = "Titulo: " + getTitulo() + "\n Dirigida por: " + getDirector() +  "\n del estudio: " + getStudio() + "\n fue lanzada el: " + getFechaDeSalida().toString() + "\n Dura: " + getDuracionMinutos() + "\n es del genero: " + getGenero().name();
		return resumen;
	}
	
}
