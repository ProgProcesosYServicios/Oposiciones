package es.tessier.comunicaciones.notas;

import java.net.*;
import java.io.*;

public class ClienteTCP extends Thread {
	private int puerto;
	private String IP;
	private BufferedReader entrada;
	private PrintWriter salida;
	private BufferedReader stdIn;
	private Socket socketCliente;
	private String mensaje;

	ClienteTCP() {
		this(4444, "127.0.0.1");
	}

	ClienteTCP(int puerto, String IP) {
		this.puerto = puerto;
		this.IP = IP;

	}

	@Override
	public void run() {
		System.out.println("Cliente Iniciado");

		// Declaramos un bloque try y catch para controlar la ejecución del subprograma
		try {

			// Instanciamos un socket con la dirección del destino y el
			// puerto que vamos a utilizar para la comunicación
			socketCliente = new Socket(IP, puerto);
			System.out.println("Cliente: escuchando " + socketCliente);

			// Obtenemos el canal de entrada
		    entrada = new BufferedReader(new InputStreamReader(socketCliente.getInputStream()));

			// Obtenemos el canal de salida
			salida = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socketCliente.getOutputStream())), true);

			stdIn = new BufferedReader(new InputStreamReader(System.in));

			// El programa cliente no analiza los mensajes enviados por el
			// usario, simplemente los reenvía al servidor hasta que este
			// se despide con "Adios"

			while (true) {
				// Leo la entrada del usuario
				System.out.println("mensaje para el servidor");
				mensaje = stdIn.readLine();
				// La envia al servidor
				salida.println(mensaje);
				// Envía a la salida estándar la respuesta del servidor
				mensaje = entrada.readLine();
				System.out.println("Respuesta servidor: " + mensaje);
				// Si es "Adios" es que finaliza la comunicación
				if (mensaje.toUpperCase().contains("ADIOS")) 
					break;
			}
		} catch (IOException e) {
			System.err.println("No puede establer canales de E/S para la conexión");
			System.exit(-1);
		} finally {
			try {
				socketCliente.close();
				entrada.close();
				salida.close();
				System.err.println("Cliente finalizado");

			} catch (IOException e) {
			}
		}
	}
}