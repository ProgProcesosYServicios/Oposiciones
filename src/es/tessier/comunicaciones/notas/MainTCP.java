package es.tessier.comunicaciones.notas;


public class MainTCP {

	public static void main(String[] args) {
		new ServidorTCP().start();
		new ClienteTCP().start();
	}

}
