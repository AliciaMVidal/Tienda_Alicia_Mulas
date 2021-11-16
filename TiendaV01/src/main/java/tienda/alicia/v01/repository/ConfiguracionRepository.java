package tienda.alicia.v01.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tienda.alicia.v01.model.Configuracion;

@Repository
public interface ConfiguracionRepository extends JpaRepository<Configuracion, Integer> {
	
	Configuracion findByClave(String clave);

}
