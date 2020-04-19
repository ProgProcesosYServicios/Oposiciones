package es.tessier.concurrencia.lectoresPrioritariosEscritores.semaforo;
import java.util.Random;
import java.util.concurrent.Semaphore;


public class LectorEscritor {
	
	private volatile long dato = 0;
	private int numLect = 0;
	private Semaphore semLect = new Semaphore(1, true);
	private Semaphore semEsc = new Semaphore(1, true);

    private final Random aleatorio  = new Random();;
	
	
	//Evita que un nuevo lector se "cuele" entre escritores


	class Lector extends Thread{
		private int idLector;
		
		Lector( int idlector) 
	    {	      
	        this.idLector = idlector;
	    }
		
	@Override
	public void run()   {
		while (true) {
			try {
				Thread.sleep((long) (Math.random()*500));
			} catch (InterruptedException e) {}
			semLect.acquireUninterruptibly();
			numLect++;
			if (numLect==1)
				semEsc.acquireUninterruptibly();
			semLect.release();
		
			System.out.println("lector "+ idLector+" Leer dato "+dato);
			
			
			semLect.acquireUninterruptibly();
			numLect--;
			if (numLect==0)
				semEsc.release();
			semLect.release();
			}

			
		}
	
	}
	
	class Escritor extends Thread{
		private int idEscritor;
		
		Escritor( int idlector) 
	    {	      
	        this.idEscritor = idlector;
	    }
		
	@Override
	public void run()   {
		while (true) {
			semEsc.acquireUninterruptibly();
			try {
				Thread.sleep((long) (Math.random()*500));
			} catch (InterruptedException e) {}			dato = aleatorio.nextInt(300);
			System.out.println("Escritor "+ idEscritor+" escribe dato "+dato);
			semEsc.release();
		}
	}

	}
	public void exec() {
		for (int i = 0; i < 5; i++) {
			new Lector(i).start();
		}

		for (int i = 0; i < 3; i++) {
			new Escritor(i).start();
		}
	}

	public static void main(String[] args) {
		new LectorEscritor().exec();
	}
	

}