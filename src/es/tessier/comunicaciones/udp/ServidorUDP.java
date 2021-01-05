package es.tessier.comunicaciones.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class ServidorUDP extends Thread{

	private int puertoServidor;

	private byte[] bufer;
	private DatagramSocket socketUDP;
	private DatagramPacket peticion;
	private DatagramPacket respuesta;
	private String mensaje;
	private byte[] mensajeBytes;
	
	ServidorUDP() {
		this(6789);
	}

	ServidorUDP(int puertoServidor) {
		this.puertoServidor = puertoServidor;
		
	}
	@Override
	public void run() {

	    try {

	      socketUDP = new DatagramSocket(puertoServidor);
	      bufer = new byte[1000];

	      while (true) {
	        // Construimos el DatagramPacket para recibir peticiones
	         peticion =
	          new DatagramPacket(bufer, bufer.length);

	        // Leemos una petición del DatagramSocket
	        socketUDP.receive(peticion);

	        System.err.print("\tDatagrama recibido del host: " +
	                           peticion.getAddress());
	        System.err.print(" desde el puerto remoto: " +
	                           peticion.getPort());
	        String cliente = new String(peticion.getData());
	        System.err.println("\t"+cliente);
	        // Construimos el DatagramPacket para enviar la respuesta
	        mensaje = "Hola "+cliente+", soy el servidor";
	        mensajeBytes = mensaje.getBytes();
	         respuesta =
	          new DatagramPacket(mensajeBytes, mensajeBytes.length,
	                             peticion.getAddress(), peticion.getPort());

	        // Enviamos la respuesta, que es un eco
	        socketUDP.send(respuesta);
	      }
	     

	    } catch (SocketException e) {
	      System.out.println("Socket: " + e.getMessage());
	    } catch (IOException e) {
	      System.out.println("IO: " + e.getMessage());
	    }
	    System.out.println("\tservidor cerrado");
	  }
}
