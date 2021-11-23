package tienda.alicia.v01.service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tienda.alicia.v01.model.Producto;
import tienda.alicia.v01.repository.ProductoRepository;

@Service
public class ProductoService {

	@Autowired
	private ProductoRepository productoRepository;

	@PostConstruct
	public void cargarProductos() {
		// fecha
		Date date = new Date();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String formattedDate = simpleDateFormat.format(date);
		java.sql.Date date1 = java.sql.Date.valueOf(formattedDate);
		
		Producto producto = new Producto(1, 1, "Applaws feline adult Salmón y Pollo 7,5KG",
				"Pienso para gatos Sin cereales e hipoalergénico. Con una receta que garantiza una dieta completa y equilibrada.",
				37.99, 20, date1,null, 21, "/img/comidagatoapplaws.jpg", true);
		productoRepository.save(producto);
		producto = new Producto(1, 2, "Lata Catzilla para gato 90Gr", "Comida húmeda para gatos en edad adulta o gatos senior. Su principal característica es que en su elaboración se han utilizado ingredientes naturales.\r\n" + 
				"", 1.45, 120, date1, null, 21, "/img/comidagatocatzilla.jpg", true);
		productoRepository.save(producto);
		producto = new Producto(1, 1, "Catisfactions snacks para gatos Salmón", "Snacks para gatos, elaborados con deliciosos sabores para crear unos sabrosos premios felinos.",
				1.49, 40, date1, null, 21, "/img/comidagatosnack.jpg", true);
		productoRepository.save(producto);
		producto = new Producto(2, 2, "Dogzilla Adult Salmón", "Alimento completo para perros con un gran sabor a salmón, que le proporciona todos los nutrientes que necesita.",
				19.95, 25, date1, null,21, "/img/comidaperrodogzilla.jpg", true);
		productoRepository.save(producto);
		producto = new Producto(2, 5, "True origins wild dog country", "Pienso natural elaborado para perros adultos, con ingredientes seleccionados para aportar una nutrición completa y equilibrada a nuestras mascotas para que se mantengan sanos",
				55.95, 15, date1, null, 21, "/img/comidaperronatural.jpg", true);
		productoRepository.save(producto);
		producto = new Producto(2, 5, "Pienso para perros purina pro", "Pienso para cachorros de razas medianas elaborado con ingredientes seleccionados para conseguir una receta nutritiva que fomente el crecimiento de la mascota.\r\n" + 
				"", 49.45, 64, date1, null, 21, "/img/comidaperrocachorro.jpg", true);
		productoRepository.save(producto);
		producto = new Producto(3, 3, "JBL novogranomix mini alimento para peces", "Ofrece una alimentación completa a tus peces tropicales de agua dulce de pequeño tamaño con JBL NovoGranoMix Mini alimento para peces.\r\n" + 
				"", 10.68, 12, date1, null, 21, "/img/comidapecesmini.jpg", true);
		productoRepository.save(producto);
		producto = new Producto(3, 3, "Tetra pond sticks comida para peces","Una alimentación completa, saludable y que potenciará sus colores naturales, así es Tetra Pond sticks comida para peces.\r\n" + 
				"", 5.29, 45, date1, null, 21, "/img/comidapecesnaranja.jpg", true);
		productoRepository.save(producto);
		producto = new Producto(3, 3, "JBL propond all seasons alimento para peces", "Elige un alimento que cubra todas las necesidades nutricionales de tus peces de estanque con JBL ProPond All Seasons alimento para peces.\r\n" + 
				"",40.79, 17, date1, null, 21, "/img/comidapecestodos.jpg", true);
		productoRepository.save(producto);
		producto = new Producto(4, 4, "Vitakraft menú canario 1KG","Alimento especial para canarios.\r\n" + 
				"",3.49, 62, date1, null, 21, "/img/comidapajaroscanarios.jpg", true);
		productoRepository.save(producto);
		producto = new Producto(4, 4, "Vivanimals pienso para pájaros exóticos","El pienso para agapornis, ninfas y cotorras Vivanimals es un alimento completo para nutrir a tus aves diariamente con los mejores ingredientes.\r\n" + 
				"",1.99, 22, date1, null, 21, "/img/comidapajaroexotico.jpg", true);
		productoRepository.save(producto);
		producto = new Producto(4, 3, "Snack para pájaros small file espiga de mijo 150GR","La espiga de mijo es un alimento complementario para pájaros rico en nutrientes. Y... ¡diversión asegurada para la mascota!\r\n" + 
				"",2.91, 7, date1, null, 21, "/img/comidapajarosnack.jpg", true);
		productoRepository.save(producto);
		}
		

	public List<Producto> getListaProductos() {
		return productoRepository.findAll();
	}

	public void addProducto(Producto producto) {
		productoRepository.save(producto);
	}

	public Producto getProductoId(int id) {
		Producto producto = productoRepository.getById(id);
		return producto;
	}

	public void editProducto(Producto producto) {
		productoRepository.save(producto);
	}

	public void deleteProducto(int id) {
		Producto producto = productoRepository.getById(id);
		productoRepository.delete(producto);
	}

	public ArrayList getProductosPorIdCarrito(ArrayList ids) {
		System.out.println("ids: " + ids.toString());
		System.out.println("size: " + ids.size());
		ArrayList<Producto> lista = new ArrayList<Producto>();
		for (int i = 0; i < ids.size(); i++) {
			System.out.println("id: " + ids.get(i));
			Producto producto = productoRepository.getById((Integer) ids.get(i));
			System.out.println(producto.getNombre());
			lista.add(producto);
		}
		return lista;
	}

	public Producto getProductoPorId(int id) {
		Producto producto = productoRepository.getById(id);
		return producto;
	}

	// metodo para obtener el nombre del producto (producto.nombre)
	// de un array de ids de producto que estan en la tabla de detalle
	// pedido(detalle_pedido.id_producto)

	public HashMap<Integer, String> listadeproductosids(ArrayList listaIdsProductoEnDetallePedido) {
		// Se crea una lista de los Producto cuya id esten en la tabla detalle pedido
		List<Producto> listaObjetosProductoEnElDetallePedido = productoRepository
				.findByIdIn(listaIdsProductoEnDetallePedido);
		// Se crea un HashMap con la relacion de detalle_producto.id_producto y
		// producto.id
		HashMap<Integer, String> hmRelacionDetalleYProducto = new HashMap<Integer, String>();
		// Se recorren los objetos para sacar el id.producto y guardarlo en el hashmap
		for (Producto producto : listaObjetosProductoEnElDetallePedido) {
			hmRelacionDetalleYProducto.put(producto.getId(), producto.getNombre());
		}
		return hmRelacionDetalleYProducto;
	}
	
	//Para el buscador
	
	
	//Por precio caro
	public List<Producto> getListaProductosCaro() {
		return productoRepository.findByOrderByPrecioDesc();
	}
	
	//Por precio barato
	public List<Producto> getListaProductosBarato() {
		return productoRepository.findByOrderByPrecioAsc();
	}
	//Por categoria
	
	public List<Producto> getListaProductoCategoria(int id){
		return productoRepository.getProductoPorCategoria(id);
	}

}
