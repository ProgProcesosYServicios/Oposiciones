package es.tessier.hilos.ejemplos.thread;

class HelloThread extends Thread {

    @Override
	public void run() {
	//C�digo a ejecutar por el hilo
		System.out.println ("\tHola desde el hilo creado!");
	    System.out.println("\tHilo finalizando.");
	}
}
