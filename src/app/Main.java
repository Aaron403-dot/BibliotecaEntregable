package app;

import java.io.File;
import java.io.FileWriter;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import Excepciones.NoPlanRangeException;
import enums.EstadosRecurso;
import enums.GeneroDePelicula;
import enums.GeneroLibro;
import enums.PlanMiembro;
import enums.TipoRevista;
import interfaces.Prestamista;
import model.*;

/**
 * clase main principal la cual tiene toda la logica de la app
 * @author Aarón del Cristo Suárez Suárez
 * */

public class Main implements Prestamista {
	/**
	 * metodo main que se ocupa de ejecutar la logica del programa
	 * @param args
	 * */
	
	
	public static void main(String[] args) {
		Main main = new Main();
		LocalDate fechaIntroductor = null;
		Map<Long, RecursoBiblioteca> recursos = main.starterRecursos(fechaIntroductor);
		Map<Long, Usuario> usuarios = main.straterUsuarios();
		long currentIdProduct = recursos.size();
		long currentIdUsuario = usuarios.size();
		Scanner sc = new Scanner(System.in);
		Usuario usuarioActual=main.menuAccesoUsuario(sc, usuarios);
		usuarioActual = main.menu(sc,usuarioActual,recursos);
		
		sc.close();
	}
	
	
	/***
	 * Este se ocupa de generar los usuarios de incio de prueba
	 * @return usuarios: la lista de los usuarios de esta hipotetica cuenta
	 * */
	
	private Map<Long, Usuario> straterUsuarios() {
		try {
		long currentIdUsuario = 0l;
		Map<Long, Usuario> usuarios = new HashMap<>();
		Usuario usuario1 = new Usuario(currentIdUsuario, "Pablo", false);
		currentIdUsuario++;
		Usuario usuario2 = new Usuario(currentIdUsuario, "Marta", false);
		usuario2.setAmonestado(true);
		usuario2.setMultaAmonestacion(23);
		currentIdUsuario++;
		Usuario usuario3 = new Usuario(currentIdUsuario, "Juana", true);
		usuario3.setPlan(PlanMiembro.GOLDEN);
		currentIdUsuario++;
		usuarios.put(usuario1.getId(), usuario1);
		usuarios.put(usuario2.getId(), usuario2);
		usuarios.put(usuario3.getId(), usuario3);
		
		for (Entry<Long, Usuario> usuario : usuarios.entrySet()) {
			File file = new File(usuario.getValue().getNombre()+".txt");
			FileWriter FW = new FileWriter(file);
			FW.write(usuario.toString());
			FW.close();
		}
		
		
		return usuarios;
		}catch(Exception e){
			System.err.println(e.getMessage());
			return null;
		}
	}

	/***
	 * Este se ocupa de generar los Recursos de incio de prueba
	 * @param fechaIntroductor: se ocupa de ser el dato de la fecha inicial
	 * */
	
	private Map<Long, RecursoBiblioteca> starterRecursos(LocalDate fechaIntroductor) {
		Map<Long, RecursoBiblioteca> recursos = new HashMap<>();
		long currentIdProduct = 0l;
		DVD dvd1 = new DVD(currentIdProduct,"Mi vecino totoro","Estudio Ghibli","Hayao Miyazaki",fechaIntroductor.parse("16 04 1988",DateTimeFormatter.ofPattern("dd MM yyyy")),86,GeneroDePelicula.INFANTIL,100);
		currentIdProduct++;
		DVD dvd2 = new DVD(currentIdProduct,"El último combate","Estudio TOEI","Mitsuo Hashimoto",fechaIntroductor.parse("10 04 1990",DateTimeFormatter.ofPattern("dd MM yyyy")),47,GeneroDePelicula.INFANTIL,550);
		currentIdProduct++;
		Revista revista1 = new Revista(currentIdProduct,"Hobbie Consolas",TipoRevista.HOBBIES,1,0);
		currentIdProduct++;
		revista1.setEstado(EstadosRecurso.SIN_EXISTENCIAS);
		Revista revista2 = new Revista(currentIdProduct,"Metal Magazine",TipoRevista.MUSICA,10,100);
		currentIdProduct++;
		Libro libro1 = new Libro(currentIdProduct,"El ingenioso hidalgo don Quixote de la Mancha","Miguel de Cervantes Saavedra",fechaIntroductor.parse("01 01 1605",DateTimeFormatter.ofPattern("dd MM yyyy")),GeneroLibro.COMEDIA,100);
		currentIdProduct++;
		Libro libro2 = new Libro(currentIdProduct, "Java Puzzlers" , "Joshua Bloch" , fechaIntroductor.parse("24 06 2005",DateTimeFormatter.ofPattern("dd MM yyyy")) , GeneroLibro.EDUCATIVO,100);
		currentIdProduct++;
		libro1.setEstado(EstadosRecurso.DESCATALOGADO);
		recursos.put(dvd1.getId(), dvd1);
		recursos.put(dvd2.getId(), dvd2);
		recursos.put(revista1.getId(), revista1);
		recursos.put(revista2.getId(), revista2);
		recursos.put(libro1.getId(), libro1);
		recursos.put(libro2.getId(), libro2);
		return recursos;
	}
	
