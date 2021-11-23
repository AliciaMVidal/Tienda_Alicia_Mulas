package tienda.alicia.v01.service;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tienda.alicia.v01.model.DetallePedido;
import tienda.alicia.v01.repository.PedidoDetalleRespository;
import tienda.alicia.v01.repository.PedidoRepository;

@Service
public class PedidoDetalleService {
	
	@Autowired
	PedidoDetalleRespository pedidoDetalleRepository;
	
	@PostConstruct
	public void cargarDetallePedido() {
		DetallePedido dp = new DetallePedido(1, 12, 2.91, 1, 21, 2.91);
		pedidoDetalleRepository.save(dp);
		dp = new DetallePedido(1, 9, 40.79, 1, 21, 40.79);
		pedidoDetalleRepository.save(dp);
		dp = new DetallePedido(1, 2, 1.45, 1, 21, 1.45);
		pedidoDetalleRepository.save(dp);
		dp = new DetallePedido(2, 5, 55.95, 1, 21, 55.95);
		pedidoDetalleRepository.save(dp);
		dp = new DetallePedido(2, 8, 5.29, 1 ,21, 5.29);
		pedidoDetalleRepository.save(dp);
	}
	
	public void addDetallePedido(DetallePedido detallePedido) {
		pedidoDetalleRepository.save(detallePedido);
	}
	
	public List<DetallePedido> getDetalleByIDPedido(int id) {
		return pedidoDetalleRepository.getDetalleByIdPedido(id);
	}
}
