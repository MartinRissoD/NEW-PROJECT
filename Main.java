package juegoDePreguntas;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;

public class Main {

	// HashMap para almacenar las puntuaciones de los usuarios
	private static HashMap<String, Integer> tablaPuntuaciones = new HashMap<>();
	// Scanner para leer la entrada del usuario
	private static Scanner scanner = new Scanner(System.in);
	// listad de preguntas que se van a utilizar en el juego
	private static List<Preguntas> listaPreguntas = new ArrayList<Preguntas>();

	public static void main(String[] args) {

		preCargarPreguntas();

		String nombreUsuario = "";
//		cargarHistorialPuntuaciones();
		// Variables para el menú y la opción seleccionada
		int opcion;
		boolean salir = false;
		// Menú principal
		System.out.print("Ingresa tu nombre para comenzar: ");
		nombreUsuario = scanner.nextLine();
		System.out.println("\nBienvenido/a " + nombreUsuario + "! \n ");
		System.out.println("Elige la operacion que desee realizar: \n ");
		System.out.println("1.- Jugar.");
		System.out.println("2.- Ver Historial.");
		System.out.println("3.- Crear nueva pregunta.");
		System.out.println("4.- Salir.");
		// lee un entero desde la entrada estándar (consola) y lo almacena en la
		// variable ´opcion´. esta línea se utiliza para obtener la opción del menú que
		// el usuario ha ingresado
		opcion = scanner.nextInt();

		// Obtener nombre usuario

		// Bucle principal del programa
		while (opcion != 4 && !salir) {
			switch (opcion) {
			case 1:
				System.out.println("Has seleccionado la opción Jugar.");
				jugar(nombreUsuario);
				System.out.println("2.- Para ver los puntajes");
				System.out.println("4.- Si deseas salir.");
				opcion = scanner.nextInt();

				break;
			case 2: {
				System.out.println("Has seleccionado la opción Ver Historial.");

				String historialPuntuaciones = leerTablaPuntuaciones("src/historial_puntuaciones.txt");
				System.out.println("Historial de Puntuaciones: \n" + historialPuntuaciones);

				System.out.println("1.- Para volver a jugar");
				System.out.println("4.- para salir.");
				opcion = scanner.nextInt();
			}
				break;
			case 3: {
				System.out.println("Has seleccionado la opción Crear Nueva Pregunta.\n");
				crearNuevaPregunta();
				System.out.println("La nueva pregunta ha sido creada y guardada.");
				System.out.println("1.- Para jugar");
				System.out.println("2.- Para ver los puntajes");
				System.out.println("4.- Si deseas salir.");
				opcion = scanner.nextInt();
			}
				break;
			case 4: {
				System.out.println("Has seleccionado la opción Salir.");
				scanner.close();
				System.exit(4); // Cierre del programa
			}

			default: {

				System.out.println("Opción inexistente");
				System.out.println("Seleccione una opcion del 1 al 4");
				break;

			}

			}

		}

	}

	// Método para cargar preguntas de ejemplo en la lista
	private static void preCargarPreguntas() {
		Preguntas pregunta1 = new Preguntas("Geografía", "¿Cuál es la capital de Francia?", "París", "Madrid", "Berlín",
				1);
		Preguntas pregunta2 = new Preguntas("Historia", "¿En qué año se descubrió América?", "1492", "1500", "1600", 1);
		Preguntas pregunta3 = new Preguntas("Deporte", "¿Que pais gano la copa del mundo en el año 1950?", "Belgica",
				"Uruguay", "Brasil", 2);
		Preguntas pregunta4 = new Preguntas("Historia", "¿En qué año se descubrió América?", "1492", "1500", "1600", 1);

		// Agregar preguntas a la lista
		listaPreguntas.add(pregunta1);
		listaPreguntas.add(pregunta2);
		listaPreguntas.add(pregunta3);
		listaPreguntas.add(pregunta4);
	}

