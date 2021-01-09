package es.tessier.concurrencia.filosofos;

public class Main {
	public static void main(String[] args) {
		Tenedor[] tenedores = new Tenedor[5];
		Filosofo[] filosofos = new Filosofo[5];
		Estado hlp = new Estado();
		for (int i = 0; i < 5; i++) {
			tenedores[i] = new Tenedor();
		}
		for (int i = 0; i < 5; i++) {
			filosofos[i] = new Filosofo(i, tenedores[i], tenedores[(i + 4) % 5], hlp);
			new Thread(filosofos[i]).start();
		}
	}

}
