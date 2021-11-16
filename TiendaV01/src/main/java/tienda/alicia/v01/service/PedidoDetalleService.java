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
		DetallePedido dp = new DetallePedido(17, 29, 20.51,
				1, 21, 20.51);
		pedidoDetalleRepository.save(dp);
		dp = new DetallePedido(17, 30, 8.99, 1, 21, 8.99);
		pedidoDetalleRepository.save(dp);
		dp = new DetallePedido(17,5, 39, 2.49,
				1, 21, 2.49);
		pedidoDetalleRepository.save(dp);
		dp = new DetallePedido(18, 29, 8.99, 1, 21, 8.99);
		pedidoDetalleRepository.save(dp);
		dp = new DetallePedido(18, 30, 45.24, 1 ,21, 45.24);
		pedidoDetalleRepository.save(dp);
	}
	
	public void addDetallePedido(DetallePedido detallePedido) {
		pedidoDetalleRepository.save(detallePedido);
	}
	
	public List<DetallePedido> getDetalleByIDPedido(int id) {
		return pedidoDetalleRepository.getDetalleByIdPedido(id);
	}
}
