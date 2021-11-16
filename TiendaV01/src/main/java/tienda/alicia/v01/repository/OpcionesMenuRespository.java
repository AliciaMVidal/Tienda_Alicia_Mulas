package tienda.alicia.v01.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import tienda.alicia.v01.model.OpcionesMenu;

@Repository
public interface OpcionesMenuRespository extends JpaRepository<OpcionesMenu, Integer> {

	@Query(value="select * from opciones_menu where id_rol=?1", nativeQuery = true)
	List<OpcionesMenu> getOpcionesPorRol(int id_rol);
}
