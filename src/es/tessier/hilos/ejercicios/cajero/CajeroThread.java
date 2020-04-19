package es.tessier.hilos.ejercicios.cajero;

public class CajeroThread extends Thread {
    public static final int SEGUNDO = 1000;
	private String nombre;
    private Cliente clienteActual;
	private long initialTime;

	CajeroThread() {
		 this("",0,new Cliente());
   }
	
	CajeroThread(String nombre) {
       this(nombre,0,new Cliente());
   }
	
	CajeroThread(String nombre, long initialTime, Cliente cliente) {
       this.nombre = nombre;
       this.initialTime = initialTime;
       this.clienteActual = cliente;
   }
    
	@Override
	public void run() {
		System.out.format("\tEl cajero %s comienza a procesar la compra del cliente %s en el tiempo :%d \n", this.nombre,clienteActual.getNombre(), (System.currentTimeMillis() - this.initialTime)  / SEGUNDO);
			
		for (int i = 0; i < clienteActual.getCarroCompra().length; i++) { 
			this.esperarXsegundos(clienteActual.getCarroCompra()[i]); 
			System.out.format("\t\tProcesado el producto %d  ->Tiempo: seg %d \n ", i+1, (System.currentTimeMillis() - this.initialTime)  / SEGUNDO);

		}
		System.out.format("\tEl cajero %s termina de procesar la compra del cliente %s en el tiempo :%d \n", this.nombre,clienteActual.getNombre(), (System.currentTimeMillis() - this.initialTime)  / SEGUNDO);
		
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

	public Cliente getClienteActual() {
		return clienteActual;
	}

	public void setClienteActual(Cliente clienteActual) {
		this.clienteActual = clienteActual;
	}

	public long getInitialTime() {
		return initialTime;
	}

	public void setInitialTime(long initialTime) {
		this.initialTime = initialTime;
	}
	
	

}

