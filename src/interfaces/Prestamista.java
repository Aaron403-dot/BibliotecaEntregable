package interfaces;

import java.util.Scanner;

import enums.PlanMiembro;
import model.*;

/**
 * Interfaz Prestamista
 * Es La interfaz de la cual se heredaran los metodos de acciones de la APP
 * @author Aaron del Cristo Suárez Suárez
 * */

public interface Prestamista {
	/**
	 * Interfaz de prestar
	 * @param recurso: usando la clase padre puede recibir cualquiera de las clases hijos
	 * @param usuario: el usuario que va a recibir el prodcto
	 * @return Usuario: devuelve el añadido al map del usuario de libros rentados
	 * */
	Usuario prestar(RecursoBiblioteca recurso, Usuario usuario);
	/**
	 * interfaz de devolver
	 * @param recurso: usando la clase padre puede recibir cualquiera de las clases hijos en este caso para la devolucion
	 * @param usuario: el usuario que va a devolver el producto si lo tiene
	 * @return Usuario: devuelve el Usuario con el cambio al map
	 * */
	Usuario devolver(RecursoBiblioteca recurso, Usuario usuario);
	/**
	 * interfaz de cambio de plan de membresia
	 * @param usuario: el usuario que va a recibir el cambio
	 * @param sc: el input para la selecion de plan
	 * @return Usuario: el cambio realizado al usaurio
	 * */
	Usuario cambioPlan(Usuario usuario,Scanner sc);
	/**
	 * interfaz de Baja de la membresia
	 * @param usuario: el usuario que va a darse de baja
	 * @param sc: el input para la confirmacion o desconfirmacion de la baja
	 * @return Usuario: el cambio realizado al usaurio
	 * */
	
	Usuario darseDeBaja(Usuario usuario,Scanner sc);
	
	/**
	 * Interfaz de pago de amonestacion
	 * @param usuario: el usuario que va a pagar la amonestacion
	 * @return Usuario: el cambio realizado al usaurio
	 * */
	
	Usuario pagarAmonestacion(Usuario usuario);
}
