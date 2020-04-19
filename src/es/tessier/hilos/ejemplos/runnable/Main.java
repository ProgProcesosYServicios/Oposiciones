package es.tessier.hilos.ejemplos.runnable;

class Main {
	public static void main(String args[]) {
		new HelloThread(); // Crea un nuevo hilo de ejecución
		System.out.println("Hola desde el hilo principal!");
		System.out.println("Proceso acabando.");
	}
}
