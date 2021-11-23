package tienda.alicia.v01.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tienda.alicia.v01.model.Producto;
import tienda.alicia.v01.model.Rol;

@Repository
public interface RolRepository extends JpaRepository<Rol, Integer>{
	
	//Buscar los rolesque estan en un ArrayList de ids de roles
	List<Rol> findByIdIn(ArrayList<Integer> listaidsrol);

	
}
