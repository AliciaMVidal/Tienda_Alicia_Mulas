package tienda.alicia.v01.service;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tienda.alicia.v01.model.Impuesto;
import tienda.alicia.v01.repository.ImpuestoRepository;

@Service
public class ImpuestoService {
	
	 @Autowired
	 ImpuestoRepository impuestoRespository;
	 
	 @PostConstruct
	 public void cargarImpuestos() {
		 Impuesto impuesto = new Impuesto(21);
		 impuestoRespository.save(impuesto);
		 impuesto = new Impuesto(10);
		 impuestoRespository.save(impuesto);
	 }
	 
	 public List<Impuesto> getTodosImpuestos(){
		 return impuestoRespository.findAll();
	 }
	 
	 public void addImpuesto(Impuesto impuesto) {
		 impuestoRespository.save(impuesto);
	 }
	 
	 public void editImpuesto(Impuesto impuesto) {
		 impuestoRespository.save(impuesto);
	 }
	 

}
