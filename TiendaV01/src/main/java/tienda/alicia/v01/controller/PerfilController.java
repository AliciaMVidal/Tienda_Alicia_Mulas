package tienda.alicia.v01.controller;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.swing.text.StyledEditorKit.ForegroundAction;
import javax.xml.bind.annotation.XmlElement.DEFAULT;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;

import tienda.alicia.v01.model.DetallePedido;
import tienda.alicia.v01.model.Pedido;
import tienda.alicia.v01.model.Producto;
import tienda.alicia.v01.model.Usuario;
import tienda.alicia.v01.service.PedidoDetalleService;
import tienda.alicia.v01.service.PedidoService;
import tienda.alicia.v01.service.ProductoService;
import tienda.alicia.v01.service.UsuarioService;
import tienda.alicia.v01.util.EscribirPDF;

@Controller
@RequestMapping("/perfil")
public class PerfilController {

	@Autowired
	UsuarioService usuarioServicio;
	@Autowired
	PedidoService pedidoServicio;
	@Autowired
	PedidoDetalleService detallePedidoServicio;
	@Autowired
	ProductoService productoServicio;

	@GetMapping("")
	public String inicio(Model model, HttpSession sesion) {
		// Coger el usuario que esta conectado
		Usuario usuario;
		String email = (String) sesion.getAttribute("sesion");
		usuario = usuarioServicio.getUsuarioByEmail(email);
		model.addAttribute("usuario", usuario);
		List listapedidos = pedidoServicio.getPedidoByIdUsuario(usuario.getId());
		System.out.println(listapedidos.toString());
		model.addAttribute("listaPedidos", listapedidos);
		return "perfil";

	}

	@GetMapping("/editar/{id}")
	public String editarPerfil(@PathVariable int id, Model model) {
		Usuario usuario = usuarioServicio.getUsuarioId(id);
		model.addAttribute("usuario", usuario);
		return "perfileditar";
	}

	@PostMapping("/editar/submit")
	public String editarPerfilSubmit(@ModelAttribute Usuario usuario) {
		usuarioServicio.editUsuario(usuario);
		return "redirect:/perfil";
	}

	@GetMapping("/editarpedido/{id}")
	public String editarPedido(Model model, @PathVariable int id) {
		Usuario usuario = usuarioServicio.getUsuarioId(id);
		model.addAttribute("usuario", usuario);
		List listapedidos = pedidoServicio.getPedidoByIdUsuario(usuario.getId());
		model.addAttribute("listaPedidos", listapedidos);
		return "perfileditarpedidos";
	}
	
	@GetMapping("/cancelarpedido/{id}")
	public String cancelarPedido(Model model, @PathVariable int id) {
		Pedido pedido = pedidoServicio.getPedidoById(id);
		pedido.setEstado("en cancelacion");
		pedidoServicio.cancelarPedido(pedido);
		return "redirect:/perfil";
	}
	
	@GetMapping("/verdetalle/{id}")
	public String verDetallePedido(@PathVariable int id, Model model) {
		//Buscar el pedido por el id
		Pedido pedido = pedidoServicio.getPedidoById(id);
		//Lista de las lineas de pedido que hay en un pedido
		List<DetallePedido> listaDetallePedido = detallePedidoServicio.getDetalleByIDPedido(pedido.getId());
		//Esto sirve para sacar el nombre de los productos que hay en el detalle de los pedidos  
		//Se saca una lista de todos los productos que hay
		List<Producto> listaProductos = productoServicio.getListaProductos();
		//Se crea una lista donde se van a almacenar todos los ids de productos que hay en un detalle de producto
		ArrayList<Integer> listadeIdsProductoEnDetallePedido = new ArrayList();
		//Se recorre la lista de las linas que hay en un pedido
		//y se guardan los ids de los productos de cada linea en un arraylist
		for (DetallePedido detalle : listaDetallePedido) {
			listadeIdsProductoEnDetallePedido.add(detalle.getId_producto());
		}
		//Se crea un hashmap donde se guarda el id del producto que esta en el detalle del producto y el nombre de producto de la tabla producto
		//Se pasa como parametro la lista de los ids producto que estan en en el detalle de producto
		HashMap<Integer, String> listaIdProductoEnDetalleNombreProducto = productoServicio.listadeproductosids(listadeIdsProductoEnDetallePedido);
		//System.out.println(listaIdProductoEnDetalleNombreProducto.toString());
		//System.out.println("lista");
		//System.out.println(listaDetallePedido);
		model.addAttribute("listadetalle", listaDetallePedido);
		model.addAttribute("productonombre", listaIdProductoEnDetalleNombreProducto);
		
		return "perfilverdetallepedido";
	}
	
	@GetMapping("/descargarfactura/{id}")
	public String descargarFactura(@PathVariable int id, Model model) {
		//Buscar el pedido por el id
		Pedido pedido = pedidoServicio.getPedidoById(id);
		//Lista de las lineas de pedido que hay en un pedido
		List<DetallePedido> listaDetallePedido = detallePedidoServicio.getDetalleByIDPedido(pedido.getId());
		//Esto sirve para sacar el nombre de los productos que hay en el detalle de los pedidos  
		//Se saca una lista de todos los productos que hay
		List<Producto> listaProductos = productoServicio.getListaProductos();
		//Se crea una lista donde se van a almacenar todos los ids de productos que hay en un detalle de producto
		ArrayList<Integer> listadeIdsProductoEnDetallePedido = new ArrayList();
		//Se recorre la lista de las linas que hay en un pedido
		//y se guardan los ids de los productos de cada linea en un arraylist
		for (DetallePedido detalle : listaDetallePedido) {
			listadeIdsProductoEnDetallePedido.add(detalle.getId_producto());
		}
		//Se crea un hashmap donde se guarda el id del producto que esta en el detalle del producto y el nombre de producto de la tabla producto
		//Se pasa como parametro la lista de los ids producto que estan en en el detalle de producto
		HashMap<Integer, String> listaIdProductoEnDetalleNombreProducto = productoServicio.listadeproductosids(listadeIdsProductoEnDetallePedido);


		// Get keys and values
		for (Map.Entry<Integer, String> entry : listaIdProductoEnDetalleNombreProducto.entrySet()) {
			int k = entry.getKey();
			String v = entry.getValue();
			System.out.println("Key: " + k + ", Value: " + v);
		}
		for(String key : listaIdProductoEnDetalleNombreProducto.values()) {
			System.out.println("key"+key);
			
    	}
	
		EscribirPDF escribirPDf = new EscribirPDF();
		escribirPDf.escribir(pedido, listaIdProductoEnDetalleNombreProducto, listaDetallePedido);
		return  "redirect:/perfil";
	}
	
	
	
	
}
