package tienda.alicia.v01.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import tienda.alicia.v01.model.Categoria;
import tienda.alicia.v01.model.OpcionesMenu;
import tienda.alicia.v01.model.Proveedor;
import tienda.alicia.v01.model.Usuario;
import tienda.alicia.v01.service.CategoriaService;
import tienda.alicia.v01.service.OpcionesMenuService;
import tienda.alicia.v01.service.ProveedorService;
import tienda.alicia.v01.service.UsuarioService;

@Controller
@RequestMapping("/administracion/proveedores")
public class ProveedorController {
	
	@Autowired
	UsuarioService usuarioServicio;
	@Autowired
	ProveedorService proveedorService;
	@Autowired
	OpcionesMenuService opcionesMenuServicio;
	
	private static Logger logger = LogManager.getLogger(ProveedorController.class);

	@GetMapping("")
	public String inicio(Model model, HttpSession sesion) {
		model.addAttribute("listaProveedores", proveedorService.getTodosProveedores());
		// Coger el usuario que quiere va a hacer esto
		Usuario usuario;
		String email = (String) sesion.getAttribute("sesion");
		usuario = usuarioServicio.getUsuarioByEmail(email);
		int idrol = usuario.getId_rol();
		model.addAttribute("idrol", idrol);
		ArrayList<OpcionesMenu> listaOpciones = new ArrayList<OpcionesMenu>();
		listaOpciones = (ArrayList<OpcionesMenu>) opcionesMenuServicio.getOpcionesPorRol(idrol);
		model.addAttribute("listaOpciones", listaOpciones);
		logger.info(String.format(" >>>>>> Cargando la lista de proveedores"));
		return "administracion/proveedores";
	}
	

	@GetMapping("/addproveedor")
	public String addProveedor(Model model, HttpSession sesion) {
		model.addAttribute("proveedor", new Proveedor());
		Usuario usuario;
		String email = (String) sesion.getAttribute("sesion");
		usuario = usuarioServicio.getUsuarioByEmail(email);
		int idrol = usuario.getId_rol();
		model.addAttribute("idrol", idrol);
		ArrayList<OpcionesMenu> listaOpciones = new ArrayList<OpcionesMenu>();
		listaOpciones = (ArrayList<OpcionesMenu>) opcionesMenuServicio.getOpcionesPorRol(idrol);
		model.addAttribute("listaOpciones", listaOpciones);
		logger.info(String.format(" >>>>>> Cargar formulario para crear un proveedor nuevo"));
		return "administracion/addproveedor";
	}
	
	@PostMapping("/addproveedor/submit")
	public String addProveedorSubmit(@ModelAttribute Proveedor proveedor) {
		proveedorService.addProveedor(proveedor);
		logger.info(String.format(" >>>>>> Proveedor nueva creada "+proveedor.toString()));
		return "redirect:/administracion/proveedores";
	}
	
	@GetMapping("/editproveedor/{id}")
	public String editProveedor(@PathVariable int id, Model model,HttpSession sesion) {
		Proveedor proveedor = proveedorService.getProveedorById(id);
		model.addAttribute("proveedor", proveedor);
		//
		Usuario usuarioo;
		String email = (String) sesion.getAttribute("sesion");
		usuarioo = usuarioServicio.getUsuarioByEmail(email);
		int idrol = usuarioo.getId_rol();
		model.addAttribute("idrol", idrol);
		ArrayList<OpcionesMenu> listaOpciones = new ArrayList<OpcionesMenu>();
		listaOpciones = (ArrayList<OpcionesMenu>) opcionesMenuServicio.getOpcionesPorRol(idrol);
		model.addAttribute("listaOpciones", listaOpciones);
		logger.info(String.format(" >>>>>> Cargando formulario para editar el proveedor: "+proveedor.toString()));
		return "administracion/editproveedor";
		
	}
	
	@PostMapping("/editproveedor/submit")
	public String editProveedorSubmit(@ModelAttribute Proveedor proveedor) {
		proveedorService.editProveedor(proveedor);
		logger.info(String.format(" >>>>>> Proveedor editado: "+proveedor.toString()));
		return "redirect:/administracion/proveedores";	
	}
	
}
