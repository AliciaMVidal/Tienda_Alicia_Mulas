package tienda.alicia.v01.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import tienda.alicia.v01.model.Producto;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Integer> {
	
	Producto findByNombre(String nombre);
	
	@Query(value="select * from Producto where nombre=?1", nativeQuery = true)
	List<Producto> buscarProductoPorNombre(String nombre);

	//Buscar los productos que estan en un ArrayList de ids de producto
	List<Producto> findByIdIn(ArrayList<Integer> listaidsproducto);
	
	//Buscar todos los productos ordenador por precio mas caro
	 List<Producto> findByOrderByPrecioDesc();
	 //Por barato
	 List<Producto> findByOrderByPrecioAsc();
	 //Por categoria
	 @Query(value="select * from Producto where id_categoria=?1", nativeQuery = true)
	List<Producto> getProductoPorCategoria(int id_categoria);
	
}
