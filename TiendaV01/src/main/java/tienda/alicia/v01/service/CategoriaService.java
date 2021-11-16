package tienda.alicia.v01.service;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tienda.alicia.v01.model.Categoria;
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
}
