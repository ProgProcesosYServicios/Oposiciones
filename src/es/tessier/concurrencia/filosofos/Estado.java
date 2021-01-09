package es.tessier.concurrencia.filosofos;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Estado {

	Lock mutex = new ReentrantLock();
	Condition[] cond = new Condition[5];
	String[] estado = new String[5];
	int[] id = new int[5];

	void imprimirEstado(int id) {
		StringBuffer line = new StringBuffer();
		for (int i = 0; i < 5; i++) {
			line.append(estado[i] + " ");
		}
		System.out.println(line + "(" + (id + 1) + ")");
	}

	public Estado() {
		for (int i = 0; i < 5; i++) {
			id[i] = i;
			estado[i] = "Piensa";
			cond[i] = mutex.newCondition();
		}
	}

	public void setState(int id, String s) {
		estado[id] = s;
	}

	public void cogerTenedor(int id, Tenedor izq, Tenedor der) {
		mutex.lock();
		try {
			setState(id, "Espera");
			while (!izq.getDisponibilidad() || !der.getDisponibilidad()) {
				cond[id].await();
			}
			izq.setDisponibilidad(false);
			der.setDisponibilidad(false);
			setState(id, "Come  ");
			imprimirEstado(id);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			mutex.unlock();
		}
	}

	public void liberarTenedor(int id, Tenedor izq, Tenedor der) {
		mutex.lock();
		setState(id, "Piensa");
		izq.setDisponibilidad(true);
		der.setDisponibilidad(true);
		cond[(id + 1) % 5].signalAll();
		cond[(id + 4) % 5].signalAll();
		imprimirEstado(id);
		mutex.unlock();
	}
}