	/**
	 * 
	 * Este menu pregunta a que usuario se le va a entregar los productos, actualmente esta limitado a los 3 usuarios de base
	 * @param sc: se ocupa de recibir los inputs
	 * @param usuarios: recibe el map de usuarios de la cuenta
	 * @return usuario: el usuario seleccionado
	 * */
	public Usuario menuAccesoUsuario(Scanner sc,Map<Long, Usuario> usuarios)
	{
		try {
			System.out.println("-------------------[BUENOS DIAS]----------------------");
			System.out.println("-----Actualmente quien esta usando la Aplicacion------");
			usuarios.forEach((ID,usuario) -> System.out.print("["+(ID+1)+"] " + usuario.getNombre()+ ", "));
			while (true) {
				switch (sc.nextLine()) {
				case "1":
					return usuarios.get(0l);
				case "2":
					return usuarios.get(1l);
				case "3":
					return usuarios.get(2l);
				default:
					System.out.println("introduzca un valor valido");
					break;
				}
			}
		} catch (Exception e) {
			System.err.println(e.getMessage());
			return usuarios.get(0l);
		}
		
	}
	
	/**
	 * Clase del menu principal ocupada de la selecion de las funciones de la biblioteca
	 * @param sc: se ocupa de recibir el input del usuario
	 * @param usuario: el usuario actualmente asignado
	 * @param recursos: el hashmap de recursos de la biblioteca
	 * @return usuario: el usuario tras a ejecucion del programa
	 * */
	
	
	public Usuario menu(Scanner sc,Usuario usuario,Map<Long, RecursoBiblioteca> recursos){
		try {
			boolean loopState=true;
			while (loopState) {
				System.out.println("que desea hacer hoy:");
				System.out.println("[A]lquilar, [D]evolver,[C]ambio,[B]aja,am[O]nestacion ,[S]alir");
				switch (sc.nextLine().toUpperCase()) {
				case "A":
					
					System.out.println("que deseas alquilar");
					boolean whileLoopState = true;
					
					for (Map.Entry<Long, RecursoBiblioteca> listaDeCatalogo : recursos.entrySet()) {
						Long key = listaDeCatalogo.getKey();
						RecursoBiblioteca val = listaDeCatalogo.getValue();
						System.out.println("["+key+"] "+ val.getTitulo() + " Existencias: " + val.getExistencias() + " tipo: " + val.getClass().getName());
					}
					
					while(whileLoopState){
						
						String confirmable = sc.nextLine();
						
						if(esNumero(confirmable) && !confirmable.isBlank()){
							
							long converter =Long.parseLong(confirmable);
							RecursoBiblioteca recurso = null;
							switch(recursos.get(converter).getClass().toString()){
							case "class model.Libro":
								recurso = new Libro((Libro) recursos.get(converter));
								break;
							case "class model.DVD":
								recurso = new DVD((DVD) recursos.get(converter));
								break;
							case "class model.Revista":
								recurso = new Revista((Revista) recursos.get(converter));
								break;
							default:
							}
							
							
							if(recursos.containsKey(converter)){
								
								if(recurso.getExistencias() > 0) {
									
									int existenciasActuales = recurso.getExistencias()-1;
									RecursoBiblioteca prestamo = null;
									prestamo = recurso.clone();
									recurso.setExistencias(existenciasActuales);
									recursos.put(recurso.getId(), recurso);
									usuario = prestar(prestamo, usuario);									
									whileLoopState = false;
									
								}else{
								
									System.out.println("actualmente no nos quedan existencias quieres reservarlo");
									System.out.println("[S]i ------------------------- [N]o");
									boolean confirmStuck=true;
									RecursoBiblioteca prestamo = null;
									prestamo = recurso.clone();
									while(confirmStuck){
										switch (sc.nextLine().toUpperCase()){
											case "S":
												usuario = prestar(prestamo, usuario);
												recursos.get(converter).setEstado(EstadosRecurso.PRESTADO);
												confirmStuck=false;
												whileLoopState = false;
												break;
											case "N":
												System.out.println("no se reservo el producto");
												confirmStuck=false;
												whileLoopState = false;
												break;
											default:
												System.out.println("introduzca un valor valido");
												break;
										}
									}	
								}
								
							}else{
								System.out.println("El numero del producto no existe");
							}
							
						}
					}
					
					
					break;
				case "D":
					
					if(usuario.getListaAlquiler().size()==0){System.out.println("No tienes peliculas a devolver");
						break;
						}
					
					System.out.println("que deseas Devolver");
					
					RecursoBiblioteca peliculaADevolver = null;
					
					boolean whileLoopState2 = true;
					
					for (Map.Entry<Long, RecursoBiblioteca> listaDeCatalogo : usuario.getListaAlquiler().entrySet()) {
						Long key = listaDeCatalogo.getKey();
						RecursoBiblioteca val = listaDeCatalogo.getValue();
						System.out.println("["+key+"] "+ val.getTitulo() + " tipo: " + val.getClass().getName());
					}
					while(whileLoopState2){
						String confirmable = sc.nextLine();
						
						if(esNumero(confirmable)){
							
							if(usuario.getListaAlquiler().containsKey(Long.parseLong(confirmable))){
								
								peliculaADevolver = usuario.getListaAlquiler().get(Long.parseLong(confirmable)).clone();
								if(usuario.getListaAlquiler().get(Long.parseLong(confirmable)).getEstado() == EstadosRecurso.RESERVADO && recursos.get(Long.parseLong(confirmable)).getExistencias() <= 0){
									usuario = devolver(peliculaADevolver,usuario);
									
									recursos.get(Long.parseLong(confirmable)).setEstado(EstadosRecurso.SIN_EXISTENCIAS);
									
									whileLoopState2 = false;
								}
								usuario = devolver(peliculaADevolver,usuario);
								
								recursos.get(Long.parseLong(confirmable)).setExistencias(recursos.get(Long.parseLong(confirmable)).getExistencias()+1);
								
								whileLoopState2 = false;
								
							}else{
								
								System.out.println("El numero del producto no existe");
							
							}
							
						}
					}
					break;
				case "C":
					if (!usuario.isMiembro()) {
						usuario.setMiembro(true);
					}
					usuario = cambioPlan(usuario, sc);
					break;
				case "B":
					if(usuario.isMiembro()){
						usuario = darseDeBaja(usuario, sc);
					}else{
						System.out.println("Actualmente no es miembro");
					}
					break;
				case "O":
					if(usuario.isAmonestado())
					{
						pagarAmonestacion(usuario);
					}
					else {
						System.out.println("Que bien no tienes ningun tipo de amonestacion");
					}
					break;
				case "S":
					loopState = false;
					break;

				default:
					System.err.println("Introduzca un valor valido");
					break;
				}
			}
			return usuario;
		}		
		catch (Exception e) {
			System.err.println(e.getMessage());
			return usuario;
		}
		
	}
	
