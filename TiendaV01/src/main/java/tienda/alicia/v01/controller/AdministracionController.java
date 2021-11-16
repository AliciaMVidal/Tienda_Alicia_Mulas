package tienda.alicia.v01.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import tienda.alicia.v01.model.OpcionesMenu;
import tienda.alicia.v01.model.Usuario;
import tienda.alicia.v01.service.OpcionesMenuService;
import tienda.alicia.v01.service.UsuarioService;

@Controller
@RequestMapping("/administracion")
public class AdministracionController {

	@Autowired
	UsuarioService usuarioServicio;
	@Autowired
	OpcionesMenuService opcionesMenuServicio;
	
	private static Logger logger = LogManager.getLogger(AdministracionController.class);


	@GetMapping("")
	public String inicio(Model model, HttpSession sesion) {
		// Coger el usuario que quiere entrar a la administracion
		Usuario usuario;
		String email = (String) sesion.getAttribute("sesion");

		usuario = usuarioServicio.getUsuarioByEmail(email);
		if (sesion.getAttribute("sesion") == null || usuario.getId_rol() == 3) {
			logger.warn(String.format(" >>>>>> Un cliente ha intentado acceder a la administracion: "));
			return "redirect:/login";
		} else {
			//Lo de antes
			int id_rol = usuario.getId_rol();
			//model.addAttribute("idrol", idrol);
			//con OpcionesMenu
			ArrayList<OpcionesMenu> listaOpciones = new ArrayList<OpcionesMenu>();
			listaOpciones = (ArrayList<OpcionesMenu>) opcionesMenuServicio.getOpcionesPorRol(id_rol);
			model.addAttribute("listaOpciones", listaOpciones);
			logger.info(String.format(" >>>>>> Ir a la administracion: Usuario: "+usuario.getEmail()));
			return "administracion/dashboard";
		}

	}

}
