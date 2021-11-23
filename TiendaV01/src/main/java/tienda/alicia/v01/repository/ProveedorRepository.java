package tienda.alicia.v01.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tienda.alicia.v01.model.Categoria;
import tienda.alicia.v01.model.Proveedor;

@Repository
public interface ProveedorRepository extends JpaRepository<Proveedor, Integer>{
	

	List<Proveedor> findByIdIn(ArrayList<Integer> listaidsproveedor);
}
