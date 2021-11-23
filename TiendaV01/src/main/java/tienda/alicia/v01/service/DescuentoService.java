package tienda.alicia.v01.service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tienda.alicia.v01.model.Descuento;
import tienda.alicia.v01.repository.DescuentoRepository;

@Service
public class DescuentoService {
	
	@Autowired
	DescuentoRepository descuentoRepository;
	
	@PostConstruct
	public void cargarDescuentos() {
		Date date = new Date();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String formattedDate = simpleDateFormat.format(date);
		java.sql.Date fechaInicio = java.sql.Date.valueOf(formattedDate);
		LocalDate diaActual = fechaInicio.toLocalDate();
		LocalDate mesDespues = diaActual.plusMonths(1);
		java.sql.Date fechaFinal = java.sql.Date.valueOf(mesDespues);
		Descuento descuento = new Descuento("gatito", 12, fechaInicio, fechaFinal);
		descuentoRepository.save(descuento);
	}
	

	public List<Descuento> getTodosDescuentos(){
		return descuentoRepository.findAll();
	}
	
	public void addDescuento(Descuento descuento) {
		descuentoRepository.save(descuento);
	}
	
	public void editDescuento(Descuento descuento) {
		descuentoRepository.save(descuento);
	}
	
	public void deleteDescuento(Descuento descuento) {
		descuentoRepository.delete(descuento);
	}
	
}
