package es.tessier.comunicaciones.udp;


public class MainUDP {

	public static void main(String[] args) {
		new ServidorUDP().start();
		new ClienteUDP().start();
		new ClienteUDP().start();
		new ClienteUDP().start();
		new ClienteUDP().start();
	}

}
