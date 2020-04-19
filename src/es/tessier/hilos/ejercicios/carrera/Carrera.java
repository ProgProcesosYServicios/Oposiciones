package es.tessier.hilos.ejercicios.carrera;

import java.util.*;
import java.util.concurrent.*;

public class Carrera {
	static final int LINEA_META = 30;
	private List<Caballo> caballos = new ArrayList<Caballo>();
	private ExecutorService exec = Executors.newCachedThreadPool();
	private CyclicBarrier barrier;

	public Carrera(int nCaballos, final int pause) {
		barrier = new CyclicBarrier(nCaballos, new Runnable() {
			private StringBuilder valla = new StringBuilder();

			{
				for (int i = 0; i < LINEA_META; i++)
					valla.append("="); // The fence on the racetrack
			}

			@Override
			public void run() {
				System.out.println(valla);
				for (Caballo caballo : caballos)
					System.out.println(caballo.tracks());
				for (Caballo caballo : caballos)
					if (caballo.getZancadas() >= LINEA_META) {
						System.out.format("El %s ha ganado \n", caballo.toString());
						exec.shutdownNow();
						return;
					}
				try {
					TimeUnit.MILLISECONDS.sleep(pause);
				} catch (InterruptedException e) {
					
					System.out.println("barrier-action sleep interrupted");
				}
			}
		});

		for (int i = 0; i < nCaballos; i++) {
			Caballo caballo = new Caballo(barrier);
			caballos.add(caballo);
			exec.execute(caballo);
		}
	}

}
