package es.tessier.concurrencia.filosofos;

public class Tenedor {
	private boolean disponibilidad;

	public Tenedor() {
		disponibilidad = true;
	}

	public boolean getDisponibilidad() {
		return disponibilidad;
	}

	public void setDisponibilidad(boolean bandera) {
		disponibilidad = bandera;
	}

}
