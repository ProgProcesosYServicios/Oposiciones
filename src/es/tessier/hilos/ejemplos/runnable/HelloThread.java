package es.tessier.hilos.ejemplos.runnable;

class HelloThread implements Runnable {

	Thread t;

	HelloThread() {
		t = new Thread(this, "Nuevo Thread");
		System.out.println("Creado hilo: " + t);
		t.start(); // Arranca el nuevo hilo de ejecución. Ejecuta run
	}

	@Override
	public void run() {
		// Código a ejecutar por el hilo
		System.out.println("\tHola desde el hilo creado!");
		System.out.println("\tHilo finalizando.");
	}
}
