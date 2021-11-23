package tienda.alicia.v01.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tienda.alicia.v01.model.MetodoPago;
import tienda.alicia.v01.repository.MetodoPagoRepository;

@Service
public class MetodoPagoService {
	
	@Autowired
	
	MetodoPagoRepository metodoPagoRepository;
	
	public void cargarMetodos() {
		MetodoPago metodoPago = new MetodoPago("Tarjeta");
		metodoPagoRepository.save(metodoPago);
		metodoPago = new MetodoPago("Paypal");
		metodoPagoRepository.save(metodoPago);
	}
	
	public List<MetodoPago> getTodosMetodoPago(){
		return metodoPagoRepository.findAll();
	}
	
	public void addMetodoPago(MetodoPago metodoPago) {
		metodoPagoRepository.save(metodoPago);
	}
	
	public void editMetodoPago(MetodoPago metodoPago) {
		metodoPagoRepository.save(metodoPago);
	}

}
