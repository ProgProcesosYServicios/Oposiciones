package es.tessier.comunicaciones.echo;


public class MainTCP {

	public static void main(String[] args) {
		new ServidorTCP().start();
		new ClienteTCP().start();
	}

}
