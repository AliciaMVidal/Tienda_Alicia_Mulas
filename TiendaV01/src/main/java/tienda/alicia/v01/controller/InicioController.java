package tienda.alicia.v01.controller;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.w3c.dom.ls.LSInput;

import com.zaxxer.hikari.util.SuspendResumeLock;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import tienda.alicia.v01.model.Categoria;
import tienda.alicia.v01.model.Producto;
import tienda.alicia.v01.model.Proveedor;
import tienda.alicia.v01.model.Usuario;
import tienda.alicia.v01.model.Valoracion;
import tienda.alicia.v01.service.CategoriaService;
import tienda.alicia.v01.service.ProductoService;
import tienda.alicia.v01.service.UsuarioService;
import tienda.alicia.v01.service.ValoracionService;
import tienda.alicia.v01.util.Buscador;
import tienda.alicia.v01.util.ObjetoBuscador;

@Controller
@RequestMapping("")
public class InicioController {

	@Autowired
	ProductoService productoService;
	@Autowired
	CategoriaService categoriaService;
	@Autowired
	ValoracionService valoracionService;
	@Autowired
	UsuarioService usuarioService;

	// private ArrayList carritolista = new ArrayList();
	private static Logger logger = LogManager.getLogger(InicioController.class);


	@GetMapping("/")
	public String inicio(Model model, HttpSession sesion) {
		ArrayList carritolista = (ArrayList) sesion.getAttribute("carritosesion");
		if (sesion.getAttribute("carritosesion") == null) {
			 carritolista = new ArrayList();
		}
		sesion.setAttribute("carritosesion", carritolista);
		model.addAttribute("listaProductos", productoService.getListaProductos());
		sesion.setAttribute("numerodecosascarrito", carritolista.size());
		//Añadir las opciones del buscador
		//model.addAttribute("buscador", new Buscador());
		ArrayList<ObjetoBuscador> listaBuscador = new ArrayList<ObjetoBuscador>();
		ObjetoBuscador ob = new ObjetoBuscador("Precio:Caro");
		listaBuscador.add(ob);	
		ob = new ObjetoBuscador("Precio:Barato");
		listaBuscador.add(ob);	
		ob = new ObjetoBuscador("Gato");
		listaBuscador.add(ob);	
		ob = new ObjetoBuscador("Perro");
		listaBuscador.add(ob);	
		ob = new ObjetoBuscador("Peces");
		listaBuscador.add(ob);	
		ob = new ObjetoBuscador("Pajaro");
		listaBuscador.add(ob);	

		model.addAttribute("buscador", listaBuscador);
		logger.info(String.format(" >>>>>> Tienda iniciada. Index"));
		return "index";
	}
	
	//Para el bsucador
    @PostMapping("/buscar")
    public String buscadorSeleccionado(@ModelAttribute("objetobuscador") ObjetoBuscador objeto, Model model) {
    	System.out.println(objeto.getOpcion());
    	if(objeto.getOpcion().equals("Precio:Caro")) {
    		model.addAttribute("listaProductos", productoService.getListaProductosCaro());
    		logger.info(String.format(" >>>>>> Busqueda por: "+ objeto.getOpcion()));
    	}
    	if(objeto.getOpcion().equals("Precio:Barato")) {
    		model.addAttribute("listaProductos", productoService.getListaProductosBarato());
    		logger.info(String.format(" >>>>>> Busqueda por: "+ objeto.getOpcion()));
    	}
    	if(objeto.getOpcion().equals("Gato")) {
    		Categoria categoria = categoriaService.getIdCategoriaNombre("Gatos");
    		System.out.println(categoria.getId());
    		model.addAttribute("listaProductos", productoService.getListaProductoCategoria(categoria.getId()));
    		logger.info(String.format(" >>>>>> Busqueda por: "+ objeto.getOpcion()));
    	}
    	if(objeto.getOpcion().equals("Perro")) {
    		Categoria categoria = categoriaService.getIdCategoriaNombre("Perros");
    		System.out.println(categoria.getId());
    		model.addAttribute("listaProductos", productoService.getListaProductoCategoria(categoria.getId()));
    		logger.info(String.format(" >>>>>> Busqueda por: "+ objeto.getOpcion()));
    	}
    	if(objeto.getOpcion().equals("Peces")) {
    		Categoria categoria = categoriaService.getIdCategoriaNombre("Peces");
    		System.out.println(categoria.getId());
    		model.addAttribute("listaProductos", productoService.getListaProductoCategoria(categoria.getId()));
    		logger.info(String.format(" >>>>>> Busqueda por: "+ objeto.getOpcion()));
    	}
    	if(objeto.getOpcion().equals("Pajaro")) {
    		Categoria categoria = categoriaService.getIdCategoriaNombre("Pajaros");
    		System.out.println(categoria.getId());
    		model.addAttribute("listaProductos", productoService.getListaProductoCategoria(categoria.getId()));
    		logger.info(String.format(" >>>>>> Busqueda por: "+ objeto.getOpcion()));
    	}
        return "busqueda";
    }

