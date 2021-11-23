package tienda.alicia.v01.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tienda.alicia.v01.model.Categoria;
import tienda.alicia.v01.model.Rol;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Integer>{

	Categoria findByNombre(String nombre);
	
	//Buscar las categorias que estan en un ArrayList de ids categorias
	List<Categoria> findByIdIn(ArrayList<Integer> listaidscategoria);
}
