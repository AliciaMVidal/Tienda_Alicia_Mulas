package tienda.alicia.v01.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Impuesto {
	
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private double impuesto;
	
	
	public Impuesto() {
		
	}
	
	public Impuesto(int id, double impuesto) {
		super();
		this.id = id;
		this.impuesto = impuesto;
	}
	
	public Impuesto(double impuesto) {
		this.impuesto = impuesto;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getImpuesto() {
		return impuesto;
	}

	public void setImpuesto(double impuesto) {
		this.impuesto = impuesto;
	}
	
	

}
