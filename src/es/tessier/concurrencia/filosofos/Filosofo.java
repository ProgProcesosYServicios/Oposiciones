package es.tessier.concurrencia.filosofos;

public class Filosofo implements Runnable {
	Estado estado;
	  Tenedor izq, der;
	  int id;
	  public Filosofo(int id, Tenedor izq, Tenedor der, Estado i) {
	    this.estado = i;
	    this.izq = izq;
	    this.der = der;
	    this.id = id;
	  }

	  private void eat() {
	    try {
	      Thread.sleep(1000);
	      System.out.println(id+" Come");
	    } catch (Exception e) {
	    }
	  }

	  private void think() {
	    try {
	      Thread.sleep(1000);
	      System.out.println(id + " piensa");
	    } catch (Exception e) {
	    }
	  }

	  public void run() {
	    while (true) {
	      estado.cogerTenedor(id, izq, der);
	      eat();
	      estado.liberarTenedor(id, izq, der);
	      think();
	    }
	  }
	}