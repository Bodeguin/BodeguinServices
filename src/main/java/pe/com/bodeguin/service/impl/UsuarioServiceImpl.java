package pe.com.bodeguin.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pe.com.bodeguin.model.Usuario;
import pe.com.bodeguin.repository.UsuarioRepository;
import pe.com.bodeguin.service.UsuarioService;

@Service
public class UsuarioServiceImpl implements UsuarioService{

	@Autowired
	private UsuarioRepository usuarioRpository;

	@Override
	public List<Usuario> findAll() throws Exception {
		return usuarioRpository.findAll();
	}

	@Override
	public Optional<Usuario> findById(Integer id) throws Exception {
		return usuarioRpository.findById(id);
	}

	@Override
	public Usuario save(Usuario t) throws Exception {
		return usuarioRpository.save(t);
	}

	@Override
	public Usuario update(Usuario t) throws Exception {
		return usuarioRpository.save(t);
	}

	@Override
	public void deleteById(Integer id) throws Exception {
		usuarioRpository.deleteById(id);
	}

	@Override
	public void deleteAll() throws Exception {
		usuarioRpository.deleteAll();
	}

	@Override
	public Optional<Usuario> login(String correo, String password) throws Exception {
		return usuarioRpository.login(correo, password);
	}

}
