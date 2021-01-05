package es.tessier.comunicaciones.udp;

import java.net.*;
import java.io.*;

public class ClienteUDP extends Thread {

	private int puertoServidor;
	private String IP;
	private byte[] mensajeBytes;
	private byte[] bufer;
	private DatagramSocket socketUDP;
	private InetAddress hostServidor;
	private DatagramPacket peticion;
	private DatagramPacket respuesta;

	ClienteUDP() {
		this(6789, "127.0.0.1");
	}

	ClienteUDP(int puertoServidor, String IP) {
		this.puertoServidor = puertoServidor;
		this.IP = IP;
	}

	// Los argumentos proporcionan el mensaje y el nombre del servidor
	@Override
	public void run() {

		try {
			socketUDP = new DatagramSocket();
			
			hostServidor = InetAddress.getByName(IP);
			mensajeBytes = Thread.currentThread().getName().getBytes();
			// Construimos un datagrama para enviar el mensaje al servidor
			peticion = new DatagramPacket(mensajeBytes, mensajeBytes.length, hostServidor, puertoServidor);

			// Enviamos el datagrama
			socketUDP.send(peticion);

			// Construimos el DatagramPacket que contendrá la respuesta
			bufer = new byte[1000];
			respuesta = new DatagramPacket(bufer, bufer.length);
			socketUDP.receive(respuesta);

			// Enviamos la respuesta del servidor a la salida estandar
			System.out.println(Thread.currentThread().getName()+":Respuesta: " + new String(respuesta.getData()));

			

		} catch (SocketException e) {
			System.err.println(Thread.currentThread().getName()+":Socket: " + e.getMessage());
		} catch (IOException e) {
			System.err.println(Thread.currentThread().getName()+":IO: " + e.getMessage());
		}
		finally {
			// Cerramos el socket
			socketUDP.close();
			//System.out.println(Thread.currentThread().getName()+":cliente cerrado");
		}
	}
}