	// Método para jugar el juego de preguntas
	private static void jugar(String nombreUsuario) {
		if (!tablaPuntuaciones.containsKey(nombreUsuario)) {
			tablaPuntuaciones.put(nombreUsuario, 0);
		}
		// Cargar preguntas desde archivo
		obtenerPreguntasDesdeArchivo();

		int preguntasCorrectas = 0;
		int maxPreguntas = 10; // Establecer el número máximo de preguntas a responder
		int contadorPreguntas = 1;

		// Recorre la lista de preguntas para preguntarle al usuario
		for (Preguntas pregunta : listaPreguntas) {
			// mostrar pregunta en consola
			presentarPregunta(pregunta, contadorPreguntas);
			// Solicitar respuesta al usuario
			int respuestaUsuario = scanner.nextInt();
			// Evaluar si la respuesta es correcta
			if (pregunta.verificarRespuesta(respuestaUsuario)) {
				System.out.println("¡Respuesta correcta! Sumas puntos.");
				preguntasCorrectas += 1;
				scanner.nextLine();

				tablaPuntuaciones.put(nombreUsuario, preguntasCorrectas);

				// Pausa de 1.5 segundo (puedes ajustar el tiempo según tu preferencia)
				try {
					Thread.sleep(1500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				// Comprobar si se han respondido todas las preguntas requeridas
				if (preguntasCorrectas == maxPreguntas) {
					System.out.println("¡Has respondido todas las preguntas correctamente!");
					break; // Salir del bucle después de responder todas las preguntas requeridas
				}

			} else {
				System.out.println(
						"Respuesta incorrecta. La respuesta correcta es la opcion: " + pregunta.getOpcionCorrecta());

				int puntosGenerados = tablaPuntuaciones.getOrDefault(nombreUsuario, 0);
				puntosGenerados = 0; // Puedes ajustar cómo quieres incrementar la puntuación
				// Actualizar la puntuación en el HashMap
				tablaPuntuaciones.put(nombreUsuario, puntosGenerados);
				try {
					Thread.sleep(1800);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

			if (contadorPreguntas < listaPreguntas.size()) {
				// Mostrar la puntuación actualizada
				System.out
						.println("Tu puntuación actual es: " + tablaPuntuaciones.getOrDefault(nombreUsuario, 0) + "\n");
			}

			contadorPreguntas++;

		}

		System.out.println("\nNo quedan mas preguntas. Resultado final: ");
		for (Map.Entry<String, Integer> entry : tablaPuntuaciones.entrySet()) {
			System.out.println(entry.getKey() + ": " + entry.getValue() + " puntos");
		}
		// Llamada al final del juego para guardar la puntuación
		guardarTablaPuntuaciones();

	}

	// Método para presentar la pregunta al usuario
	private static void presentarPregunta(Preguntas pregunta, int contadorPreguntas) {
		// Mostrar la información de cada pregunta en pantalla
		System.out.println("Pregunta " + contadorPreguntas);
		System.out.println("Tema: " + pregunta.getTema());
		System.out.println("Pregunta: " + pregunta.getPregunta());
		System.out.println("Opcion 1: " + pregunta.getRespuesta1());
		System.out.println("Opcion 2: " + pregunta.getRespuesta2());
		System.out.println("Opcion 3: " + pregunta.getRespuesta3());
		System.out.println("¿Cual es la opcion correcta? ");
	}

	// Método para crear y guardar una nueva pregunta
	private static void crearNuevaPregunta() {
		Scanner newScanner = new Scanner(System.in);

		System.out.println("Ingrese el tema de la nueva pregunta: ");
		String tema = newScanner.nextLine();

		System.out.println("Ingrese la pregunta: ");
		String pregunta = newScanner.nextLine();

		System.out.println("Ingrese la respuesta 1: ");
		String respuesta1 = newScanner.nextLine();

		System.out.println("Ingrese la respuesta 2: ");
		String respuesta2 = newScanner.nextLine();

		System.out.println("Ingrese la respuesta 3: ");
		String respuesta3 = newScanner.nextLine();

		System.out.println("Ingrese el numero de la opcion correcta: ");
		int opcionCorrecta = newScanner.nextInt();

		Preguntas nuevaPregunta = new Preguntas(tema, pregunta, respuesta1, respuesta2, respuesta3, opcionCorrecta);
		guardarPregunta(nuevaPregunta, "src/preguntas.txt");
		newScanner.close();
	}

	public static void guardarTablaPuntuaciones() {
		String nombreArchivo = "src/historial_puntuaciones.txt";

		try (FileWriter fichero = new FileWriter(nombreArchivo, true); PrintWriter writer = new PrintWriter(fichero)) {

			for (Map.Entry<String, Integer> entry : tablaPuntuaciones.entrySet()) {
				writer.println(entry.getKey() + ": " + entry.getValue() + " puntos");
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// lee archivo de texto, da formato a las preguntas y retorna un listado de
	// preguntas
	private static void obtenerPreguntasDesdeArchivo() {
		String data = "";
		String filePath = "src/preguntas.txt";

		// lee archivo de preguntas
		try {
			data = new String(Files.readAllBytes(Paths.get(filePath)));
		} catch (Exception e) {
			System.out.println("error: " + e.getMessage());
		}
		// cargado de matriz
		String filas[] = data.split(";");
		String col[] = filas[0].split(":");

		String tabla[][] = new String[filas.length][col.length];
		for (int i = 0; i < filas.length; i++) {
			col = filas[i].split(":");
			for (int j = 0; j < col.length; j++) {
				tabla[i][j] = col[j];
			}
		}
		for (int i = 1; i < tabla.length; i++) {
			listaPreguntas.add(new Preguntas(tabla[i][0], tabla[i][1], tabla[i][2], tabla[i][3], tabla[i][4],
					Integer.parseInt(tabla[i][5])));
		}
	}

	// recibe una pregunta y la carga en el archivo de preguntas con el formato
	// correcto
	private static void guardarPregunta(Preguntas pregunta, String filePath) {
		String nuevaPregunta = "";

		try (PrintWriter writer = new PrintWriter(new FileWriter(filePath, true))) {
			nuevaPregunta = ";" + pregunta.getTema() + ":" + pregunta.getPregunta() + ":" + pregunta.getRespuesta1()
					+ ":" + pregunta.getRespuesta2() + ":" + pregunta.getRespuesta3() + ":"
					+ pregunta.getOpcionCorrecta();
			writer.print(nuevaPregunta);
			writer.close();

		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Error al guardar la pregunta en el archivo: " + e.getMessage());
		}
	}

	// lee el archivo de texto de puntuaciones y devuelve la tabla de puntuaciones
	private static String leerTablaPuntuaciones(String nombreArchivo) {
		try {
			String datos = new String(Files.readAllBytes(Paths.get(nombreArchivo)));
			return datos;
		} catch (IOException e) {
			System.err.println("Error al leer la tabla de puntuaciones: " + e.getMessage());
			return null;
		}

	}

}