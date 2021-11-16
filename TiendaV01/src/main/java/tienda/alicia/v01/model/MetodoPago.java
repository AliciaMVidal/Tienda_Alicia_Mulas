package tienda.alicia.v01.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class MetodoPago {

	@Id @GeneratedValue
	private int id;
	private String metodo_pago;
	
	public MetodoPago() {
		
	}
	
	public MetodoPago(String metodo_pago) {
		this.metodo_pago = metodo_pago;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMetodo_pago() {
		return metodo_pago;
	}

	public void setMetodo_pago(String metodo_pago) {
		this.metodo_pago = metodo_pago;
	}
	
	
}
