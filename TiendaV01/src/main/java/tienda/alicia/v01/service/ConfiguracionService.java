package tienda.alicia.v01.service;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tienda.alicia.v01.model.Configuracion;
import tienda.alicia.v01.model.Pedido;
import tienda.alicia.v01.repository.ConfiguracionRepository;

@Service
public class ConfiguracionService {

	@Autowired
	ConfiguracionRepository configuracionRepository;
	
	@PostConstruct
	public void cargarConfiguracion() {
		Configuracion confi = new Configuracion("Nombre", "Tienda", "texto");
		configuracionRepository.save(confi);
		confi = new Configuracion("Cif","B-123456", "texto");
		configuracionRepository.save(confi);
		confi = new Configuracion("NFactura","0001", "texto");
		configuracionRepository.save(confi);
	
	}
	
	public List<Configuracion> getTodasConfiguraciones(){
		return configuracionRepository.findAll();
	}
	
	public void editConfiguracion(Configuracion configuracion) {
		configuracionRepository.save(configuracion);
	}
	
	public Configuracion verNumeroFactura(String clave) {
		return configuracionRepository.findByClave(clave);
	}
	
}
