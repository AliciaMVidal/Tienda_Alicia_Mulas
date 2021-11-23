package tienda.alicia.v01.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tienda.alicia.v01.model.Categoria;
import tienda.alicia.v01.model.Rol;
import tienda.alicia.v01.repository.CategoriaRepository;

@Service
public class CategoriaService {
	
	@Autowired
	CategoriaRepository categoriaRepositorty;
	
	@PostConstruct
	public void cargarCategorias() {
		Categoria categoria = new Categoria("Gatos", "Productos para gatos");
		categoriaRepositorty.save(categoria);
		categoria = new Categoria("Perros", "Productos para perros");
		categoriaRepositorty.save(categoria);
		categoria = new Categoria("Peces", "Productos para peces");
		categoriaRepositorty.save(categoria);
		categoria = new Categoria("Pajaros", "Productos para pajaros");
		categoriaRepositorty.save(categoria);
		
	}

	public List<Categoria> getTodasCategorias(){
		return categoriaRepositorty.findAll();
	}

	public void addCategoria(Categoria categoria) {
		categoriaRepositorty.save(categoria);
	}
	
	public Categoria getIdCategoriaNombre(String nombre) {
		Categoria categoria = categoriaRepositorty.findByNombre(nombre);
		return categoria;
	}
	
	public Categoria getCategoriaById(int id) {
		Categoria categoria = categoriaRepositorty.getById(id);
		return categoria;
	}
	public void editCategoria(Categoria categoria) {
		categoriaRepositorty.save(categoria);
	}
	
	// metodo para obtener el nombre de la categoria (categoria.nombre)
	// de un array de ids de categorias que estan en la tabla de productos (producto.id_categoria)
	
	public HashMap<Integer, String> listaCategoriasIds(ArrayList listadeIdsCategoriaEnProducto) {
		// Se crea una lista categorias cuya id esten en la tabla Productos
		List<Categoria> listaObjetosCategoriaenProducto = categoriaRepositorty.findByIdIn(listadeIdsCategoriaEnProducto);
		// Se crea un HashMap con la relacion de producto.id_categoria y categoria.id
		HashMap<Integer, String> hmRelacionCategoriaYProducto = new HashMap<Integer, String>();
		// Se recorren los objetos para sacar el id.categoria y guardarlo en el hashmap
		for (Categoria categoria : listaObjetosCategoriaenProducto) {
			hmRelacionCategoriaYProducto.put(categoria.getId(), categoria.getNombre());
		}
		return hmRelacionCategoriaYProducto;
	}
}
