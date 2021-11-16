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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import tienda.alicia.v01.model.Categoria;
import tienda.alicia.v01.model.OpcionesMenu;
import tienda.alicia.v01.model.Producto;
import tienda.alicia.v01.model.Usuario;
import tienda.alicia.v01.service.CategoriaService;
import tienda.alicia.v01.service.OpcionesMenuService;
import tienda.alicia.v01.service.UsuarioService;

@Controller
@RequestMapping("/administracion/categorias")
public class CategoriaController {
	
	@Autowired
	UsuarioService usuarioServicio;
	@Autowired
	CategoriaService categoriaService;
	@Autowired
	OpcionesMenuService opcionesMenuServicio;
	
	private static Logger logger = LogManager.getLogger(CategoriaController.class);

	
	@GetMapping("")
	public String inicio(Model model, HttpSession sesion) {
		model.addAttribute("listaCategorias", categoriaService.getTodasCategorias());
		// Coger el usuario que quiere va a hacer esto
		Usuario usuario;
		String email = (String) sesion.getAttribute("sesion");
		usuario = usuarioServicio.getUsuarioByEmail(email);
		int idrol = usuario.getId_rol();
		model.addAttribute("idrol", idrol);
		ArrayList<OpcionesMenu> listaOpciones = new ArrayList<OpcionesMenu>();
		listaOpciones = (ArrayList<OpcionesMenu>) opcionesMenuServicio.getOpcionesPorRol(idrol);
		model.addAttribute("listaOpciones", listaOpciones);
		logger.info(String.format(" >>>>>> Cargando la lista de categorias"));
		return "administracion/categorias";
	}
	

	@GetMapping("/addcategoria")
	public String addProducto(Model model, HttpSession sesion) {
		model.addAttribute("categoria", new Categoria());
		Usuario usuario;
		String email = (String) sesion.getAttribute("sesion");
		usuario = usuarioServicio.getUsuarioByEmail(email);
		int idrol = usuario.getId_rol();
		model.addAttribute("idrol", idrol);
		ArrayList<OpcionesMenu> listaOpciones = new ArrayList<OpcionesMenu>();
		listaOpciones = (ArrayList<OpcionesMenu>) opcionesMenuServicio.getOpcionesPorRol(idrol);
		model.addAttribute("listaOpciones", listaOpciones);
		logger.info(String.format(" >>>>>> Cargar formulario para crear una categoria nueva"));
		return "administracion/addcategoria";
	}

	@PostMapping("/addcategoria/submit")
	public String addProductoSubmit(@ModelAttribute Categoria categoria) {
		categoriaService.addCategoria(categoria);
		logger.info(String.format(" >>>>>> Categoria nueva creada "+categoria.toString()));
		return "redirect:/administracion/categorias";
	}

}
