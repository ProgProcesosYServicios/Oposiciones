package es.tessier.comunicaciones.tcp;

import java.net.*;
import java.io.*;

public class ClienteTCP extends Thread {
	private int puerto;
	private String IP;
	private BufferedReader in;
	private DataOutputStream out;
	private Socket socket;
	private String mensaje ;
	
	ClienteTCP(){
		this(6000,"127.0.0.1");
	}
	
	ClienteTCP(int puerto, String IP){
		this.puerto = puerto;
		this.IP = IP;
		
		

	}
	
	@Override
	public void run() {
		System.out.println("Cliente Iniciado");
		// Creamos una instancia BuffererReader en la
		// que guardamos los datos introducido por el usuario
		in = new BufferedReader(new InputStreamReader(System.in));

			
		// Declaramos un bloque try y catch para controlar la ejecución del subprograma
		try {
			
			// Instanciamos un socket con la dirección del destino y el
			// puerto que vamos a utilizar para la comunicación
			socket = new Socket(IP, puerto);
			System.out.println("Cliente: escuchando "+socket);
			// Declaramos e instanciamos el objeto DataOutputStream
			// que nos valdrá para enviar datos al servidor destino
			out = new DataOutputStream(socket.getOutputStream());

			// Creamos un bucle do while en el que enviamos al servidor el mensaje
			// los datos que hemos obtenido despues de ejecutar la función
			// "readLine" en la instancia "in"
			System.out.println("Introduzca un mensaje para enviar al servidor");
			do {
				mensaje = in.readLine();
				// enviamos el mensaje codificado en UTF
				out.writeUTF(mensaje);
				// mientras el mensaje no encuentre la cadena fin, seguiremos ejecutando
				// el bucle do-while
			} while (!mensaje.contains("adios"));
			
			System.err.println("Cliente finalizado");
		}
		catch (IOException e) {
			System.err.println("No puede establer canales de E/S para la conexión");
			System.exit(-1);
		}
		finally{
			try {
				socket.close();
				in.close();
				out.close();
			} catch (IOException e) {				
			}
		}
	}
}