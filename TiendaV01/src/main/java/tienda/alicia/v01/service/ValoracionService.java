package tienda.alicia.v01.service;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tienda.alicia.v01.model.Valoracion;
import tienda.alicia.v01.repository.ValoracionRepository;

@Service
public class ValoracionService {
	
	@Autowired
	ValoracionRepository valoracionRepository;
	
	@PostConstruct
	public void cargarValoraciones() {
		Valoracion valoracion = new Valoracion(1, 4, 5.0, "Mi perro esta muy contento" );
		valoracionRepository.save(valoracion);
		valoracion = new Valoracion(1, 4, 4.0, "Bien");
		valoracionRepository.save(valoracion);	
	}
	
	public List<Valoracion> getTodasValoracion(){
		return valoracionRepository.findAll();
	}
	
	public void addValoracion(Valoracion valoracion) {
		valoracionRepository.save(valoracion);
	}
	
	public List<Valoracion> getValoracionesDeUnProducto(int id){
		return valoracionRepository.listaValoracion(id);
	}
	
	
}
