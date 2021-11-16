package tienda.alicia.v01.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import tienda.alicia.v01.model.DetallePedido;

@Repository
public interface PedidoDetalleRespository extends JpaRepository<DetallePedido, Integer>{

	@Query(value="Select * from detalle_pedido where id_pedido=?1", nativeQuery = true)
	List<DetallePedido>  getDetalleByIdPedido(int id);
}
