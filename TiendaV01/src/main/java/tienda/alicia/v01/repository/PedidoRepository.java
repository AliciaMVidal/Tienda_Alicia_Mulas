package tienda.alicia.v01.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import tienda.alicia.v01.model.Pedido;
import tienda.alicia.v01.model.Producto;
import tienda.alicia.v01.model.Usuario;

@Repository
public interface PedidoRepository  extends JpaRepository<Pedido, Integer>{
	
	Pedido findById (int id);
	
	@Query(value="select * from Pedido where id_usuario=?1", nativeQuery = true)
	List<Pedido> buscarPedidoPorUsuario(int id);

	

	@Query(value="update Pedido set estado = 'cancelado' where id=?1", nativeQuery = true)
	void cancelarPedidoPorId(int id);
}
