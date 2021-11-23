package tienda.alicia.v01.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tienda.alicia.v01.model.Producto;
import tienda.alicia.v01.model.Proveedor;
import tienda.alicia.v01.model.Usuario;
import tienda.alicia.v01.repository.UsuarioRepository;

@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	
	@PostConstruct
	public void cargarUsuarios() {
		
		Usuario u = new Usuario(1, "alicia@tienda.es", "MTIzNA==", "Alicia", "Mulas", "Vidal", "Camiño Víctor, 73, 67º D", "Zamora", "Zamora", "98534756", "9845637U", true);
		usuarioRepository.save(u);
		u = new Usuario(1, "admin@tienda.es", "MTIzNA==", "Claudia", "Bladeras", "García", "Rúa Raquel, 245, 54º F", "Ourense","Ourense", "67823568", "3455635L", true);
		usuarioRepository.save(u);
		u = new Usuario(2, "empleado@tienda.es", "MTIzNA==", "Pedro", "Torre", "Alvarado", "Calle Moral, 379, 56º D", "Os Soriano del Vallès", "Barcelona", "567984531","1287354T", true);
		usuarioRepository.save(u);
		u = new Usuario(3, "cliente@correo.es", "MTIzNA==", "Julia", "Gracía", "Fernandez", "Ronda Jana, 1, 0º C", "La Valles del Barco", "Avila", "28328322", "21394858L", true);
		usuarioRepository.save(u);
		u = new Usuario(3, "cliente2@correo.es", "MTIzNA==", "Asier", "Quezada", "Tercero", "Carrer Héctor, 342, 0º A", "Alaniz del Mirador", "Valladolud", "679258645", "9876348A", true);
		usuarioRepository.save(u);
	}
	
	public boolean loginCorrecto(Usuario usuario) {
		boolean correcto = false;
		//Lo de antes funcionando
		//List<Usuario> listaUsuarios = usuarioRepository.buscarUsuarioLogin(usuario.getUsername(), usuario.getPassword());
		//Encriptar contraseña
				Base64 base64 = new Base64();
				//String passwEncripada = new String(base64.encode(usuario.getClave().getBytes()));
		List<Usuario> listaUsuarios = usuarioRepository.comprobarUsuarioLogin(usuario.getEmail(),  new String(base64.encode(usuario.getClave().getBytes())));
		if(!listaUsuarios.isEmpty()) {
			correcto = true;
		}
		return correcto;
	}
	
	public boolean registroCorrecto(Usuario usuario) {
		boolean correcto = false;
		//TODO: validaciones de datos
		//Usuario user = usuarioRepository.insertarUsuarioRegistro(usuario.getUsername(), usuario.getPassword());
		usuarioRepository.save(usuario);
		correcto = true;
		
		return correcto;
	}
	
	public List<Usuario> getListaUsuarios() {
		return usuarioRepository.findAll();
	}
	public List<Usuario> getListaEmpleados(int id){
		return usuarioRepository.listaEmpleados(id);
	}
	
	
	public void addUsuario(Usuario usuario) {
		usuarioRepository.save(usuario);
	}
	
	public Usuario getUsuarioId(int id) {
		Usuario usuario = usuarioRepository.getById(id);
		return usuario;
	}
	
	public void editUsuario(Usuario usuario) {
		usuarioRepository.save(usuario);
	}
	
	public void deleteUsuario(int id) {
		Usuario usuario = usuarioRepository.getById(id);
		usuarioRepository.delete(usuario);
	}
	
//	public Usuario getUsuarioByUsername(String username) {
//		System.out.println("service:" + username);
//		Usuario usuario = usuarioRepository.findByUsername(username);
//		return usuario;
//	}
//	
	public Usuario getUsuarioByEmail(String email) {
		Usuario usuario = usuarioRepository.findByEmail(email);
		return usuario;
	}
	
	public HashMap<Integer, String> listaUsuariosIds(ArrayList listadeIdsUsuariosEnPedidos) {
		// Se crea una lista categorias cuya id esten en la tabla Productos
		List<Usuario> listaObjetosUsuarioenPedido = usuarioRepository.findByIdIn(listadeIdsUsuariosEnPedidos);
		// Se crea un HashMap con la relacion de producto.id_categoria y categoria.id
		HashMap<Integer, String> hmRelacionUsuarioYPedido = new HashMap<Integer, String>();
		// Se recorren los objetos para sacar el id.categoria y guardarlo en el hashmap
		for (Usuario usuario : listaObjetosUsuarioenPedido) {
			hmRelacionUsuarioYPedido.put(usuario.getId(), usuario.getEmail());
		}
		return hmRelacionUsuarioYPedido;
	}
	
	public HashMap<Integer, String> listaUsuariosIdsEnValoraciones(ArrayList listadeIdsUsuariosEnValoraciones) {
		// Se crea una lista categorias cuya id esten en la tabla Productos
		List<Usuario> listaObjetosUsuarioenValoracion = usuarioRepository.findByIdIn(listadeIdsUsuariosEnValoraciones);
		// Se crea un HashMap con la relacion de producto.id_categoria y categoria.id
		HashMap<Integer, String> hmRelacionUsuarioYValoracion = new HashMap<Integer, String>();
		// Se recorren los objetos para sacar el id.categoria y guardarlo en el hashmap
		for (Usuario usuario : listaObjetosUsuarioenValoracion) {
			hmRelacionUsuarioYValoracion.put(usuario.getId(), usuario.getNombre());
		}
		return hmRelacionUsuarioYValoracion;
	}

}
