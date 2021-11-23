package tienda.alicia.v01.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tienda.alicia.v01.model.Categoria;
import tienda.alicia.v01.model.Proveedor;
import tienda.alicia.v01.repository.ProveedorRepository;

@Service
public class ProveedorService {

	@Autowired
	ProveedorRepository proveedorRepository;
	
	@PostConstruct
	public void cargarProveedor() {
		Proveedor proveedor = new Proveedor("Applaws felinos", "Ruela Óscar, 2, 8º F", "Vigo","Vigo", "917744927", "B-17859341", "applaws@proveedor.es" );
		proveedorRepository.save(proveedor);
		proveedor = new Proveedor("Comida Zilla", "Travesía Pol, 502, 2º A", "Salamanca", "Salamanca", "612497353", "B-66824538", "comidazilla@proveedor.es" );
		proveedorRepository.save(proveedor);
		proveedor = new Proveedor("JBL", "Praza Vidal, 1, 93º D", "Huesca", "Huesca", "901855504", "B-34784060", "jbl@proveedor.es");
		proveedorRepository.save(proveedor);
		proveedor = new Proveedor("Vitacraft Pajaros", "Avenida Soto, 37, 94º C", "Badajoz", "Badajoz", "901855504", "B-34784060", "vitacraft@proveedor.es");
		proveedorRepository.save(proveedor);
		proveedor = new Proveedor("True origins", "Travessera Jimínez, 134, 49º B", "Valencia", "Valencia", "680403856", "B-83814638", "trueorigins@proveedor.es");
		proveedorRepository.save(proveedor);
	}
	
	public List<Proveedor> getTodosProveedores(){
		return proveedorRepository.findAll();
	}
	
	public void addProveedor(Proveedor proveedor) {
		proveedorRepository.save(proveedor);
	}
	
	public void editProveedor(Proveedor proveedor) {
		proveedorRepository.save(proveedor);
	}
	
	public Proveedor getProveedorById(int id) {
		Proveedor proveedor = proveedorRepository.getById(id);
		return proveedor;
	}
	public HashMap<Integer, String> listaProveedoresIds(ArrayList listadeIdsProovedorEnProducto) {
		// Se crea una lista categorias cuya id esten en la tabla Productos
		List<Proveedor> listaObjetosProveedorenProducto = proveedorRepository.findByIdIn(listadeIdsProovedorEnProducto);
		// Se crea un HashMap con la relacion de producto.id_categoria y categoria.id
		HashMap<Integer, String> hmRelacionProveedorYProducto = new HashMap<Integer, String>();
		// Se recorren los objetos para sacar el id.categoria y guardarlo en el hashmap
		for (Proveedor proveedor : listaObjetosProveedorenProducto) {
			hmRelacionProveedorYProducto.put(proveedor.getId(), proveedor.getNombre());
		}
		return hmRelacionProveedorYProducto;
	}
}
