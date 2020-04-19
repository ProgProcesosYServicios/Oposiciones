package es.tessier.hilos.ejercicios.cajero;

public class Cajero extends Thread {
    public static final int SEGUNDO = 1000;
	private String nombre;
	private long initialTime;

	
	Cajero() {
		 this("",0);
    }
	
	Cajero(String nombre) {
        this(nombre,0);
    }
	
    Cajero(String nombre, long initialTime) {
        this.nombre = nombre;
        this.initialTime = initialTime;
    }
    
	public void procesarCompra(Cliente cliente) {
		System.out.format("\tEl cajero %s comienza a procesar la compra del cliente %s en el tiempo :%d \n", this.nombre,cliente.getNombre(), (System.currentTimeMillis() - this.initialTime)  / SEGUNDO);
		
		for (int i = 0; i < cliente.getCarroCompra().length; i++) { 
			this.esperarXsegundos(cliente.getCarroCompra()[i]); 
			System.out.format("\t\tProcesado el producto %d  ->Tiempo: seg %d \n ", i+1, (System.currentTimeMillis() - this.initialTime)  / SEGUNDO);

		}
		System.out.format("\tEl cajero %s termina de procesar la compra del cliente %s en el tiempo :%d \n", this.nombre,cliente.getNombre(), (System.currentTimeMillis() - this.initialTime)  / SEGUNDO);
		
	}	

	private void esperarXsegundos(int segundos) {
		try {
			Thread.sleep(segundos * SEGUNDO );
		} catch (InterruptedException ex) {
			Thread.currentThread().interrupt();
		}
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	
	public long getInitialTime() {
		return initialTime;
	}
	
	
	public void setInitialTime(long initialTime) {
		this.initialTime = initialTime;
	}
	
	

}

