package tienda.alicia.v01.controller;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.annotation.DeterminableImports;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import tienda.alicia.v01.model.DetallePedido;
import tienda.alicia.v01.model.MetodoPago;
import tienda.alicia.v01.model.Pedido;
import tienda.alicia.v01.model.Producto;
import tienda.alicia.v01.model.Usuario;
import tienda.alicia.v01.service.PedidoDetalleService;
import tienda.alicia.v01.service.PedidoService;
import tienda.alicia.v01.service.ProductoService;
import tienda.alicia.v01.service.UsuarioService;

@Controller
@RequestMapping("/compra")
public class PedidoController {

	@Autowired
	ProductoService productoServicio;
	@Autowired
	UsuarioService usuarioServicio;
	@Autowired
	PedidoService pedidoServicio;
	@Autowired
	PedidoDetalleService detalleServicio;

	private static Logger logger = LogManager.getLogger(PedidoController.class);

	private ArrayList carritolista = new ArrayList();

	@GetMapping("")
	public String inicio(Model model, HttpSession sesion) {
		carritolista = (ArrayList) sesion.getAttribute("carritosesion");
		System.out.println(carritolista.toString());
		ArrayList<Producto> productocarro = null;
		productocarro = productoServicio.getProductosPorIdCarrito(carritolista);
		// Calcular el total del pedido
		double total = 0.0;
		for (Producto producto : productocarro) {
			double precio = producto.getPrecio();
			total += precio;
			System.out.println(total);
		}
		System.out.println(total);
		model.addAttribute("preciototal", total);
		model.addAttribute("carritoproductos", productocarro);
		logger.info(String.format(" >>>>>> Cargando el carrito de la compra"));

		return "carro";
	}
	
	@GetMapping("/metodopago")
	public String resumen(Model model, HttpSession sesion) {
		if (sesion.getAttribute("sesion") != null) {
			//Pasar el metodo de pago
			 model.addAttribute("metodopago", new MetodoPago());
			carritolista = (ArrayList) sesion.getAttribute("carritosesion");
			ArrayList<Producto> productocarro = null;
			productocarro = productoServicio.getProductosPorIdCarrito(carritolista);
			// Calcular el total del pedido
			double total = 0.0;
			for (Producto producto : productocarro) {
				double precio = producto.getPrecio();
				total += precio;
				System.out.println(total);
			}
			System.out.println(total);
			model.addAttribute("preciototal", total);
			model.addAttribute("carritoproductos", productocarro);
	
			// Coger el usuario que hace el pedido
			Usuario usuario;
			String email = (String) sesion.getAttribute("sesion");
			System.out.println("usuario" + email);
			System.out.println(usuarioServicio.getUsuarioByEmail(email));
			usuario = usuarioServicio.getUsuarioByEmail(email);
			logger.info(String.format(" >>>>>> Cargando el metodo de pago"));



			return "metodopago";
		} else {
			return "/login";
		}
	}

	@PostMapping("/metodopago/pago")
	public String pago(Model model, HttpSession sesion, @ModelAttribute MetodoPago metodoPago) {
		if (sesion.getAttribute("sesion") != null) {
			System.out.println(metodoPago.getMetodo_pago());
			
			carritolista = (ArrayList) sesion.getAttribute("carritosesion");
			ArrayList<Producto> productocarro = null;
			productocarro = productoServicio.getProductosPorIdCarrito(carritolista);
			// Calcular el total del pedido
			double total = 0.0;
			for (Producto producto : productocarro) {
				double precio = producto.getPrecio();
				total += precio;
				System.out.println(total);
			}
			System.out.println(total);
			
			model.addAttribute("preciototal", total);
			model.addAttribute("carritoproductos", productocarro);
			// Coger el usuario que hace el pedido
			Usuario usuario;
			String email = (String) sesion.getAttribute("sesion");
			System.out.println("usuario" + email);
			System.out.println(usuarioServicio.getUsuarioByEmail(email));
			usuario = usuarioServicio.getUsuarioByEmail(email);
			// fecha
			Date date = new Date();
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			String formattedDate = simpleDateFormat.format(date);
			java.sql.Date date1 = java.sql.Date.valueOf(formattedDate);
			//
			Pedido pedido = new Pedido(usuario.getId(), date1, metodoPago.getMetodo_pago(), "pendiente", "factura", total);
			Pedido pedidoNuevo = pedidoServicio.addPedido(pedido);
			System.out.println(pedidoNuevo.getId());
			for (Producto producto : productocarro) {
				DetallePedido detallePedido = new DetallePedido(pedidoNuevo.getId(), producto.getId(),
						producto.getPrecio(), 1, 21, producto.getPrecio() * 1);
				detalleServicio.addDetallePedido(detallePedido);
				int stock = producto.getStock();
				int nuevostock = stock-1;
				producto.setStock(nuevostock);
				productoServicio.editProducto(producto);
			}
			 carritolista = null;
			 sesion.setAttribute("carritosesion", carritolista);
				logger.info(String.format(" >>>>>> Hacer el pago del pedido y terminar el pedido"));


			//return "pago";
			return "redirect:/";
		} else {
			return "/login";
		}
	}



}
