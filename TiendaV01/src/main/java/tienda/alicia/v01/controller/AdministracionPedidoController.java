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

import tienda.alicia.v01.model.Configuracion;
import tienda.alicia.v01.model.OpcionesMenu;
import tienda.alicia.v01.model.Pedido;
import tienda.alicia.v01.model.Producto;
import tienda.alicia.v01.model.Usuario;
import tienda.alicia.v01.service.ConfiguracionService;
import tienda.alicia.v01.service.OpcionesMenuService;
import tienda.alicia.v01.service.PedidoDetalleService;
import tienda.alicia.v01.service.PedidoService;
import tienda.alicia.v01.service.UsuarioService;

@Controller
@RequestMapping("/administracion/pedidos")
public class AdministracionPedidoController {

	@Autowired
	PedidoService pedidoServicio;
	@Autowired
	PedidoDetalleService detallePedidoServicio;
	@Autowired
	UsuarioService usuarioServicio;
	@Autowired
	OpcionesMenuService opcionesMenuServicio;
	@Autowired
	ConfiguracionService configuracionServicio;
	
	private static Logger logger = LogManager.getLogger(AdministracionPedidoController.class);
	
	@GetMapping("")
	public String inicio(Model model, HttpSession sesion) {
		model.addAttribute("listaPedidos", pedidoServicio.getListaPedidos());
		// Coger el usuario que quiere va a hacer esto
		Usuario usuario;
		String email = (String) sesion.getAttribute("sesion");
		usuario = usuarioServicio.getUsuarioByEmail(email);
		int idrol = usuario.getId_rol();
		model.addAttribute("idrol", idrol);
		ArrayList<OpcionesMenu> listaOpciones = new ArrayList<OpcionesMenu>();
		listaOpciones = (ArrayList<OpcionesMenu>) opcionesMenuServicio.getOpcionesPorRol(idrol);
		model.addAttribute("listaOpciones", listaOpciones);
		logger.info(String.format(" >>>>>> Cargando la lista de pedidos"));
		return "administracion/pedidos";

	}

	@GetMapping("/editpedido/{id}")
	public String editPedido(@PathVariable int id, Model model, HttpSession sesion) {
		Pedido pedido = pedidoServicio.getPedidoId(id);
		Usuario usuario;
		String email = (String) sesion.getAttribute("sesion");
		usuario = usuarioServicio.getUsuarioByEmail(email);
		int idrol = usuario.getId_rol();
		model.addAttribute("idrol", idrol);
		ArrayList<OpcionesMenu> listaOpciones = new ArrayList<OpcionesMenu>();
		listaOpciones = (ArrayList<OpcionesMenu>) opcionesMenuServicio.getOpcionesPorRol(idrol);
		model.addAttribute("listaOpciones", listaOpciones);
		model.addAttribute("pedido", pedido);
		logger.info(String.format(" >>>>>> Editar pedido: "+id));
		return "administracion/editpedido";
	}

	@PostMapping("/editpedido/submit")
	public String editPedidoSubmit(@ModelAttribute Pedido pedido) {
		pedidoServicio.editPedido(pedido);
		System.out.println(pedido.getEstado());
		logger.info(String.format(" >>>>>> Editar pedido: "+pedido.getId()));
		return "redirect:/administracion/pedidos";
	}
	
	@GetMapping("/enviarpedido/{id}")
	public String enviarPedido(@PathVariable int id, Model model) {
		Pedido pedido = pedidoServicio.getPedidoById(id);
		pedido.setEstado("enviado");
		pedidoServicio.cancelarPedido(pedido);
		Configuracion configuracion = configuracionServicio.verNumeroFactura("NFactura");
		int facturaString = Integer.parseInt(configuracion.getValor());
		System.out.println(facturaString);
		pedido.setNum_factura(String.valueOf(facturaString+1));
		configuracion.setValor(String.valueOf(facturaString+1));
		configuracionServicio.editConfiguracion(configuracion);
		pedidoServicio.editPedido(pedido);
		logger.info(String.format(" >>>>>> El pedido: "+pedido.getId()+" ha sido enviado"));
		return "redirect:/administracion/pedidos";
	}

	@GetMapping("/cancelarpedido/{id}")
	public String cancelarPedido(@PathVariable int id, Model model) {
		Pedido pedido = pedidoServicio.getPedidoById(id);
		pedido.setEstado("cancelado");
		pedidoServicio.cancelarPedido(pedido);
		logger.info(String.format(" >>>>>> El pedido: "+pedido.getId()+" ha sido cancelado"));
		return "redirect:/administracion/pedidos";
	}
}
