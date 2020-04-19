package es.tessier.hilos.ejercicios.carrera;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

class Caballo implements Runnable {
	private static int contador = 0;
	private final int id = contador++;
	private int zancada = 0;
	private static Random aleatorio = new Random();
	private static CyclicBarrier barrera;

	public Caballo(CyclicBarrier b) {
		barrera = b;
	}

	public synchronized int getZancadas() {
		return zancada;
	}

	@Override
	public void run() {
		try {
			while (!Thread.interrupted()) {
				synchronized (this) {
					zancada += aleatorio.nextInt(3) + 1; // Produce 0, 1 o 2
				}
				barrera.await();
			}
		} catch (InterruptedException e) {
			// A legitimate way to exit
		} catch (BrokenBarrierException e) {
			// This one we want to know about
			throw new RuntimeException(e);
		}
	}

	public String toString() {
		return "Caballo " + id + " ";
	}

	public String tracks() {
		StringBuilder s = new StringBuilder();
		for (int i = 0; i < getZancadas(); i++)
			s.append("*");
		s.append(id);
		return s.toString();
	}
}

