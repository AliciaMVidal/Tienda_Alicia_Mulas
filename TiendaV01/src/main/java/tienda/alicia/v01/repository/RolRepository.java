package tienda.alicia.v01.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tienda.alicia.v01.model.Rol;

@Repository
public interface RolRepository extends JpaRepository<Rol, Integer>{
	

	
}