	/**
	 * Es el metodo principal del prestamo de medios
	 * @param recurso: usando la clase padre puede recibir cualquiera de las clases hijos
	 * @param usuario: el usuario que va a recibir el libro
	 * @return usuario: devuelve el añadido al map del usuario de libros rentados
	 * */
	
	@Override
	public Usuario prestar(RecursoBiblioteca recurso, Usuario usuario) {
		try {
			
			
			if (recurso.getEstado() == EstadosRecurso.DESCATALOGADO && usuario.getPlan().compareTo(PlanMiembro.GOLDEN) < 0) {
				throw new NoPlanRangeException(NoPlanRangeException(usuario));
			}
			else{
				System.out.println("recuerde que si es un usuario golden puede alquilar productos por un extra monetario");
			}
			if(usuario.isAmonestado())
			{
				throw new NoPlanRangeException(NoPlanRangeException(usuario));
			}
			
			int cantidadUsuario=0;
			
			if(usuario.getListaAlquiler().containsKey(recurso.getId())){
				cantidadUsuario = usuario.getListaAlquiler().get(recurso.getId()).getExistencias();
			}
			
			Map<Long, RecursoBiblioteca> recursos = new HashMap<>();
			recursos = usuario.getListaAlquiler();
			recurso.setFechaDeAlquiler(LocalDate.now());
			
			LocalDate fechaDevolucion = LocalDate.now();
			fechaDevolucion.plusDays(15);
			
			recurso.setFechaDeDevolucion(fechaDevolucion);
			
			if(recurso.getExistencias() == 0)
			{
				recurso.setEstado(EstadosRecurso.RESERVADO);
			}
			
			else if(recurso.getExistencias()>0 && usuario.getListaAlquiler().containsKey(recurso.getId())){
				cantidadUsuario++;
				recurso.setExistencias(cantidadUsuario);
				recursos.put(recurso.getId(),recurso);
				usuario.setListaAlquiler(recursos);
				return usuario;
			}
			
			recurso.setExistencias(1);
			usuario.getListaAlquiler().put(recurso.getId(), recurso);
			return usuario;
		} catch (NoPlanRangeException e) {
			System.err.println(e.getMessage());
			return usuario;
		}
		
	}
	
