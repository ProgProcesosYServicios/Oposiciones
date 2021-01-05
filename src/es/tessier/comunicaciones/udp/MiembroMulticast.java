package es.tessier.comunicaciones.udp;

import java.net.*;
import java.io.*;

public class MiembroMulticast extends Thread {

	private static int miembros ;
	private int id ;
	private int puertoServidor;
	private String mensaje;
	private String IP;

	private byte[] mensajeBytes;
	private InetAddress grupo;
	private DatagramPacket mensajeSalida;
	private DatagramPacket mensajeEntrada;
	private MulticastSocket socket;
	
	MiembroMulticast(String mensaje) {
		this(6789, "230.0.0.0", mensaje);
	}
	
	MiembroMulticast() {
		this(6789, "230.0.0.0", "Hola mundo");
	}

	MiembroMulticast(int puertoServidor, String IP, String mensaje) {
		this.puertoServidor = puertoServidor;
		this.IP = IP;
		this.mensaje = mensaje;
		miembros ++;
		id = miembros;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void run() {
		// args[0] es el mensaje enviado al grupo y args[1] es la direccion del grupo
		try {
			grupo = InetAddress.getByName(IP);
			socket = new MulticastSocket(puertoServidor);
	    
			// Se une al grupo
			socket.joinGroup(grupo);

			// Envia el mensaje
			mensajeBytes = mensaje.getBytes();
			mensajeSalida = new DatagramPacket(mensajeBytes, mensajeBytes.length, grupo, puertoServidor);
			socket.send(mensajeSalida);

			byte[] bufer = new byte[1000];
			String linea;

			// Se queda a la espera de mensajes al grupo, hasta recibir "Adios"
			while (true) {
				mensajeEntrada = new DatagramPacket(bufer, bufer.length);
				socket.receive(mensajeEntrada);
				linea = new String(mensajeEntrada.getData(), 0, mensajeEntrada.getLength());
				System.out.println(id+" Recibido:" + linea);
				if (linea.equals("Adios"))
					break;
			}

			// Si recibe "Adios" abandona el grupo
			socket.leaveGroup(grupo);
		} catch (SocketException e) {
			System.out.println("Socket:" + e.getMessage());
		} catch (IOException e) {
			System.out.println("IO:" + e.getMessage());
		}
		finally {
			socket.close();
		}
	}
}
