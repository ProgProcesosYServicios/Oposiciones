package es.tessier.comunicaciones.tcp;


public class MainTCP {

	public static void main(String[] args) {
		new ServidorTCP().start();
		new ClienteTCP().start();
	}

}
