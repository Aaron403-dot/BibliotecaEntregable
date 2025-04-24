package model;

import java.time.LocalDate;

import enums.EstadosRecurso;

public abstract class RecursoBiblioteca {
	
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
	

}
