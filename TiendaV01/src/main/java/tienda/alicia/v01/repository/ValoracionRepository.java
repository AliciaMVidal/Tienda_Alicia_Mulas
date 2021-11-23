package tienda.alicia.v01.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import tienda.alicia.v01.model.Usuario;
import tienda.alicia.v01.model.Valoracion;

@Repository
public interface ValoracionRepository extends JpaRepository<Valoracion, Integer> {
	
	@Query(value="select * from Valoracion where id_producto=?1", nativeQuery = true)
	List<Valoracion> listaValoracion(int id_producto);
	

}
