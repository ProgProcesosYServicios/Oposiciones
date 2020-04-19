package es.tessier.concurrencia.condicionCarrera;

/**
 * Clase de ejemplo que busca conseguir una condici�n de carrera
 * de dos hebras. Implementa el interfaz Runnable y tiene un main.
 * En el main se crea una instancia del objeto y se utiliza en
 * dos Thread diferentes.
 * 
 * El m�todo run() ejecutado por cada hebra suma NUM_VECES veces el
 * valor NUMERO_SUMADO al atributo _suma. Pero la suma la realiza
 * incrementando el valor de uno en uno.
 * 
 * Como la operaci�n es sencilla, es f�cil saber de antemano el
 * valor final esperado, para compararlo con el conseguido.
 * 
 * Para evitar la condici�n de carrera, se hace uso del algoritmo
 * de Peterson.
 * 
 */
public class Peterson implements Runnable {

	/**
	 * N�mero que vamos a sumar al atributo _suma en el m�todo
	 * run(). Pero lo haremos de uno en uno.
	 */
	public static final int NUMERO_SUMADO = 10000;

	/**
	 * N�mero de veces que vamos a sumar NUMERO_SUMADO al atributo
	 * _suma en el m�todo run().
	 */
	public static final long NUM_VECES = 10000;

	/**
	 * Constructor. Inicializa los atributos que mantienen el estado
	 * de la secci�n cr�tica.
	 */
	public Peterson() {
		
		_enSeccionCritica = new Flag[2];
		_enSeccionCritica[0] = new Flag();
		_enSeccionCritica[1] = new Flag();

	} // constructor

	//-----------------------------------------------------

	/**
	 * M�todo est�tico que devuelve acumulador + n. Hace la
	 * suma de uno en uno con un for.
	 * 
	 * @param acumulador Valor inicial.
	 * @param n Valor a sumar
	 * @return acumulador + n
	 */
	private static long sumaN(long acumulador, int n) {

		long total = acumulador;
		for (int i = 0; i < n; ++i)
			total += 1;
		//Thread.yield(); // Aumentar la probabilidad de condici�n de carrera
		return total;
		
	} // sumaN
	
	//-----------------------------------------------------

	/**
	 * M�todo a ser ejecutado a trav�s de una hebra. Llama
	 * NUM_VECES a sumaN para sumar NUMERO_SUMADO al atributo _suma.
	 */
	public void run() {

		int numHebra;
		if (Thread.currentThread().getName().equals("Hebra0"))
			numHebra = 0;
		else
			numHebra = 1;
		
		for (int i = 1; i <= NUM_VECES; ++i) {
			//System.out.println("[" + numHebra + "]: intento entrar");
			entradaSeccionCritica(numHebra);
				//System.out.println("[" + numHebra + "]: \t entro");
				_suma = sumaN(_suma, NUMERO_SUMADO);
				//System.out.println("[" + numHebra + "]: \t salgo (" + _suma + ")");
			salidaSeccionCritica(numHebra);
		}

	} // run
	
	//-----------------------------------------------------

	/**
	 * Devuelve el valor del atributo _suma.
	 * 
	 * @return Valor del atributo _suma.
	 */
	public long getSuma() {
		
		return _suma;

	} // getSuma

	//-----------------------------------------------------
	// M�todos y atributos de gesti�n de la secci�n cr�tica
	//-----------------------------------------------------

	/**
	 * "Preprotocolo" para entrar en la secci�n cr�tica de modo
	 * que se use _suma en exclusi�n m�tua. Se pretende que el
	 * m�todo vuelva �nicamente cuando se garantice que s�lo la
	 * hebra actual estar� dentro de la secci�n cr�tica.
	 * 
	 * @param numHebra N�mero de hebra (0 o 1) que quiere entrar
	 * en la secci�n cr�tica. 
	 */
	protected void entradaSeccionCritica(int numHebra) {

		_enSeccionCritica[numHebra].valor = true;

		int otraHebra = numHebra ^ 0x1;
		
		_turno = otraHebra;
		while(_enSeccionCritica[otraHebra].valor &&
		      (_turno == otraHebra))
			;
		
		// �Est� libre!

	} // entradaSeccionCritica

	/**
	 * "Postprotocolo" para indicar que la hebra ha terminado
	 * el uso del recurso compartido que debe ser usado en
	 * exclusi�n m�tua y que abandona por tanto la secci�n cr�tica.
	 * 
	 * @param numHebra N�mero de hebra (0 o 1) que abandona
	 * la secci�n cr�tica.
	 */
	protected void salidaSeccionCritica(int numHebra) {
		
		_enSeccionCritica[numHebra].valor = false;
				
	} // salidaSeccionCritica

	/**
	 * Clase ("estructura") con un �nico booleano. Lo importante
	 * del booleano es que es vol�til.
	 */
	class Flag {
		public volatile boolean valor = false;
	} // class Flag

	protected Flag[] _enSeccionCritica; // Inicializaci�n en el constructor

	/**
	 * Hebra que tiene el turno para entrar en la secci�n cr�tica (en caso
	 * de "empate").
	 */
	protected volatile int _turno = 0;

	//-----------------------------------------------------
	//                    M�todos est�ticos
	//-----------------------------------------------------

	/**
	 * Programa principal. Crea una instancia de esta clase, y la
	 * ejecuta simult�neamente en dos hebras diferentes. Espera a que
	 * ambas terminen y mira el valor sumado final, comprobando si es
	 * el esperado.
	 * 
	 * @param args Par�metros de la aplicaci�n. Se ignoran.
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws InterruptedException {
		
		Peterson racer = new Peterson();
		Thread t1, t2;
		
		t1 = new Thread(racer, "Hebra0");
		t2 = new Thread(racer, "Hebra1");

		t1.start();
		t2.start();

		long resultadoEsperado;
		resultadoEsperado = NUMERO_SUMADO * NUM_VECES * 2;

		t1.join();
		t2.join();

		System.out.println("El resultado final es " + racer.getSuma());
		System.out.println("Esper�bamos " + resultadoEsperado);

		if (racer.getSuma() != resultadoEsperado)
			System.out.println("���NO COINCIDEN!!!");
		
	} // main

	//-----------------------------------------------------
	//                    Atributos privados
	//-----------------------------------------------------

	/**
	 * Atributo con el valor acumulado donde se realiza la suma.
	 * Hace las veces de variable compartida entre las dos hebras.
	 */
	private volatile long _suma = 0;
	
} // Peterson