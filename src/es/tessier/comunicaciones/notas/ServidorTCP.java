package es.tessier.comunicaciones.notas;

import java.net.*;
import java.util.HashMap;
import java.util.Map;
import java.io.*;

public class ServidorTCP extends Thread {

	private int puerto;
	private ServerSocket socketServidor;
	private Socket socketCliente;
	private BufferedReader entrada;
	private PrintWriter salida;

	Map<String, Integer> notas = new HashMap<String, Integer>();

	ServidorTCP() {
		this(4444);
	}

	ServidorTCP(int puerto) {
		notas.put("712345678", 5);
		notas.put("654321098", 8);
		notas.put("567890123", 10);

		this.puerto = puerto;
		try {
			socketServidor = new ServerSocket(puerto);
		} catch (IOException e) {
			System.err.println("No puede escuchar en el puerto: " + puerto);
			System.exit(-1);
		}
	}

	@Override
	public void run() {
		// Código a ejecutar por el hilo
		System.err.println("\tEscuchando en el puerto " + puerto);
		try {

			socketCliente = socketServidor.accept();
			System.err.println("\t:Conexión aceptada: " + socketCliente);

			// Establece canal de entrada
			entrada = new BufferedReader(new InputStreamReader(socketCliente.getInputStream()));
			// Establece canal de salida
			salida = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socketCliente.getOutputStream())), true);

			// Hace eco de lo que le proporciona el cliente, hasta que recibe "Adios"
			while (true) {
				System.err.println("\tEsperando alumno");
				String alumno = entrada.readLine();
				if (notas.containsKey(alumno)) {
					System.err.println("\tAlumno: " + alumno);
					salida.println("Conectado con el alumno " + alumno);
					String orden = entrada.readLine();
					while (!orden.toUpperCase().contains("EXIT")) {
						if (orden.toUpperCase().contains("NOTAS")) {
							System.err.format("\tOrden Notas %d \n", notas.get(alumno));
							salida.println(notas.containsKey(alumno));
						} else {
							System.err.format("\tOrden %s desconocida\n", alumno);
							salida.println("Orden " + alumno + " desconocida");
						}
						orden = entrada.readLine();
					}
				} else {
					salida.println("Alumno " + alumno + " desconocido");
					System.err.format("\tAlumno %s desconocido", alumno);
					
				}

			}

		} catch (IOException e) {
			System.err.println("\tNo puede establer canales de E/S para la conexión");
			System.exit(-1);
		} finally {

			try {
				socketCliente.close();
				socketServidor.close();
				entrada.close();
				salida.close();
				System.err.println("\t:Servidor finalizado");

			} catch (IOException e) {
				System.err.println("\tNo puede cerrar los sockets o canales de E/S de la conexión");

			}

		}
	}
}