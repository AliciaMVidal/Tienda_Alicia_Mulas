package tienda.alicia.v01.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jxl.Cell;
import jxl.DateCell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.*;
import jxl.write.Number;
import tienda.alicia.v01.model.Categoria;
import tienda.alicia.v01.model.OpcionesMenu;
import tienda.alicia.v01.model.Producto;
import tienda.alicia.v01.model.Proveedor;
import tienda.alicia.v01.model.Rol;
import tienda.alicia.v01.model.Usuario;
import tienda.alicia.v01.service.CategoriaService;
import tienda.alicia.v01.service.OpcionesMenuService;
import tienda.alicia.v01.service.ProductoService;
import tienda.alicia.v01.service.ProveedorService;
import tienda.alicia.v01.service.UsuarioService;

@Controller
@RequestMapping("/administracion/productos")
public class ProductoController {

	@Autowired
	ProductoService productoService;
	@Autowired
	UsuarioService usuarioService;
	@Autowired
	OpcionesMenuService opcionesMenuServicio;
	@Autowired
	CategoriaService categoriasService;
	@Autowired
	ProveedorService proveedorService;

	@GetMapping("")
	public String inicio(Model model, HttpSession sesion) {
		model.addAttribute("listaProductos", productoService.getListaProductos());
		List<Producto> listaProductos = productoService.getListaProductos();
		// para las categorias
		// Cargar el nombre de la categoria que sale en la tabla segun el id_categoria
		// Se saca una lista de todas las categorias que hay
		List<Categoria> listaCategorias = categoriasService.getTodasCategorias();
		List<Proveedor> listaProveedor = proveedorService.getTodosProveedores();
		// Se crea una lista donde se van a almacenar todos los ids de las categorias
		// que
		// puede tener un producto
		ArrayList<Integer> listadeIdsCategoriaEnProducto = new ArrayList();
		ArrayList<Integer> listadeIdsProovedorEnProducto = new ArrayList();
		// Se recorre la lista de los productos que hay
		// y se guardan los ids de las categorias de cada producto
		for (Producto producto : listaProductos) {
			listadeIdsCategoriaEnProducto.add(producto.getId_categoria());
			listadeIdsProovedorEnProducto.add(producto.getId_proveedor());
		}
		// Se crea un hashmap donde se guarda el id de la categoria que esta en el
		// producto
		// y el nombre de la categoria de la tabla categoria
		// Se pasa como parametro la lista de los ids categoria que estan en los
		// productos
		// productos
		HashMap<Integer, String> listaIdCategoriaEnProducto = categoriasService
				.listaCategoriasIds(listadeIdsCategoriaEnProducto);
		HashMap<Integer, String> listaIdsProveedorEnProducto = proveedorService.listaProveedoresIds(listadeIdsProovedorEnProducto);
		// Se pasa como atributo a la vista
		model.addAttribute("nombrecategoria", listaIdCategoriaEnProducto);
		model.addAttribute("nombreproveedor", listaIdsProveedorEnProducto);
		// Coger el usuario que quiere va a hacer esto
		Usuario usuario;
		String email = (String) sesion.getAttribute("sesion");
		usuario = usuarioService.getUsuarioByEmail(email);
		int idrol = usuario.getId_rol();
		model.addAttribute("idrol", idrol);
		// Para cargar el menu segun el rol del usuario(admin o empleado)
		ArrayList<OpcionesMenu> listaOpciones = new ArrayList<OpcionesMenu>();
		listaOpciones = (ArrayList<OpcionesMenu>) opcionesMenuServicio.getOpcionesPorRol(idrol);
		model.addAttribute("listaOpciones", listaOpciones);
		return "administracion/productos";
	}

	@GetMapping("/addproducto")
	public String addProducto(Model model, HttpSession sesion) {
		model.addAttribute("producto", new Producto());
		Usuario usuario;
		String email = (String) sesion.getAttribute("sesion");
		usuario = usuarioService.getUsuarioByEmail(email);
		int idrol = usuario.getId_rol();
		model.addAttribute("idrol", idrol);
		ArrayList<OpcionesMenu> listaOpciones = new ArrayList<OpcionesMenu>();
		listaOpciones = (ArrayList<OpcionesMenu>) opcionesMenuServicio.getOpcionesPorRol(idrol);
		// Nuevo
		List<Categoria> listaCategorias;
		List<Proveedor> listaProveedores;
		listaCategorias = categoriasService.getTodasCategorias();
		listaProveedores = proveedorService.getTodosProveedores();
		model.addAttribute("listaCategorias", listaCategorias);
		model.addAttribute("listaProveedores", listaProveedores);
		model.addAttribute("listaOpciones", listaOpciones);
		return "administracion/addproducto";
	}

