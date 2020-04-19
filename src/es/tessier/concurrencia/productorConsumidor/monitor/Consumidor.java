package es.tessier.concurrencia.productorConsumidor.monitor;

public class Consumidor implements Runnable
{
    private final Contenedor contenedor;
    private final int idconsumidor;
 
    /**
     * Constructor de la clase
     * @param contenedor Contenedor común a los consumidores y el productor
     * @param idconsumidor Identificador del consumidor
     */
    public Consumidor(Contenedor contenedor, int idconsumidor) 
    {
        this.contenedor = contenedor;
        this.idconsumidor = idconsumidor;
    }
 
    @Override
    /**
     * Implementación de la hebra
     */
    public void run() 
    {
        while(Boolean.TRUE)
        {
            System.out.println("El consumidor " + idconsumidor + " consume: " + contenedor.get());
        }
    }
}