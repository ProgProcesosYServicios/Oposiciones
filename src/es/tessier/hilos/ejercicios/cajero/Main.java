package es.tessier.hilos.ejercicios.cajero;

import java.util.Random;

public class Main {

	static Random rand = new Random();
	static final int NUM_CAJEROS = 2;
	static final int NUM_CLIENTES =2;

	public static void main(String[] args) {

		CajeroThread[] cajeros = new CajeroThread[NUM_CAJEROS];
		Cliente[] clientes = new Cliente[NUM_CLIENTES];
		

		long initialTime;
		
		System.out.println("Compienza el programa principal");
		
		clientes[0] = new Cliente(Integer.toString(1), new int[]  { 2, 2, 1, 5, 2, 3} );
		clientes[1] = new Cliente(Integer.toString(2), new int[]  {1, 3, 5, 1, 1 });
		
		// clientes aleatorios 
		 /*

		int tamCompra;
		for (int i = 0; i < NUM_CLIENTES; i++) {
			tamCompra = rand.nextInt(10) + 1;
			carroCompra = new int[tamCompra];
			for (int j = 0; j < carroCompra.length; j++) {
				carroCompra[j] = rand.nextInt(2)+1;
			}
			clientes[i] = new Cliente(Integer.toString(i), carroCompra);
		}
		*/
		
		// Solución con hilos
		
		/*
		Cajero cajero = new Cajero("1");
		int[] carroCompra;initialTime = System.currentTimeMillis();
		
		
		
		System.out.format("%d Procesando Compras\n",(System.currentTimeMillis() - initialTime)  / 1000);
		
		for (int i = 0; i < NUM_CLIENTES; i ++) {			
				cajero.setInitialTime(System.currentTimeMillis());
				cajero.procesarCompra(clientes[i]);
		}
		
		System.out.format("%d Finalizadas las compras\n",(System.currentTimeMillis() - initialTime)  / 1000);
		*/
		
		// Solución con hilos
		
		
		initialTime = System.currentTimeMillis();

		System.out.format("%d Procesando Compras\n",(System.currentTimeMillis() - initialTime)  / 1000);

		for (int i = 0; i < NUM_CAJEROS; i++) {
			cajeros[i] = new CajeroThread("Cajero " + i + 1);
		}
		
		initialTime = System.currentTimeMillis();
		System.out.format("%d Procesando Compras\n",(System.currentTimeMillis() - initialTime)  / 1000);


		for (int i = 0; i < NUM_CLIENTES; i += 2) {
			for (int j = 0; j < NUM_CAJEROS; j++) {
				cajeros[j] = new CajeroThread("Cajero " + (j + 1), System.currentTimeMillis(), clientes[i]);
				cajeros[j].start();
				
			}

		}

		for (int i = 0; i < NUM_CAJEROS; i++) {
			try {
				cajeros[i].join();
			} catch (Exception e) {
			}
		}

		System.out.format("%d Finalizadas las compras\n",(System.currentTimeMillis() - initialTime)  / 1000);


	}
}
