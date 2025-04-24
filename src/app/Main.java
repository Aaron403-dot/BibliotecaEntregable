package app;

import java.awt.Menu;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import Excepciones.NoPlanRangeException;
import enums.EstadosRecurso;
import enums.GeneroDePelicula;
import enums.GeneroLibro;
import enums.PlanMiembro;
import enums.TipoRevista;
import interfaces.Prestamista;
import model.*;

public class Main implements Prestamista {
	
	//Plan de realizacion:
	/*
	 * el usuario puede tener Recursos guardados (V)
	 * el usuario puede reservar por un precio extra (excluido el dinero) productos descatalogados si es GOLDEN (V ~)
	 * si un producto esta sin existencias se puede reservar
	 * si un usuario esta amonestado no puede alquilar un producto (V)
	 * los datos del usuario se guardan en un documento externo
	 * puede haber varias unidades de un mismo producto (V)
	 * los planes de usuario o el añadir o quitar de la lista de un usuario un producto (V)
	 * 
	 * 
	 * TODO:
	 * Generar los diagramas
	 * Generar metodos necesarios para uso correcto (~)
	 * 
	 * 
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
	 * @return HashMap<Long, Usuarios> la lista de los usuarios de esta hipotetica cuenta
	 * */
	private Map<Long, Usuario> straterUsuarios() {
		long currentIdUsuario = 0l;
		Map<Long, Usuario> usuarios = new HashMap<>();
		Usuario usuario1 = new Usuario(currentIdUsuario, "Pablo", false);
		currentIdUsuario++;
		Usuario usuario2 = new Usuario(currentIdUsuario, "Marta", false);
		usuario2.setAmonestado(true);
		currentIdUsuario++;
		Usuario usuario3 = new Usuario(currentIdUsuario, "Juana", true);
		usuario3.setPlan(PlanMiembro.GOLDEN);
		currentIdUsuario++;
		usuarios.put(usuario1.getId(), usuario1);
		usuarios.put(usuario2.getId(), usuario2);
		usuarios.put(usuario3.getId(), usuario3);
		return usuarios;
	}

	/***
	 * Este se ocupa de generar los Recursos de incio de prueba
	 * @param LocalDate: se ocupa de ser el dato de la fecha inicial
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
	 * @param Scanner: se ocupa de recibir los inputs
	 * @param Hashmap<Long, Usuario>: recibe el map de usuarios de la cuenta
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
	
	public Usuario menu(Scanner sc,Usuario usuario,Map<Long, RecursoBiblioteca> recursos){
		try {
			boolean loopState=true;
			while (loopState) {
				System.out.println("que desea hacer hoy:");
				System.out.println("[A]lquilar, [D]evolver,[C]ambio,[B]aja,am[O]nestacion ,[S]alir");
				switch (sc.nextLine().toUpperCase()) {
				case "A":
					System.out.println("que deseas alquilar");
					RecursoBiblioteca peliculaSeleccionada;
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
							RecursoBiblioteca recurso = recursos.get(converter);
							if(recursos.containsKey(converter) && recurso.getExistencias() > 0){
								usuario = prestar(recurso, usuario);
								recursos.get(converter).setExistencias(recursos.get(converter).getExistencias()-1);
								whileLoopState = false;
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
					RecursoBiblioteca peliculaADevolver;
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
								usuario = devolver(usuario.getListaAlquiler().get(Long.parseLong(confirmable)),usuario);
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
	 * @param RecursoBiblioteca: usando la clase padre puede recibir cualquiera de las clases hijos
	 * @param Usuario: el usuario que va a recibir el libro
	 * @return Usuario: devuelve el añadido al map del usuario de libros rentados
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
			
			Map<Long, RecursoBiblioteca> recursos = new HashMap<>();
			recursos = usuario.getListaAlquiler();
			recurso.setFechaDeAlquiler(LocalDate.now());
			LocalDate fechaDevolucion = LocalDate.now();
			fechaDevolucion.plusDays(15);
			recurso.setFechaDeDevolucion(fechaDevolucion);
			recursos.put(recurso.getId(),recurso);
			usuario.setListaAlquiler(recursos);
			return usuario;
		} catch (NoPlanRangeException e) {
			System.err.println(e.getMessage());
			return usuario;
		}
		
	}
	
	/**
	 * Es el metodo principal para la devolucion de medios
	 * @param RecursoBiblioteca: usando la clase padre puede recibir cualquiera de las clases hijos
	 * @param Usuario: el usuario que va a devolver el producto
	 * @return Usuario: devuelve el quitado al map del usuario de productos rentados
	 * */
	
	@Override
	public Usuario devolver(RecursoBiblioteca recurso, Usuario usuario) {
		Map<Long, RecursoBiblioteca> recursos = new HashMap<>();
		recursos = usuario.getListaAlquiler();
		LocalDate fechaDevolucion = LocalDate.now();
		if(recurso.getFechaDeDevolucion().isBefore(fechaDevolucion))
		{
			usuario.setAmonestado(true);
		}
		recurso.setFechaDeDevolucion(fechaDevolucion);
		recursos.remove(recurso.getId(),recurso);
		usuario.setListaAlquiler(recursos);
		return usuario;
	}

	
	/**
	 * Este es el metodo que se ocupa del cambio de planes sea a uno de mayor clase a uno de menor clase
	 * @param Usuario: el usuario que recibe el cambio de plan
	 * @param Scanner: el input necesario para saber el plan buscado
	 * @return Usuario: el resultado el cambio se establece
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
	 * @param Usuario: el usuario el cual desea darse de baja
	 * @param Scanner: para el input de confirmacion
	 * @return Usuario: actualizacion del objeto usuario
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
	 * @param Usuario: el usuario amonestado al que se le va a quitar la amonestacion
	 * @return Usuario: actualizacion del objeto usuario sin la amonestacion
	 * */
	
	@Override
	public Usuario pagarAmonestacion(Usuario usuario) {
		System.out.println("va a pagar la amonestacion de " + usuario.getMultaAmonestacion());
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
