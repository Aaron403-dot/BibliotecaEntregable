package model;

import java.util.Map;
import java.util.HashMap;

import enums.PlanMiembro;

/**
 * <H1>Clase usuario</H1>
 * Es La clase Para los Objetos usuarios
 * @author Aaron del Cristo Suárez Suárez
 * */

public class Usuario {
	
	
	
	private long id;
	private String nombre;
	private boolean miembro;
	private PlanMiembro plan = PlanMiembro.BASICO;
	private Map<Long, RecursoBiblioteca> listaAlquiler = new HashMap<Long, RecursoBiblioteca>();
	private boolean amonestado = false;
	private int multaAmonestacion = 0;

	public Usuario(long i,String n,boolean m) {
		id = i;
		nombre = n;
		miembro = m;
	}
	
	public long getId() {
		return id;
	}

	public String getNombre() {
		return nombre;
	}

	public boolean isMiembro() {
		return miembro;
	}

	public PlanMiembro getPlan() {
		return plan;
	}
	
	public void setPlan(PlanMiembro plan) {
		this.plan = plan;
	}
	
	public void modificarPlan(PlanMiembro plan) {
		this.plan = plan;
	}
	
	public void setMiembro(boolean miembro) {
		this.miembro = miembro;
	}

	public Map<Long, RecursoBiblioteca> getListaAlquiler() {
		return listaAlquiler;
	}

	public void setListaAlquiler(Map<Long, RecursoBiblioteca> listaAlquiler) {
		this.listaAlquiler = listaAlquiler;
	}
	
	public boolean isAmonestado() {
		return amonestado;
	}

	public void setAmonestado(boolean amonestado) {
		this.amonestado = amonestado;
	}

	public int getMultaAmonestacion() {
		return multaAmonestacion;
	}

	public void setMultaAmonestacion(int multaAmonestacion) {
		this.multaAmonestacion = multaAmonestacion;
	}

	@Override
	public String toString() {
		return "Usuario [id=" + id + ", nombre=" + nombre + ", miembro=" + miembro + ", plan=" + plan
				+"]";
	}
	
	
	
	

}
