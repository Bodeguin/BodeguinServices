package pe.com.bodeguin.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import pe.com.bodeguin.model.Usuario;
import pe.com.bodeguin.repository.UsuarioRepository;
import pe.com.bodeguin.security.UsuarioDetails;

@Service
public class UsuarioDetailsService implements UserDetailsService{
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Override
	public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {
		Optional<Usuario> buscado = this.usuarioRepository.findByCorreo(correo);
		if(buscado.isPresent()) {
			UsuarioDetails usuarioDetails = new UsuarioDetails(buscado.get());
			return usuarioDetails;
		}
		throw new UsernameNotFoundException("Invalid User");
	}
}