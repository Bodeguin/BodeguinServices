package pe.com.bodeguin.restController;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;	
import pe.com.bodeguin.model.Usuario;
import pe.com.bodeguin.service.UsuarioService;

@Api(value = "Endpoints de usuario")
@RestController
@RequestMapping("/api/usuarios")
public class UsuarioRestController {
	
	@Autowired
	private UsuarioService usuarioService;
	
	@ApiOperation(value = "EndPoint que permite listar los usuarios")
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Usuario>> listar(){
		ResponseEntity<List<Usuario>> response;
		try {
			List<Usuario> usuarios = usuarioService.findAll();
			response = new ResponseEntity<List<Usuario>>(usuarios, HttpStatus.OK);
			return response;
		} catch (Exception e) {
			return new ResponseEntity<List<Usuario>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@ApiOperation(value ="Endpoint que permite buscar un usuario por id")
	@GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Usuario> getUsuario(@PathVariable("id") int id){
		try {
			Optional<Usuario> usuario = usuarioService.findById(id);
			if(usuario.isPresent()) {
				return new ResponseEntity<Usuario>(usuario.get(), HttpStatus.OK);
			}else {
				return new ResponseEntity<Usuario>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<Usuario>(HttpStatus.BAD_REQUEST);
		}
	}
	@ApiOperation(value = "EndPoint que permite grabar un usuario")
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Usuario> nuevo(@RequestBody Usuario usuario) {
		try {
			Usuario nuevoUsuario = usuarioService.save(usuario);
			return new ResponseEntity<Usuario>(nuevoUsuario, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<Usuario>(HttpStatus.BAD_REQUEST);
		}
	}
	@ApiOperation(value = "EndPoint que permite actualizar un usuario")
	@PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Usuario> actualizar(@PathVariable("id") Integer id, @RequestBody Usuario usuario) {
		try {
			if (id.equals(usuario.getId())) {
				Optional<Usuario> usu = usuarioService.findById(id);
				if (usu.isPresent()) {
					Usuario usuarioUpdate = usuarioService.update(usuario);
					return new ResponseEntity<Usuario>(usuarioUpdate, HttpStatus.OK);
				} else {
					return new ResponseEntity<Usuario>(HttpStatus.NOT_FOUND);
				}
			} else {
				return new ResponseEntity<Usuario>(HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			return new ResponseEntity<Usuario>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@DeleteMapping(path = "/{id}", produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> eliminar(@PathVariable("id") Integer id) {
		try {
			Optional<Usuario> usuario = usuarioService.findById(id);
			if (usuario.isPresent()) {
				usuarioService.deleteById(id);
				return new ResponseEntity<String>("Eliminado", HttpStatus.ACCEPTED);
			} else {
				return new ResponseEntity<String>(HttpStatus.NOT_ACCEPTABLE);
			}
		} catch (Exception e) {
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@ApiOperation(value = "EndPoint que permite obtener usuario por correo y password")
	@PostMapping(path = "/login",consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,  produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Usuario> login( Usuario usuario) {
		try {
			String correo = usuario.getCorreo();
			String password = usuario.getPassword();
			
			Optional<Usuario> login = usuarioService.login(correo, password);
			if (login.isPresent()) {
				return new ResponseEntity<Usuario>(login.get(), HttpStatus.OK);
			} else {
				return new ResponseEntity<Usuario>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<Usuario>(HttpStatus.BAD_REQUEST);
		}	
	}	
	@ApiOperation(value = "EndPoint que permite obtener usuario por correo y password (2do intento)")
	@PostMapping(path= "/log", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Usuario> log(@RequestBody Usuario user){
		try {
			String correo = user.getCorreo();
			String password = user.getPassword();
			Optional<Usuario> login = usuarioService.login(correo, password);
			if (login.isPresent()) {
				return new ResponseEntity<Usuario>(login.get(), HttpStatus.OK);
			} else {
				return new ResponseEntity<Usuario>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<Usuario>(HttpStatus.BAD_REQUEST);
		}
	}
}
