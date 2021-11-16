package tienda.alicia.v01.service;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tienda.alicia.v01.model.Rol;
import tienda.alicia.v01.repository.RolRepository;

@Service
public class RolService {
	
	@Autowired
	RolRepository rolRepository;
	
	@PostConstruct
	public void cargarRoles() {
		Rol r = new Rol("admin");
		rolRepository.save(r);
		r = new Rol("empleado");
		rolRepository.save(r);
		r = new Rol("cliente");
		rolRepository.save(r);
		
	}

}
