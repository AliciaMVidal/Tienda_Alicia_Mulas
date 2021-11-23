package tienda.alicia.v01.service;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tienda.alicia.v01.model.Pedido;
import tienda.alicia.v01.model.Producto;
import tienda.alicia.v01.repository.PedidoRepository;

@Service
public class PedidoService {
	
	@Autowired
	PedidoRepository pedidoRepository;
	
	
	@PostConstruct
	public void cargarPedidos() {
		Date date = new Date();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String formattedDate = simpleDateFormat.format(date);
		java.sql.Date date1 = java.sql.Date.valueOf(formattedDate);
		Pedido p = new Pedido(4, date1, "paypal" , "pendiente" , "factura" , 45.15);
		pedidoRepository.save(p);
		p = new Pedido(4, date1, "tarjeta" , "pendiente" , "factura" , 61.24);
		pedidoRepository.save(p);
		
	}
	public Pedido addPedido(Pedido pedido) {
		pedidoRepository.save(pedido);
		return pedido;
	}
	
	public List<Pedido> getListaPedidos(){
		return pedidoRepository.findAll();
	}
	
	public Pedido getPedidoId(int id) {
		Pedido pedido = pedidoRepository.getById(id);
		return pedido;
	}
	
	public void editPedido(Pedido pedido) {
		pedidoRepository.save(pedido);
	}
	
	public List<Pedido> getPedidoByIdUsuario(int id) {
		return pedidoRepository.buscarPedidoPorUsuario(id);
	}
	
	public void cancelarPedidoById(int id) {
		pedidoRepository.cancelarPedidoPorId(id);
	}
	
	public void cancelarPedido(Pedido pedido) {
		pedidoRepository.save(pedido);
	}
	
	public Pedido getPedidoById(int id) {
		Pedido pedido = pedidoRepository.findById(id);
		return pedido;
	}
	
}
