package es.tessier.comunicaciones.echo;

import java.net.*;

import java.io.*;

public class ServidorTCP extends Thread {

	private int puerto;
	private ServerSocket socketServidor;
	private Socket socketCliente;
	private BufferedReader entrada;
	private PrintWriter salida;

	ServidorTCP() {
		this(4444);
	}

	ServidorTCP(int puerto) {
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
		System.err.println("\tEscuchando en el puerto" + puerto);
		try {						
			
			socketCliente = socketServidor.accept();
			System.err.println("\t:Conexión aceptada: " + socketCliente);

			// Establece canal de entrada
			entrada = new BufferedReader(new InputStreamReader(socketCliente.getInputStream()));
			// Establece canal de salida
			salida = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socketCliente.getOutputStream())), true);

			// Hace eco de lo que le proporciona el cliente, hasta que recibe "Adios"
			while (true) {
				System.err.println("\tmensaje para el cliente $>");

				String str = entrada.readLine();
				System.err.println("\t:Cliente: " + str);
				salida.println(str);
				if (str.toUpperCase().contains("ADIOS"))
					break;
			}

		} catch (IOException e) {
			System.err.println("No puede establer canales de E/S para la conexión");
			System.exit(-1);
		} finally {

			try {
				socketCliente.close();
				socketServidor.close();
				entrada.close();
				salida.close();
				System.err.println("\t:Servidor finalizado");

			} catch (IOException e) {
				System.err.println("No puede cerrar los sockets o canales de E/S de la conexión");

			}

		}
	}
}