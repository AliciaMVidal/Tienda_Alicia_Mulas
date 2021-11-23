package tienda.alicia.v01.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Valoracion {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private int id_Producto;
	private int id_Usuario;
	private double valoracion;
	private String comentario;
	
	public Valoracion() {
		
	}

	public Valoracion(int id, int id_Producto, int id_Usuario, double valoracion, String comentario) {
		super();
		this.id = id;
		this.id_Producto = id_Producto;
		this.id_Usuario = id_Usuario;
		this.valoracion = valoracion;
		this.comentario = comentario;
	}

	public Valoracion(int id_Producto, int id_Usuario, double valoracion, String comentario) {
		super();
		this.id_Producto = id_Producto;
		this.id_Usuario = id_Usuario;
		this.valoracion = valoracion;
		this.comentario = comentario;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId_Producto() {
		return id_Producto;
	}

	public void setId_Producto(int id_Producto) {
		this.id_Producto = id_Producto;
	}

	public int getId_Usuario() {
		return id_Usuario;
	}

	public void setId_Usuario(int id_Usuario) {
		this.id_Usuario = id_Usuario;
	}

	public double getValoracion() {
		return valoracion;
	}

	public void setValoracion(double valoracion) {
		this.valoracion = valoracion;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}
	
	
	

}