	/**
	 * Es el metodo principal para la devolucion de medios
	 * @param recurso: usando la clase padre puede recibir cualquiera de las clases hijos
	 * @param usuario: el usuario que va a devolver el producto
	 * @return usuario: devuelve el quitado al map del usuario de productos rentados
	 * */
	
	@Override
	public Usuario devolver(RecursoBiblioteca recurso, Usuario usuario) {
		Map<Long, RecursoBiblioteca> recursos = new HashMap<>();
		recursos = usuario.getListaAlquiler();
		LocalDate fechaDevolucion = LocalDate.now();
		int existenciasRecursos = recurso.getExistencias();
		if(recurso.getFechaDeDevolucion().isBefore(fechaDevolucion))
		{
			usuario.setAmonestado(true);
		}
		
		recurso.setFechaDeDevolucion(fechaDevolucion);
		if(existenciasRecursos <= 1){
			recursos.remove(recurso.getId(),recurso);
		}
		else{
			recurso.setExistencias(existenciasRecursos-1);
			recursos.put(recurso.getId(), recurso);
		}
		usuario.setListaAlquiler(recursos);
		return usuario;
	}

	
	/**
	 * Este es el metodo que se ocupa del cambio de planes sea a uno de mayor clase a uno de menor clase
	 * @param usuario: el usuario que recibe el cambio de plan
	 * @param sc: el input necesario para saber el plan buscado
	 * @return usuario: el resultado el cambio se establece
	 * */
	
