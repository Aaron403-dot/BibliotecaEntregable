package model;

import java.time.LocalDate;
import java.util.Objects;

import enums.EstadosRecurso;

/**
 * <H1>Clase abstrcta de Recursos de Biblioteca</H1>
 * Es La clase de la cual heredaran las otras subclasse para genera la base de los objetos
 * @author Aaron del Cristo Suárez Suárez
 * */

public abstract class RecursoBiblioteca implements Cloneable{
	
	long id;
	String titulo;
	int existencias = 0;
	EstadosRecurso estado = EstadosRecurso.DISPONIBLE;
	LocalDate fechaDeAlquiler;
	LocalDate fechaDeDevolucion;
	
	public RecursoBiblioteca(long id, String titulo,int existencias) {
		this.id = id;
		this.titulo = titulo;
		this.existencias = existencias;
	}
	
	public RecursoBiblioteca(RecursoBiblioteca recursos){
		this.id = recursos.id;
		this.titulo = recursos.titulo;
		this.existencias = recursos.existencias;
		this.estado = recursos.estado;
		this.fechaDeAlquiler = recursos.fechaDeAlquiler;
		this.fechaDeDevolucion = recursos.fechaDeDevolucion;
	}
	
	
	public long getId() {
		return id;
	}
	
	public String getTitulo() {
		return titulo;
	}
	
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	
	public EstadosRecurso getEstado() {
		return estado;
	}
	
	public void setEstado(EstadosRecurso estado) {
		this.estado = estado;
	}
	
	public LocalDate getFechaDeAlquiler() {
		return fechaDeAlquiler;
	}

	public LocalDate getFechaDeDevolucion() {
		return fechaDeDevolucion;
	}

	public void setFechaDeAlquiler(LocalDate fechaDeAlquiler) {
		this.fechaDeAlquiler = fechaDeAlquiler;
	}

	public void setFechaDeDevolucion(LocalDate fechaDeDevolucion) {
		this.fechaDeDevolucion = fechaDeDevolucion;
	}

	public int getExistencias() {
		return existencias;
	}

	public void setExistencias(int existencias) {
		this.existencias = existencias;
	}

	public abstract String descripcion();

	@Override
	public int hashCode() {
		return Objects.hash(estado, existencias, fechaDeAlquiler, fechaDeDevolucion, id, titulo);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RecursoBiblioteca other = (RecursoBiblioteca) obj;
		return estado == other.estado && existencias == other.existencias
				&& Objects.equals(fechaDeAlquiler, other.fechaDeAlquiler)
				&& Objects.equals(fechaDeDevolucion, other.fechaDeDevolucion) && id == other.id
				&& Objects.equals(titulo, other.titulo);
	}
	
	
	// Método para clonar el objeto
    @Override
	public RecursoBiblioteca clone() throws CloneNotSupportedException {
        return (RecursoBiblioteca) super.clone();
    }
	

}
