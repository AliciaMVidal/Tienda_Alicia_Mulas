package tienda.alicia.v01.service;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tienda.alicia.v01.model.Categoria;
import tienda.alicia.v01.model.OpcionesMenu;
import tienda.alicia.v01.repository.OpcionesMenuRespository;

@Service
public class OpcionesMenuService {
	
	@Autowired
	OpcionesMenuRespository opcionesRepository;
	
	@PostConstruct
	public void cargarOpciones() {
		OpcionesMenu opcionesMenu;
		opcionesMenu = new OpcionesMenu(1, "Inicio" , "/administracion");
		opcionesRepository.save(opcionesMenu);
		opcionesMenu = new OpcionesMenu(1, "Usuarios" , "/administracion/usuarios");
		opcionesRepository.save(opcionesMenu);
		opcionesMenu = new OpcionesMenu(1, "Productos" , "/administracion/productos");
		opcionesRepository.save(opcionesMenu);
		opcionesMenu = new OpcionesMenu(1, "Pedidos" , "/administracion/pedidos");
		opcionesRepository.save(opcionesMenu);
		opcionesMenu = new OpcionesMenu(1, "Empleados" , "/administracion/empleados");
		opcionesRepository.save(opcionesMenu);
		opcionesMenu = new OpcionesMenu(1, "Categorias" , "/administracion/categorias");
		opcionesRepository.save(opcionesMenu);
		opcionesMenu = new OpcionesMenu(2, "Inicio" , "/administracion");
		opcionesRepository.save(opcionesMenu);
		opcionesMenu = new OpcionesMenu(2, "Usuarios" , "/administracion/usuarios");
		opcionesRepository.save(opcionesMenu);
		opcionesMenu = new OpcionesMenu(2, "Productos" , "/administracion/productos");
		opcionesRepository.save(opcionesMenu);
		opcionesMenu = new OpcionesMenu(2, "Pedidos" , "/administracion/pedidos");
		opcionesRepository.save(opcionesMenu);
		opcionesMenu = new OpcionesMenu(2, "Categorias" , "/administracion/categorias");
		opcionesRepository.save(opcionesMenu);
	}


	public List<OpcionesMenu> getTodasOpciones(){
		return opcionesRepository.findAll();
	}
	
	public List<OpcionesMenu> getOpcionesPorRol(int id_rol){
		return opcionesRepository.getOpcionesPorRol(id_rol);
	}
}
