package tienda.alicia.v01.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;
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
import tienda.alicia.v01.model.Rol;
import tienda.alicia.v01.model.Usuario;
import tienda.alicia.v01.service.OpcionesMenuService;
import tienda.alicia.v01.service.RolService;
import tienda.alicia.v01.service.UsuarioService;

@Controller
@RequestMapping("administracion/empleados")
public class EmpleadoController {

	@Autowired
	UsuarioService usuarioService;
	@Autowired
	OpcionesMenuService opcionesMenuServicio;
	@Autowired
	RolService rolService;
	
	private static Logger logger = LogManager.getLogger(EmpleadoController.class);

	
	@GetMapping("")
	public String inicio(Model model,HttpSession sesion) {
		model.addAttribute("listaEmpleados", usuarioService.getListaEmpleados(2));
		List<Usuario> listaUsuarios = usuarioService.getListaEmpleados(2);
		// Cargar el nombre del rol que sale en la tabla srgundo el id_rol
		// Se saca una lista de todos los roles que hay
		List<Rol> listaRoles = rolService.getTodosRoles();
		// Se crea una lista donde se van a almacenar todos los ids de los roles que
		// tiene un usuario
		ArrayList<Integer> listadeIdsRolEnUsuario = new ArrayList();
		// Se recorre la lista de los usuarios que hay
		// y se guardan los ids de los roles de cada usuario
		for (Usuario usuario : listaUsuarios) {
			listadeIdsRolEnUsuario.add(usuario.getId_rol());
		}
		// Se crea un hashmap donde se guarda el id del rol que esta en el usuario
		// y el nombre del rol de la tabla rol
		// Se pasa como parametro la lista de los ids roles que estan en en el
		// usuarios
		HashMap<Integer, String> listaIdRolEnRolUsuario = rolService.listaderolesids(listadeIdsRolEnUsuario);
		//Se pasa como atributo a la vista
		model.addAttribute("nombrerol", listaIdRolEnRolUsuario);
		
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
		usuario.setActivo(true);
		Base64 base64 = new Base64();
		String passw = new String(base64.encode(usuario.getClave().getBytes()));
		usuario.setClave(passw);
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
	
	@GetMapping("/deleteempleado/{id}")
	public String deleteUEmpleado(@PathVariable int id, Model model) {
		usuarioService.deleteUsuario(id);
		return "redirect:/administracion/empleados";

	}
	
	@GetMapping("/desactivarempleado/{id}")
	public String desactivarEmpleado(@PathVariable int id, Model model) {
		Usuario usuario = usuarioService.getUsuarioId(id);
		usuario.setActivo(false);
		usuarioService.editUsuario(usuario);
		return "redirect:/administracion/empleados";
	}
	@GetMapping("/activarempleado/{id}")
	public String activarEmpleado(@PathVariable int id, Model model) {
		Usuario usuario = usuarioService.getUsuarioId(id);
		usuario.setActivo(true);
		usuarioService.editUsuario(usuario);
		return "redirect:/administracion/empleados";
	}
}
