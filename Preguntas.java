package juegoDePreguntas;


import java.util.List;

public class Preguntas {

	// Lista que contendrá instancias de la clase Preguntas
	private List<Preguntas> listaPreguntas;

	// Atributos de una pregunta individual
	private String tema;
	private String pregunta;
	private String respuesta1;
	private String respuesta2;
	private String respuesta3;
	private int opcionCorrecta;


	// Constructor para crear una pregunta individual
	public Preguntas(String tema, String pregunta, String respuesta1, String respuesta2,
			String respuesta3, int opcionCorrecta) {
		this.tema = tema;
		this.pregunta = pregunta;
		this.respuesta1 = respuesta1;
		this.respuesta2 = respuesta2;
		this.respuesta3 = respuesta3;
		this.opcionCorrecta = opcionCorrecta;
	}
	
	// Método para obtener la lista de preguntas
	public List<Preguntas> getListaPreguntas() {
		return this.listaPreguntas;
	}

	// Método para obtener el tema de la pregunta
	public String getTema() {
		return this.tema;
	}
	// Método para obtener el texto de la pregunta
	public String getPregunta() {
		return this.pregunta;
	}
	// Método para obtener la respuesta 1
	public String getRespuesta1() {
		return this.respuesta1;
	}
	// Método para obtener la respuesta 2
	public String getRespuesta2() {
		return this.respuesta2;
	}
	// Método para obtener la respuesta 3
	public String getRespuesta3() {
		return this.respuesta3;
	}
	// Método para obtener la opcion correcta
	public int getOpcionCorrecta() {
		return this.opcionCorrecta;
	}

	// Método para verificar si la respuesta dada por el usuario es correcta
	public boolean verificarRespuesta(int opcionSeleccionada) {
		// La opción seleccionada por el usuario es correcta si coincido con el numero de respuesta correcta
		return opcionSeleccionada == this.opcionCorrecta;
	}

}
