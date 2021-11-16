package tienda.alicia.v01.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import tienda.alicia.v01.model.Usuario;

@Repository
public interface UsuarioRepository  extends JpaRepository<Usuario, Integer>{
	
	//Usuario findByUsername(String username);
	
	
	//@Query(value="select * from Usuario where username=?1 and password=?2", nativeQuery = true)
	//List<Usuario> buscarUsuarioLogin(String username, String password);

	//List<Usuario> findb
	//@Query(value="select * from Usuario where username=?1", nativeQuery = true)

	@Query(value="select * from Usuario where email=?1 and clave=?2", nativeQuery = true)
	List<Usuario> comprobarUsuarioLogin(String email, String clave);

	
	Usuario findByEmail(String email);
	
	
	@Query(value="select * from Usuario where id_rol=?1", nativeQuery = true)
	List<Usuario> listaEmpleados(int id);
	

}
