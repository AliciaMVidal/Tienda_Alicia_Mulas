package tienda.alicia.v01.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import tienda.alicia.v01.model.DetallePedido;
import tienda.alicia.v01.model.OpcionesMenu;
import tienda.alicia.v01.model.Producto;
import tienda.alicia.v01.model.Rol;
import tienda.alicia.v01.model.Usuario;
import tienda.alicia.v01.service.OpcionesMenuService;
import tienda.alicia.v01.service.RolService;
import tienda.alicia.v01.service.UsuarioService;

@Controller
@RequestMapping("/administracion/usuarios")
public class UsuariosController {

	@Autowired
	UsuarioService usuarioService;
	@Autowired
	OpcionesMenuService opcionesMenuServicio;
	@Autowired
	RolService rolService;

	@GetMapping("")
	public String inicio(Model model, HttpSession sesion) {
		// Enviar la lista de usuarios a la vista
		model.addAttribute("listaUsuarios", usuarioService.getListaUsuarios());
		List<Usuario> listaUsuarios = usuarioService.getListaUsuarios();
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
		// Controlar quien puede hacer ciertas acciones
		Usuario usuario;
		String email = (String) sesion.getAttribute("sesion");
		usuario = usuarioService.getUsuarioByEmail(email);
		int idrol = usuario.getId_rol();
		model.addAttribute("idrol", idrol);
		// Para cargar el menu segun el rol del usuario(admin o empleado)
		ArrayList<OpcionesMenu> listaOpciones = new ArrayList<OpcionesMenu>();
		listaOpciones = (ArrayList<OpcionesMenu>) opcionesMenuServicio.getOpcionesPorRol(idrol);
		model.addAttribute("listaOpciones", listaOpciones);
		return "administracion/usuarios";
	}

	@GetMapping("/addusuario")
	public String addUsuario(Model model, HttpSession sesion) {
		// Coger el usuario que quiere va a hacer esto
		Usuario usuario;
		String email = (String) sesion.getAttribute("sesion");
		usuario = usuarioService.getUsuarioByEmail(email);
		int idrol = usuario.getId_rol();
		ArrayList<OpcionesMenu> listaOpciones = new ArrayList<OpcionesMenu>();
		listaOpciones = (ArrayList<OpcionesMenu>) opcionesMenuServicio.getOpcionesPorRol(idrol);
		model.addAttribute("listaOpciones", listaOpciones);
		model.addAttribute("idrol", idrol);
		model.addAttribute("usuario", new Usuario());
		return "administracion/addusuario";
	}

	// Lode antes
	@PostMapping("addusuario/submit")
	public String addUsuarioSubmit(@ModelAttribute Usuario usuario) {
		usuario.setActivo(true);
		Base64 base64 = new Base64();
		String passw = new String(base64.encode(usuario.getClave().getBytes()));
		usuario.setClave(passw);
		usuarioService.addUsuario(usuario);
		return "redirect:/administracion/usuarios";
	}

//	
	@GetMapping("/editusuario/{id}")
	public String editUsuario(@PathVariable int id, Model model, HttpSession sesion) {
		// Coger el usuario que quiere va a hacer esto
		Usuario usuariosesion;
		String email = (String) sesion.getAttribute("sesion");
		usuariosesion = usuarioService.getUsuarioByEmail(email);
		int idrol = usuariosesion.getId_rol();
		model.addAttribute("idrol", idrol);
		ArrayList<OpcionesMenu> listaOpciones = new ArrayList<OpcionesMenu>();
		listaOpciones = (ArrayList<OpcionesMenu>) opcionesMenuServicio.getOpcionesPorRol(idrol);
		model.addAttribute("listaOpciones", listaOpciones);
		Usuario usuario = usuarioService.getUsuarioId(id);
		model.addAttribute("usuario", usuario);
		return "administracion/editusuario";

	}

	@PostMapping("/editusuario/submit")
	public String editUsuarioSubmit(@ModelAttribute Usuario usuario) {
		usuarioService.editUsuario(usuario);
		return "redirect:/administracion/usuarios";
	}

	@GetMapping("/deleteusuario/{id}")
	public String deleteUsuario(@PathVariable int id, Model model) {
		usuarioService.deleteUsuario(id);
		return "redirect:/administracion/usuarios";

	}
	
	@GetMapping("/desactivarusuario/{id}")
	public String desactivarUsuario(@PathVariable int id, Model model) {
		Usuario usuario = usuarioService.getUsuarioId(id);
		usuario.setActivo(false);
		Date date = new Date();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String formattedDate = simpleDateFormat.format(date);
		java.sql.Date date1 = java.sql.Date.valueOf(formattedDate);
		usuarioService.editUsuario(usuario);
		return "redirect:/administracion/usuarios";
	}
	@GetMapping("/activarusuario/{id}")
	public String activarUsuario(@PathVariable int id, Model model) {
		Usuario usuario = usuarioService.getUsuarioId(id);
		usuario.setActivo(true);
		usuarioService.editUsuario(usuario);
		return "redirect:/administracion/usuarios";
	}

}