	@Override
	public Usuario cambioPlan(Usuario usuario,Scanner sc) {
		try {
			System.out.println("a que plan desea cambiar");
			for (PlanMiembro planMiembro : PlanMiembro.values()) {
					System.out.println("["+(planMiembro.ordinal()+1)+"] "+planMiembro.name());
				}
			boolean switchLoop = true;
			while(switchLoop){
				String option = sc.nextLine();
				if(!esNumero(option)){
					option = String.valueOf(0);
				}
				switch (Integer.parseInt(option)) {
				case 1:
					if(usuario.getPlan().ordinal() == Integer.parseInt(option)){
						System.out.println("Introduzca un plan distinto");
						break;
					}
					usuario.setPlan(PlanMiembro.BASICO);
					switchLoop=false;
					break;
				case 2:
					if(usuario.getPlan().ordinal() == Integer.parseInt(option)){
						System.out.println("Introduzca un plan distinto");
						break;
					}
					usuario.setPlan(PlanMiembro.DELUX);
					switchLoop=false;
					break;
				case 3:
					if(usuario.getPlan().ordinal() == Integer.parseInt(option)){
						System.out.println("Introduzca un plan distinto");
						break;
					}
					usuario.setPlan(PlanMiembro.GOLDEN);
					switchLoop=false;
					break;
				default:
					System.out.println("Introduzca un valor valido");
					break;
				}
			}
			return usuario;
		} catch (Exception e) {
			System.err.println(e.getMessage());
			return usuario;
		}
		
	}
	
	/**
	 * Metodo simple que da de baja al usuario
	 * @param usuario: el usuario el cual desea darse de baja
	 * @param sc: para el input de confirmacion
	 * @return usuario: actualizacion del objeto usuario
	 * */
	
	@Override
	public Usuario darseDeBaja(Usuario usuario,Scanner sc) {
		System.out.println("Esta apunto de darse de baja de nuestros planes para miembros desea continuar?");
		boolean loopBaja = true;
		System.out.println("[SI]------------------------------[NO]");
		while (loopBaja) {
			switch (sc.nextLine().toUpperCase()) {
			case "SI":
				System.out.println("Esta Seguro?");
				System.out.println("[SI]-----------------------[NO]");
				switch (sc.nextLine().toUpperCase()) {
				case "SI":
						usuario.setMiembro(false);
						usuario.setPlan(PlanMiembro.BASICO);
						loopBaja = false;
						return usuario;
				case "NO":
						loopBaja = false;
					break;
				default:
					System.out.println("introduzca un valor valido");
					break;
				}
				break;
			case "NO":
					loopBaja = false;
				break;
			default:
				System.out.println("introduzca un valor valido");
				break;
			}
			
		}
		return usuario;
	}

	/**
	 * Metodo simple que declara la multa de la amonestacion
	 * @param usuario: el usuario amonestado al que se le va a quitar la amonestacion
	 * @return usuario: actualizacion del objeto usuario sin la amonestacion
	 * */
	
	@Override
	public Usuario pagarAmonestacion(Usuario usuario) {
		System.out.println("va a pagar la amonestacion de " + usuario.getMultaAmonestacion() + "Euros");
		usuario.setMultaAmonestacion(0);
		usuario.setAmonestado(false);
		return usuario;
	}
	
	/**
	 * Metodo que retorna los mensajes de excepcion del programa
	 * @param Usuario: el usuario para detectar la causa de la excepcion
	 * @return String: el mensaje de error
	 * */
	private String NoPlanRangeException(Usuario usuario){
		try {
			
            if (usuario==null) {
                return "Por Algun Motivo el Usuario NO existe";
            }
            
            if (usuario.isAmonestado()){
            	return "Actualmente No tiene permitido el alquiler de productos, Page la amonestacion y se le permitira el aquiler de nuevo";
            }
            
            if (usuario.getPlan() != PlanMiembro.GOLDEN) {
                return "Actualmente el libro esta descatalogado";
            }
            
            return "";
        } catch (NullPointerException e) {
            return e.getMessage();
        }
	}
	
	
	/**
	 * metodo interno para la deteccion de un String para saber si este es un Integer
	 * @param String: el valor <b>Supuestamente</b> numerico
	 * @return boolean: Si es un Numero <b>TRUE</b> si no es un numero <b>FALSE</b>
	 * */
	private static boolean esNumero(String NumOLet)
    {
        //Declara la posicion del Analicis de checkeo
        ParsePosition Lugar = new ParsePosition(0);
        //saca la instalcia del formato de numero y analiza el lugar
        NumberFormat.getInstance().parse(NumOLet,Lugar);
        //Positivo si el tamaño del lugar es igual a la posicion actual del analizador
        return NumOLet.length() == Lugar.getIndex();
    }

	
}
