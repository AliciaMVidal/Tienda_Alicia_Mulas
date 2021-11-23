package tienda.alicia.v01.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import tienda.alicia.v01.model.Descuento;

@Repository
public interface DescuentoRepository extends JpaRepository<Descuento, Integer>{

}
