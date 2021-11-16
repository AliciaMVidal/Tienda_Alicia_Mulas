package tienda.alicia.v01.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
@RequestMapping("/administracion/usuarios")
public class UsuariosController {
	
	@Autowired
	UsuarioService usuarioService;
	@Autowired
	OpcionesMenuService opcionesMenuServicio;

	@GetMapping("")
	public String inicio(Model model,HttpSession sesion) {
		model.addAttribute("listaUsuarios", usuarioService.getListaUsuarios());
		// Coger el usuario que quiere va a hacer esto
		Usuario usuario;
		String email = (String) sesion.getAttribute("sesion");
		usuario = usuarioService.getUsuarioByEmail(email);
		int idrol = usuario.getId_rol();
		model.addAttribute("idrol", idrol);
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
	

	//Lode antes
	@PostMapping("addusuario/submit")
	public String addUsuarioSubmit(@ModelAttribute Usuario usuario) {
		usuarioService.addUsuario(usuario);
		return "redirect:/administracion/usuarios";
	}
//	
	@GetMapping("/editusuario/{id}")
	public String editUsuario(@PathVariable int id, Model model,HttpSession sesion) {
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
	
	
}
