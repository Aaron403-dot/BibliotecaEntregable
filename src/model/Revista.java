package model;

import enums.TipoRevista;

/**
 * <H1>Clase Hija de Recursos de Biblioteca (Revista)</H1>
 * Es La clase de la cual hereda de la clase RecursosBiblioteca
 * @author Aaron del Cristo Suárez Suárez
 * */

public class Revista extends RecursoBiblioteca{
	
	private TipoRevista genero = TipoRevista.TECNOLOGIA;
	private int numeroEdicion;
	
	
	public Revista(long id, String titulo,TipoRevista genero,int numeroEdicion,int existencias) {
		super(id, titulo,existencias);
		this.genero = genero;
		this.numeroEdicion = numeroEdicion;
	}
	
	
	
	
	public Revista(Revista recursos) {
		super(recursos);
		this.genero = recursos.genero;
		this.numeroEdicion = recursos.numeroEdicion;
	}




	public TipoRevista getGenero() {
		return genero;
	}



	public int getNumeroEdicion() {
		return numeroEdicion;
	}



	public void setGenero(TipoRevista genero) {
		this.genero = genero;
	}


	public void setNumeroEdicion(int numeroEdicion) {
		this.numeroEdicion = numeroEdicion;
	}



	@Override
	public String descripcion() {
		String resumen;
		resumen = "El titulo: " + getTitulo() + "\n del genero: " + getGenero().name() + "\n Numero de edicion: " + getNumeroEdicion();
		return resumen;
	}
}