	@PostMapping("/addproducto/submit")
	public String addProductoSubmit(@ModelAttribute Producto producto) {
		producto.setImagen("/img/sinfoto.jpg");
		Date date = new Date();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String formattedDate = simpleDateFormat.format(date);
		java.sql.Date date1 = java.sql.Date.valueOf(formattedDate);
		producto.setFecha_alta(date1);
		producto.setFecha_baja(null);
		producto.setActivo(true);
		productoService.addProducto(producto);
		return "redirect:/administracion/productos";
	}

	@GetMapping("/editproducto/{id}")
	public String editProducto(@PathVariable int id, Model model, HttpSession sesion) {
		Producto producto = productoService.getProductoId(id);
		Usuario usuario;
		String email = (String) sesion.getAttribute("sesion");
		usuario = usuarioService.getUsuarioByEmail(email);
		int idrol = usuario.getId_rol();
		model.addAttribute("idrol", idrol);
		ArrayList<OpcionesMenu> listaOpciones = new ArrayList<OpcionesMenu>();
		listaOpciones = (ArrayList<OpcionesMenu>) opcionesMenuServicio.getOpcionesPorRol(idrol);
		model.addAttribute("listaOpciones", listaOpciones);
		// Nuevo
		List<Categoria> listaCategorias;
		List<Proveedor> listaProveedores;
		listaCategorias = categoriasService.getTodasCategorias();
		listaProveedores = proveedorService.getTodosProveedores();
		model.addAttribute("listaCategorias", listaCategorias);
		model.addAttribute("listaProveedores", listaProveedores);

		model.addAttribute("producto", producto);
		return "administracion/editproducto";
	}

	@PostMapping("/editproducto/submit")
	public String editProductoSubmit(@ModelAttribute Producto producto) {
		
		
		productoService.editProducto(producto);
		return "redirect:/administracion/productos";
	}

	@GetMapping("/deleteproducto/{id}")
	public String deleteProducto(@PathVariable int id, Model model) {
		productoService.deleteProducto(id);
		return "redirect:/administracion/productos";
	}

	@GetMapping("/desactivarproducto/{id}")
	public String desactivarProducto(@PathVariable int id, Model model) {
		Producto producto = productoService.getProductoPorId(id);
		producto.setActivo(false);
		Date date = new Date();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String formattedDate = simpleDateFormat.format(date);
		java.sql.Date date1 = java.sql.Date.valueOf(formattedDate);
		producto.setFecha_baja(date1);
		productoService.editProducto(producto);
		return "redirect:/administracion/productos";
	}

	@GetMapping("/activarproducto/{id}")
	public String activarProducto(@PathVariable int id, Model model) {
		Producto producto = productoService.getProductoPorId(id);
		producto.setActivo(true);
		producto.setFecha_baja(null);
		Date date = new Date();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String formattedDate = simpleDateFormat.format(date);
		java.sql.Date date1 = java.sql.Date.valueOf(formattedDate);
		producto.setFecha_alta(date1);
		productoService.editProducto(producto);
		return "redirect:/administracion/productos";
	}

