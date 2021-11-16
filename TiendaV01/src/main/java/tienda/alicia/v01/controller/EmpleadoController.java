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

import tienda.alicia.v01.model.OpcionesMenu;
import tienda.alicia.v01.model.Usuario;
import tienda.alicia.v01.service.OpcionesMenuService;
import tienda.alicia.v01.service.UsuarioService;

@Controller
@RequestMapping("administracion/empleados")
public class EmpleadoController {

	@Autowired
	UsuarioService usuarioService;
	@Autowired
	OpcionesMenuService opcionesMenuServicio;
	
	private static Logger logger = LogManager.getLogger(EmpleadoController.class);

	
	@GetMapping("")
	public String inicio(Model model,HttpSession sesion) {
		model.addAttribute("listaEmpleados", usuarioService.getListaEmpleados(2));
		// Coger el usuario que quiere va a hacer esto
		Usuario usuario;
		String email = (String) sesion.getAttribute("sesion");
		usuario = usuarioService.getUsuarioByEmail(email);
		int idrol = usuario.getId_rol();
		model.addAttribute("idrol", idrol);
		ArrayList<OpcionesMenu> listaOpciones = new ArrayList<OpcionesMenu>();
		listaOpciones = (ArrayList<OpcionesMenu>) opcionesMenuServicio.getOpcionesPorRol(idrol);
		model.addAttribute("listaOpciones", listaOpciones);
		logger.info(String.format(" >>>>>> Cargando la lista de empleados"));

		return "administracion/empleados";
	}
	
	@GetMapping("/addempleado")
	public String addEmpleado(Model model, HttpSession sesion) {
		model.addAttribute("usuario", new Usuario());
		Usuario usuario;
		String email = (String) sesion.getAttribute("sesion");
		usuario = usuarioService.getUsuarioByEmail(email);
		int idrol = usuario.getId_rol();
		model.addAttribute("idrol", idrol);
		ArrayList<OpcionesMenu> listaOpciones = new ArrayList<OpcionesMenu>();
		listaOpciones = (ArrayList<OpcionesMenu>) opcionesMenuServicio.getOpcionesPorRol(idrol);
		model.addAttribute("listaOpciones", listaOpciones);
		logger.info(String.format(" >>>>>> Cargando formulario para añadir empleados"));

		return "administracion/addempleado";
	}
	
	@PostMapping("addempleado/submit")
	public String addEmpleadoSubmit(@ModelAttribute Usuario usuario) {
		usuarioService.addUsuario(usuario);
		logger.info(String.format(" >>>>>> Empleado añadido: "+usuario.toString()));
		return "redirect:/administracion/empleados";
	}
	
	@GetMapping("/editempleado/{id}")
	public String editEmpleado(@PathVariable int id, Model model,HttpSession sesion) {
		Usuario usuario = usuarioService.getUsuarioId(id);
		model.addAttribute("usuario", usuario);
		//
		Usuario usuarioo;
		String email = (String) sesion.getAttribute("sesion");
		usuarioo = usuarioService.getUsuarioByEmail(email);
		int idrol = usuarioo.getId_rol();
		model.addAttribute("idrol", idrol);
		ArrayList<OpcionesMenu> listaOpciones = new ArrayList<OpcionesMenu>();
		listaOpciones = (ArrayList<OpcionesMenu>) opcionesMenuServicio.getOpcionesPorRol(idrol);
		model.addAttribute("listaOpciones", listaOpciones);
		logger.info(String.format(" >>>>>> Cargando formulario para editar el empleado: "+usuario.toString()));
		return "administracion/editempleado";
		
	}
	
	@PostMapping("/editempleado/submit")
	public String editEmpleadoSubmit(@ModelAttribute Usuario usuario) {
		usuarioService.editUsuario(usuario);
		logger.info(String.format(" >>>>>> Empleado editado: "+usuario.toString()));
		return "redirect:/administracion/empleados";	
	}
}