	@GetMapping("/carrito/{id}")
	public String carrito(@PathVariable int id, Model model, HttpSession sesion) {
		System.out.println("estoy aqui");
		System.out.println(id);
		ArrayList carritolista = (ArrayList) sesion.getAttribute("carritosesion");
		carritolista.add(id);
		// addCarrito(carritolista, id);
		//System.out.println(carritolista.toString());
		logger.info(String.format(" >>>>>> Se ha añadido al carrito el producto ID: "+id));

		// return "index";
		return "redirect:/";
	}

	public void addCarrito(ArrayList carrito, int id) {
		carrito.add(id);
		// System.out.println(id);
	}
	
	
	@GetMapping("/cerrarsesion")
	public String cerrarsesion(Model model, HttpSession sesion) {
		sesion.setAttribute("sesion", null);
		logger.info(String.format(" >>>>>> Se ha cerrado la sesion"));
		return "redirect:/";
		
	}
	
	@GetMapping("/vistaproducto/{id}")
	public String vistaProducto(@PathVariable int id,Model model) {
		Producto producto = productoService.getProductoPorId(id);
		List<Valoracion> listaValoraciones = valoracionService.getValoracionesDeUnProducto(id);
		//Hacer media de reviews
		boolean tieneValoraciones;
		double valoracionSuma = 0;
		int cantidadValoraciones = listaValoraciones.size();
		for (Valoracion valoracion : listaValoraciones) {
			valoracionSuma += valoracion.getValoracion();
		}
		double valoracionMedia = valoracionSuma/cantidadValoraciones;
		if(cantidadValoraciones > 0) {
			tieneValoraciones = true; 
		}else {
			tieneValoraciones = false;
		}
		
		
		//Lista de usuarios que hacen valoraciones
		List<Usuario> listaUsuario = usuarioService.getListaUsuarios();
		
		
		ArrayList<Integer> listadeIdsUsuarioEnValoracion = new ArrayList();
		
		
		for (Valoracion valoracion : listaValoraciones) {
			listadeIdsUsuarioEnValoracion.add(valoracion.getId_Usuario());
		}
		
		HashMap<Integer, String> listaIdCategoriaEnProducto = usuarioService.listaUsuariosIdsEnValoraciones(listadeIdsUsuarioEnValoracion);
	
		// Se pasa como atributo a la vista
		model.addAttribute("nombreUsuario", listaIdCategoriaEnProducto);
	
		//
		model.addAttribute("tieneValoraciones", tieneValoraciones);
		model.addAttribute("valoracionMedia", valoracionMedia);
		model.addAttribute("cantidadValoraciones", cantidadValoraciones);
		model.addAttribute("listaValoraciones", valoracionService.getValoracionesDeUnProducto(id));
		logger.info(String.format(" >>>>>> Ir a la vista del producto: "+id));
		model.addAttribute("producto", producto);
		return "vistaproducto";
		
	}
}