	@GetMapping("/exportarproductos")
	public String exportarProdutos() {

		File fichero = new File("ficheros/ProductosExportar.xls");

		try {
			WritableWorkbook w = Workbook.createWorkbook(fichero);

			/*
			 * Workbook wb = Workbook.getWorkbook(fichero); WritableWorkbook w =
			 * Workbook.createWorkbook(fichero, wb);
			 */

			// Nombre de la hoja
			WritableSheet sheet = w.createSheet("Productos", 0);

			// columna fila contenido
			ArrayList<Producto> listaProductos = new ArrayList<Producto>();
			listaProductos = (ArrayList<Producto>) productoService.getListaProductos();

			jxl.write.Label cadena;
			jxl.write.Number numero;
			jxl.write.DateTime fecha;
			
			//para coger el nombre de la categoria
			Categoria categoria = null;
			Proveedor proveedor = null;
			for (Producto producto : listaProductos) {

				//Categoria
				cadena = new jxl.write.Label(0, 0, "Categoria");
				sheet.addCell(cadena);
				//Coger el nombre de la categoria
				categoria = categoriasService.getCategoriaById(producto.getId_categoria());
				cadena = new jxl.write.Label(0, listaProductos.indexOf(producto), categoria.getNombre());
				sheet.addCell(cadena);
				//Proveedor
				cadena = new jxl.write.Label(1, 0, "Proveedor");
				sheet.addCell(cadena);
				proveedor = proveedorService.getProveedorById(producto.getId_proveedor());
				cadena = new jxl.write.Label(1, listaProductos.indexOf(producto) + 1, proveedor.getNombre());
				sheet.addCell(cadena);
				//
				cadena = new jxl.write.Label(2, 0, "nombre");
				sheet.addCell(cadena);
				cadena = new jxl.write.Label(2, listaProductos.indexOf(producto) + 1, producto.getNombre());
				sheet.addCell(cadena);
				cadena = new jxl.write.Label(3, 0, "descripcion");
				sheet.addCell(cadena);
				cadena = new jxl.write.Label(3, listaProductos.indexOf(producto) + 1, producto.getDescripcion());
				sheet.addCell(cadena);
				cadena = new jxl.write.Label(4, 0, "precio");
				sheet.addCell(cadena);
				numero = new jxl.write.Number(4, listaProductos.indexOf(producto) + 1, producto.getPrecio());
				sheet.addCell(numero);
				cadena = new jxl.write.Label(5, 0, "stock");
				sheet.addCell(cadena);
				numero = new jxl.write.Number(5, listaProductos.indexOf(producto) + 1, producto.getStock());
				sheet.addCell(numero);
				cadena = new jxl.write.Label(6, 0, "fecha_alta");
				sheet.addCell(cadena);
				fecha = new jxl.write.DateTime(6, listaProductos.indexOf(producto) + 1, producto.getFecha_alta());
				sheet.addCell(fecha);
				cadena = new jxl.write.Label(7, 0, "fecha_baja");
				sheet.addCell(cadena);
				if(producto.getFecha_baja() == null) {
					cadena = new jxl.write.Label(7, listaProductos.indexOf(producto) + 1, " ");
					sheet.addCell(cadena);
				}else {
				fecha = new jxl.write.DateTime(7, listaProductos.indexOf(producto) + 1, producto.getFecha_baja());
				sheet.addCell(fecha);
				}
				cadena = new jxl.write.Label(8, 0, "impuesto");
				sheet.addCell(cadena);
				numero = new jxl.write.Number(8, listaProductos.indexOf(producto) + 1, producto.getImpuesto());
				sheet.addCell(numero);

			}

			w.write();
			w.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "redirect:/administracion/productos";

	}

	@GetMapping("/importarproductos")
	public String importarProdutos() throws ParseException {
		File fichero = new File("./ficheros/ProductosImportar.xls");

		Workbook w;
		try {
			w = Workbook.getWorkbook(fichero);

			// Se lee la primera hoja de la excel
			Sheet sheet = w.getSheet(0);

			ArrayList<Producto> listaProductos = new ArrayList<Producto>();
			Producto producto = new Producto();

			for (int f = 0; f < sheet.getRows(); f++) {

				producto.setId_categoria(Integer.parseInt(sheet.getCell(0, f).getContents()));
				System.out.println(sheet.getCell(0, f).getContents());
				producto.setNombre(sheet.getCell(1, f).getContents());
				producto.setDescripcion(sheet.getCell(2, f).getContents());
				producto.setPrecio(Double.parseDouble(sheet.getCell(3, f).getContents()));
				producto.setStock(Integer.parseInt(sheet.getCell(4, f).getContents()));
				Date date = new Date();
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
				String formattedDate = simpleDateFormat.format(date);
				java.sql.Date date1 = java.sql.Date.valueOf(formattedDate);
				producto.setFecha_alta(date1);
				producto.setFecha_baja(date1);
				producto.setImpuesto(Double.parseDouble(sheet.getCell(7, f).getContents()));
				producto.setImagen(sheet.getCell(8, f).getContents());

			}
			listaProductos.add(producto);
			for (Producto productol : listaProductos) {
				productoService.addProducto(productol);
			}

		} catch (BiffException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//    

		return "redirect:/administracion/productos";
	}
}
