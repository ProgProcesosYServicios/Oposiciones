package es.tessier.comunicaciones.tcp;

import java.net.*;

import java.io.*;

public class ServidorTCP extends Thread {

	private int puerto;
	private ServerSocket socketServidor;
	private Socket socketCliente;
	private DataInputStream in;
	
	ServidorTCP( ) {
		this(6000);
	}
	
	ServidorTCP(int puerto) {
		this.puerto = puerto;
	}

	@Override
	public void run() {
		// Código a ejecutar por el hilo
		System.err.println("\tServidor: Iniciado");
		try {

			// Instanciamos un ServerSocket con la dirección del destino y el
			// puerto que vamos a utilizar para la comunicación
			socketServidor = new ServerSocket(puerto);
			System.out.println("\tServidor: escuchando "+socketServidor);
			// Creamos un socket_cli al que le pasamos el contenido del objeto socket
			// después
			// de ejecutar la función accept que nos permitirá aceptar conexiones de
			// clientes
			socketCliente = socketServidor.accept();
			System.out.println("\tServidor:Conexión aceptada: "+ socketCliente);
			// Declaramos e instanciamos el objeto DataInputStream
			// que nos valdrá para recibir datos del cliente

			in = new DataInputStream(socketCliente.getInputStream());

			// Creamos un bucle do while en el que recogemos el mensaje
			// que nos ha enviado el cliente y después lo mostramos
			// por consola
			String mensaje = "";
			do {				
				mensaje = in.readUTF();
				System.out.println("\t"+mensaje);
			} while (!mensaje.contains("adios"));
			System.out.println("\t Servidor finalizado");
		}
		catch (IOException e) {			
			System.err.println("No puede establer canales de E/S para la conexión");
			System.exit(-1);
		}
		finally{
			try {
				socketCliente.close();
				socketServidor.close();
				in.close();
			} catch (IOException e) {				
			}
		}
	}
}