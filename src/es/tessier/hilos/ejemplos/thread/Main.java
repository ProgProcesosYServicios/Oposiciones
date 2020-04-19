package es.tessier.hilos.ejemplos.thread;

class Main {
	 public static void main(String args[]) {
	        new HelloThread().start();// Crea y arranca un nuevo hilo de ejecución
		System.out.println ("Hola desde el hilo principal!");
		System.out.println ("Proceso acabando.");

	 }
	}
