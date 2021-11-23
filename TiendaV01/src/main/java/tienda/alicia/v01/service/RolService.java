package tienda.alicia.v01.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tienda.alicia.v01.model.Producto;
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
	
	public List<Rol> getTodosRoles(){
		return rolRepository.findAll();
	}
	
	// metodo para obtener el nombre del rol (rol.rol)
	// de un array de ids de rol que estan en la tabla de usuarios (usuarios.id_rol)
	
	public HashMap<Integer, String> listaderolesids(ArrayList listadeIdsRolEnUsuario) {
		// Se crea una lista de los Roles cuya id esten en la tabla Usuarios
		List<Rol> listaObjetosRolenUsuarios = rolRepository.findByIdIn(listadeIdsRolEnUsuario);
		// Se crea un HashMap con la relacion de usuario.id_rol y rol.id
		HashMap<Integer, String> hmRelacionRolYUsuario = new HashMap<Integer, String>();
		// Se recorren los objetos para sacar el id.rol y guardarlo en el hashmap
		for (Rol rol : listaObjetosRolenUsuarios) {
			hmRelacionRolYUsuario.put(rol.getId(), rol.getRol());
		}
		return hmRelacionRolYUsuario;
	}
	

}
