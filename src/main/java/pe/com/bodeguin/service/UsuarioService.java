package pe.com.bodeguin.service;

import java.util.Optional;

import pe.com.bodeguin.model.Usuario;

public interface UsuarioService  extends CrudService<Usuario, Integer>{
	Optional<Usuario> login(String correo, String password) throws Exception;
}
